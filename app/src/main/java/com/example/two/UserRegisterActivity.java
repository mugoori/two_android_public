package com.example.two;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.two.model.User;
import com.example.two.model.UserRes;

import com.google.android.gms.common.util.IOUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import com.example.two.Api.NetworkClient2;
import com.example.two.config.Config;
import com.example.two.Api.RegisterApi;
import com.example.two.model.Res;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
//import org.apache.commons.io.IOUtils;
public class UserRegisterActivity extends AppCompatActivity {
    Button buttonRegister;

    Button btnEmail;
    Button btnNickname;
    EditText editTextTextEmailAddress;
    EditText editTextTextPassword;
    EditText editTextTextNickName;

    EditText editName;

    EditText editAnswer;

    EditText editAge;
    CircleImageView circle_iv;

    Spinner genderSp;
    Spinner questionSp;

    String userEmail;
    String password;
    String nickname;
    String name;
    String questionAnswer;
    String gender;
    String age;
    String questionNum;

    Boolean emailcheck = false;
    Boolean nicknamecheck = false;
    Boolean passwordcheck = false;
    User user = new User();

    File profileImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);
        editTextTextNickName = findViewById(R.id.editTextTextNickName);
        editAge = findViewById(R.id.editAge);
        editName = findViewById(R.id.editName);
        editAnswer = findViewById(R.id.editAnswer);

        btnEmail = findViewById(R.id.button5);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail = editTextTextEmailAddress.getText().toString().trim();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(userEmail.equals("") || !pattern.matcher(userEmail).matches()){

                    return;
                }
                getIsUserEmail();
            }
        });
        btnNickname =findViewById(R.id.button6);
        btnNickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nickname = editTextTextNickName.getText().toString().trim();
                if(nickname.equals("")){
                    return;
                }
                getIsNickname();
            }
        });

        // 스피너
        genderSp = findViewById(R.id.genderSp);
        ArrayAdapter adapterGender = ArrayAdapter.createFromResource(this,R.array.gender,android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSp.setAdapter(adapterGender);
        genderSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        questionSp = findViewById(R.id.questionSp);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.find, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        questionSp.setAdapter(adapter);
        questionSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               questionNum= String.valueOf(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        circle_iv = findViewById(R.id.circle_iv);
        circle_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);
            }
        });
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = editTextTextPassword.getText().toString().trim();
                name = editName.getText().toString().trim();
                age = editAge.getText().toString().trim();
                questionAnswer = editAnswer.getText().toString().trim();
                userEmail = editTextTextEmailAddress.getText().toString().trim();
                nickname = editTextTextNickName.getText().toString().trim();
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                if(userEmail.equals("")){
                    alert("이메일");
                    return;
                }else if (!pattern.matcher(userEmail).matches()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
                    builder.setTitle("이메일 확인");
                    builder.setMessage("이메일을 정확하게 입력해주세요.");
                    builder.setPositiveButton("확인",null);
                    AlertDialog ad = builder.create();
                    ad.show();
                    return;
                }
                else if (!emailcheck){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
                    builder.setTitle("중복 확인");
                    builder.setMessage("이메일을 확인하세요.");
                    builder.setPositiveButton("확인",null);
                    AlertDialog ad = builder.create();
                    ad.show();
                    return;
                }else if(password.equals("")){
                    alert("비밀번호");
                    return;
                }
                else if(password.length() > 12 || password.length() < 4){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
                    builder.setTitle("비밀번호 확인");
                    builder.setMessage("비밀번호는 4자리 이상 12자리 이하 입니다.");
                    builder.setPositiveButton("확인",null);
                    AlertDialog ad = builder.create();
                    ad.show();
                    return;
                }else if(nickname.equals("")){
                    alert("닉네임");
                    return;
                }else if (!nicknamecheck){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
                    builder.setTitle("중복 확인");
                    builder.setMessage("닉네임을 확인하세요.");
                    builder.setPositiveButton("확인",null);

                    AlertDialog ad = builder.create();
                    ad.show();
                    return;
                }
                if(name.equals("")) {
                    alert("이름");
                    return;
                }
                else if(age.equals("")){
                    alert("나이");
                    return;
                }
                else if(questionAnswer.equals("")){
                    alert("본인 확인 질문");
                    return;
                }
                else{
                    passwordcheck = true;
                }
                if(profileImg == null){
                    defaultImage();

                }
                try {
                    getRegister();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    public void getIsUserEmail(){
        user.setUserEmail(userEmail);

        Retrofit retrofit = NetworkClient2.getRetrofitClient(this);
        RegisterApi api = retrofit.create(RegisterApi.class);
        Call<HashMap<String,String>> call = api.IsEmail(user);
        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {

                if(response.code() == 200 ){
                    HashMap<String,String> data = response.body();
                    String result = data.get("result");
                    String resultCode = data.get("result_code");
                    if(resultCode.equals("1")){
                        emailcheck = true;
                    }else{
                        emailcheck=false;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
                    builder.setTitle("이메일 중복체크");
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
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.i("UserRegisterActivity_fail","isemail");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(UserRegisterActivity.this);
        builder.setTitle(txt+" 확인");
        builder.setMessage(txt+"(을)를 입력하세요.");
        builder.setPositiveButton("확인",null);

        AlertDialog ad = builder.create();
        ad.show();
    }

    public void getRegister() throws JSONException {

       // {"name":"테스트",
        // "nickname" : "테스터바보",
        // "userEmail":"abc1234@naver.com",
        // "password" : "1234",
        // "gender" : "1",
        // "age" :"28",
        // "questionNum":"1",
        // "questionAnswer":"인천"}

        JSONObject data = new JSONObject();
        data.put("name",name);
        data.put("nickname",nickname);
        data.put("userEmail",userEmail);
        data.put("password",password);
        data.put("gender",gender);
        data.put("age",age);
        data.put("questionNum",questionNum);
        data.put("questionAnswer",questionAnswer);

        RequestBody requestBody = RequestBody.create(profileImg,MediaType.parse("image/jpg"));
        MultipartBody.Part uploadImgFile = MultipartBody.Part.createFormData("profileImg", profileImg.getName(),requestBody);


        RequestBody requestBodyData = RequestBody.create(String.valueOf(data),MediaType.parse("text/plain"));

        Retrofit retrofit = NetworkClient2.getRetrofitClient(UserRegisterActivity.this);
        RegisterApi api = retrofit.create(RegisterApi.class);

        Call<UserRes> call = api.resgister(requestBodyData,uploadImgFile);
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                if(response.code()==200){
                    String token = response.body().getAccess_token();
                    SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("AccessToken",token);
                    editor.apply();
                    Log.i("userRegister","success");
                    Intent intent = new Intent(UserRegisterActivity.this,UserChoiceActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {
                Log.i("UseRegister register","method fail");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                Uri uri = data.getData();
                Picasso.get().load(uri).into(circle_iv);

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
    public void defaultImage(){
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.profile);
            String Directory = this.getFilesDir().toString()+File.separator;
            File file = new File(Directory,"profile.png");
            FileOutputStream outstream = null;
            try {
                 outstream= new FileOutputStream(file);
                 bm.compress(Bitmap.CompressFormat.PNG,100,outstream);
                 outstream.close();

            }catch (FileNotFoundException e){
                throw  new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            profileImg = new File(Directory,"profile.png");
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




