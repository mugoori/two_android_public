package com.example.two;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.two.Api.LoginApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindPasswordResultActivity extends AppCompatActivity {
    EditText editpassword,editpasswordcheck;
    String userEmail;
    String password;
    TextView txtcheck;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password_result);

        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");

        editpassword = findViewById(R.id.editTextTextPassword5);
        editpasswordcheck = findViewById(R.id.editTextTextPassword6);
        txtcheck = findViewById(R.id.check);

        editpasswordcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password = editpassword.getText().toString().trim();
                String passwordcheck = editpasswordcheck.getText().toString().trim();

                if(password.equals(passwordcheck)){
                    txtcheck.setText("비밀번호가 같습니다.");
                }else{
                    txtcheck.setText("비밀번호가 다릅니다.");
                }
            }
        });

        btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChangedPassword();
            }
        });

    }

    public void getChangedPassword() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(FindPasswordResultActivity.this);
        LoginApi api = retrofit.create(LoginApi.class);
        User user = new User();
        user.setUserEmail(userEmail);
        user.setPassword(password);

        Call<HashMap<String,String>> call = api.Changepassword(user);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(response.code() == 200 ){
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPasswordResultActivity.this);
                    builder.setTitle("변경 성공");
                    builder.setMessage("로그인 페이지로 이동합니다.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }else{

                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {

            }
        });

    }
}