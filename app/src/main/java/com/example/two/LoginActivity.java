package com.example.two;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.two.Api.LoginApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.config.Config;
import com.example.two.model.User;
import com.example.two.model.UserRes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    TextView txtRegister;
    TextView txtFindId;
    TextView txtFindPassword;
    Button btnLogin;

    String UserEmail;
    String password;

    EditText editUserEmail,editPassword;

    String AccessToken;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);

        AccessToken = sp.getString("AccessToken","");

        if ( AccessToken != "" ){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnLogin = findViewById(R.id.btnLogin);
        txtFindId = findViewById(R.id.txtFindId);
        txtFindPassword = findViewById(R.id.txtFindPassword);
        txtRegister = findViewById(R.id.txtRegister);

        editUserEmail = findViewById(R.id.editTextTextEmailAddress2);
        editPassword = findViewById(R.id.editTextTextPassword4);

        // 비밀번호 찾기 텍스트뷰 이벤트 처리
        txtFindPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,FindPasswordActivity.class);
                startActivity(intent);
            }
        });

        // 아이디 찾기 텍스트뷰 이벤트 처리
        txtFindId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,FindIdActivity.class);
                startActivity(intent);

            }
        });

        // 회원가입 텍스트뷰 이벤트 처리
        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,UserRegisterActivity.class);
                startActivity(intent);
            }
        });


        // 로그인 버튼 이벤트 처리
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEmail = editUserEmail.getText().toString().trim();
                password = editPassword.getText().toString().trim();
                getLogin();
            }
        });
    }

    public void getLogin(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(LoginActivity.this);
        LoginApi api = retrofit.create(LoginApi.class);
        User user = new User(UserEmail,password);
        Call<UserRes> call = api.Login(user);
        call.enqueue(new Callback<UserRes>() {
            @Override
            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                if(response.code()==200){
                    String token = response.body().getAccess_token();

                    SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("AccessToken",token);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle("로그인 실패");
                    builder.setMessage("회원정보가 존재하지 않습니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();

                }
            }

            @Override
            public void onFailure(Call<UserRes> call, Throwable t) {
                Log.i("getLoginMethod","call_failler");
            }
        });
    }
}