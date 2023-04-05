package com.example.two;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.two.Api.LoginApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.Api.RegisterApi;
import com.example.two.Api.UserApi;
import com.example.two.config.Config;
import com.example.two.model.User;
import com.example.two.model.UserRes;
import com.google.android.gms.common.util.IOUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserUpdateActivity extends AppCompatActivity {

    CircleImageView imgProfile;
    TextView txtEmail;
    EditText editNickname;
    EditText editPassword;
    EditText editPassword2;
    Button btnDelete;
    Button btnSave;
    Button btnCheck;

    String userEmail;
    String password;
    String nickname;
    File profileImg;
    User user = new User();
    Boolean nicknamecheck = false;
    String AccessToken;

    ImageView imgLogout;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);

        imgProfile = findViewById(R.id.imgProfile);
        txtEmail = findViewById(R.id.txtEmail);
        editNickname = findViewById(R.id.editNickname);
        editPassword = findViewById(R.id.editPassword);
        editPassword2 = findViewById(R.id.editPassword2);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave = findViewById(R.id.btnSave);
        btnCheck = findViewById(R.id.btnCheck);
        imgLogout = findViewById(R.id.imgLogout);

        String profile = getIntent().getStringExtra("profile");
        userEmail = getIntent().getStringExtra("email");
        nickname = getIntent().getStringExtra("nickname");
        password = getIntent().getStringExtra("password");
        AccessToken = getIntent().getStringExtra("token");

        Glide.with(UserUpdateActivity.this).load(profile).into(imgProfile);
        txtEmail.setText(userEmail);
        editNickname.setText(nickname);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);
            }
        });

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLogout();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                builder.setTitle("회원탈퇴");
                builder.setMessage("정말 탈퇴하시겠습니까?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteId();
                    }
                });
                builder.setNegativeButton("No",null);
                builder.show();
            }
        });

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtNickname = editNickname.getText().toString().trim();

                if ( nickname.equals(txtNickname)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                    builder.setTitle("닉네임 중복체크");
                    builder.setMessage("기존 닉네임 입니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    nicknamecheck = true;
                    AlertDialog ad = builder.create();
                    ad.show();
                    return;
                }else{
                    nicknamecheck =false;
                }
                if(nickname.equals("")){
                    return;
                }
                getIsNickname();
            }

        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 입력값 가져오기
                nickname = editNickname.getText().toString().trim();
                password = editPassword2.getText().toString().trim();

                // 서버에 저장
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if (nickname.equals("")){
                    alert("닉네임");
                }else if (!nicknamecheck){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                    builder.setTitle("중복 확인");
                    builder.setMessage("닉네임을 확인하세요.");
                    builder.setPositiveButton("확인",null);

                    AlertDialog ad = builder.create();
                    ad.show();
                    return;
                }else if (password.equals("")){
                    alert("패스워드");
                    return;
                }try {
                    if (profileImg == null){
                    getUpdate2();
                }else {
                        getUpdate();
                }

                }catch (JSONException e){
                    throw new RuntimeException(e);
                }

            }
        });


    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                Picasso.get().load(uri).into(imgProfile);

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
            profileImg = new File( this.getCacheDir( ), filename );
            FileOutputStream outputStream = new FileOutputStream( profileImg );
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

    public void getUpdate() throws JSONException {


        // {"nickname" : "테스터바보",
        // "password" : "1234",
        // "userEmail":"abc1234@naver.com"}

        JSONObject data = new JSONObject();
        data.put("nickname",nickname);
        data.put("password",password);
        data.put("userEmail",userEmail);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");



        RequestBody requestBody = RequestBody.create(profileImg, MediaType.parse("image/jpg"));
        MultipartBody.Part uploadImgFile = MultipartBody.Part.createFormData("profileImg", profileImg.getName(),requestBody);

        Log.i("aa",profileImg.getAbsolutePath().toString());
        RequestBody requestBodyData = RequestBody.create(String.valueOf(data),MediaType.parse("text/plain"));

        Retrofit retrofit = NetworkClient2.getRetrofitClient(UserUpdateActivity.this);
        UserApi api = retrofit.create(UserApi.class);

        Call<UserRes> call = api.update("Bearer "+ AccessToken,requestBodyData,uploadImgFile);
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                if(response.code()==200){
                    String token = response.body().getAccess_token();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("AccessToken",token);
                    editor.apply();
                    Log.i("userUpdate","success");
                    editor.putString("AccessToken","");
                    Intent intent = new Intent(UserUpdateActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {
                Log.i("userUpdate update","method fail");
            }
        });
    }
    public void getUpdate2() throws JSONException {


        // {"nickname" : "테스터바보",
        // "password" : "1234",
        // "userEmail":"abc1234@naver.com"}

        JSONObject data = new JSONObject();
        data.put("nickname",nickname);
        data.put("password",password);
        data.put("userEmail",userEmail);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");




        RequestBody requestBodyData = RequestBody.create(String.valueOf(data),MediaType.parse("text/plain"));

        Retrofit retrofit = NetworkClient2.getRetrofitClient(UserUpdateActivity.this);
        UserApi api = retrofit.create(UserApi.class);

        Call<UserRes> call = api.updateNotChangeProfile("Bearer "+ AccessToken,requestBodyData);
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                if(response.code()==200){
                    String token = response.body().getAccess_token();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("AccessToken",token);
                    editor.apply();
                    Log.i("userUpdate","success");
                    editor.putString("AccessToken","");
                    Intent intent = new Intent(UserUpdateActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {
                Log.i("userUpdate update","method fail");
            }
        });
    }

    public void deleteId(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(UserUpdateActivity.this);
        UserApi api = retrofit.create(UserApi.class);

        Call<UserRes> call = api.delete("Bearer "+ AccessToken);

        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                if(response.code()==200){
                    SharedPreferences pref = getSharedPreferences(Config.PREFERENCE_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent = new Intent(UserUpdateActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {

            }
        });
    }


    public void getIsNickname(){
        user.setNickname(nickname);
        Retrofit retrofit = NetworkClient2.getRetrofitClient(this);
        RegisterApi api = retrofit.create(RegisterApi.class);
        Call<HashMap<String,String>> call = api.IsNickname(user);
        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {
                if(response.code() == 200 ){
                    HashMap<String,String> data = response.body();
                    String result = data.get("result");
                    String resultCode = data.get("result_code");

                    if(resultCode.equals("1")){
                        nicknamecheck = true;
                    }else{
                        nicknamecheck = false;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
                    builder.setTitle("닉네임 중복체크");
                    builder.setMessage(result);
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }
            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {

            }
        });
    }

    public void alert(String txt){
        AlertDialog.Builder builder = new AlertDialog.Builder(UserUpdateActivity.this);
        builder.setTitle(txt+" 확인");
        builder.setMessage(txt+"(을)를 입력하세요.");
        builder.setPositiveButton("확인",null);

        AlertDialog ad = builder.create();
        ad.show();
    }

    public void getLogout(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(UserUpdateActivity.this);
        LoginApi api = retrofit.create(LoginApi.class);

        Call<UserRes> call = api.Logout("Bearer "+AccessToken);
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {

                SharedPreferences sp = (UserUpdateActivity.this).getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("AccessToken",null);
                editor.apply();
                Intent intent = new Intent(UserUpdateActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {

            }
        });
    }

}