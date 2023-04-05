package com.example.two;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.two.Api.CommunityApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.config.Config;
import com.example.two.model.Community;
import com.example.two.model.CommunityRes;
import com.example.two.model.User;
import com.google.android.gms.common.util.IOUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CommunityAddActivity extends AppCompatActivity {
    EditText editTitle;
    EditText editContent;
    ImageView contentImage;
    Button communityAddbtn;
    File communityImg;
    String AccessToken;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_add);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        contentImage = findViewById(R.id.contentImage);
        communityAddbtn = findViewById(R.id.communityAddBtn);

        contentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
            }
        });

        communityAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCommunity();
            }
        });
    }

    private void saveCommunity() {
        String title = editTitle.getText().toString().trim();
        String content = editContent.getText().toString().trim();
        RequestBody requestTitle = RequestBody.create(title, MediaType.parse("text/plain"));
        RequestBody requestContent = RequestBody.create(content, MediaType.parse("text/plain"));

        Retrofit retrofit = NetworkClient2.getRetrofitClient(CommunityAddActivity.this);
        CommunityApi api = retrofit.create(CommunityApi.class);

        if ( communityImg != null ){
            RequestBody requestBody = RequestBody.create(communityImg, MediaType.parse("image/jpg"));
            MultipartBody.Part uploadImgFile = MultipartBody.Part.createFormData("photo", communityImg.getName(),requestBody);
            Call<CommunityRes> call = api.createCommunity("Bearer "+AccessToken,uploadImgFile,requestTitle,requestContent);
            call.enqueue(new Callback<CommunityRes>() {
                @Override
                public void onResponse(Call<CommunityRes> call, Response<CommunityRes> response) {
                    if(response.code() == 200){
                        List<Community> newdata = response.body().getCommunityList();
                        Intent intent = new Intent();
                        intent.putExtra("community",newdata.get(0));
                        Log.i("정상작동?1",newdata.get(0).getNickname());
                        setResult(101,intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CommunityRes> call, Throwable t) {

                }
            });
        }else {
            Call<CommunityRes> call = api.createCommunityNoImage("Bearer "+AccessToken,requestTitle,requestContent);
            call.enqueue(new Callback<CommunityRes>() {
                @Override
                public void onResponse(Call<CommunityRes> call, Response<CommunityRes> response) {
                    if(response.code() == 200 ){
                        List<Community> newdata = response.body().getCommunityList();
                        Intent intent = new Intent();
                        intent.putExtra("community",newdata.get(0));
                        setResult(101,intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<CommunityRes> call, Throwable t) {

                }
            });
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                Picasso.get().load(uri).into(contentImage);

                getImagePath(uri);
            }
        }
    }

    public void getImagePath(Uri uri){
        String filename = getFileName(uri);
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver( ).openFileDescriptor( uri, "r" );
            if ( parcelFileDescriptor == null ){
                return;
            }
            FileInputStream inputStream = new FileInputStream( parcelFileDescriptor.getFileDescriptor( ) );
            communityImg = new File( this.getCacheDir( ), filename );
            FileOutputStream outputStream = new FileOutputStream( communityImg );
            IOUtils.copyStream( inputStream, outputStream);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String getFileName( Uri uri ) {
        Cursor cursor = getContentResolver( ).query( uri, null, null, null, null );
        try {
            if ( cursor == null ) return null;
            cursor.moveToFirst( );
            @SuppressLint("Range") String fileName = cursor.getString( cursor.getColumnIndex( OpenableColumns.DISPLAY_NAME ) );
            cursor.close( );
            return fileName;

        } catch ( Exception e ) {
            e.printStackTrace( );
            cursor.close( );
            return null;
        }
    }
}