package com.example.two;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.two.Api.LoginApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindPasswordActivity extends AppCompatActivity {
    EditText editUserEmail,editQuestionAnswer;


    Spinner question;

    int questionNum;

    Button btn;

    String userEmail;
    String questionAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");

        editUserEmail = findViewById(R.id.editTextTextPersonName);
        editUserEmail.setText(userEmail);
        editQuestionAnswer = findViewById(R.id.editTextTextPersonName8);



        question = findViewById(R.id.spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.find, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        question.setAdapter(adapter);
        question.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                questionNum = i;
                Log.i("test", String.valueOf(questionNum));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionAnswer = editQuestionAnswer.getText().toString().trim();
                userEmail = editUserEmail.getText().toString().trim();
                getIsPassword();
            }
        });
    }

    public void getIsPassword(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(FindPasswordActivity.this);
        LoginApi api = retrofit.create(LoginApi.class);
        User user = new User();
        user.setUserEmail(userEmail);
        user.setQuestionNum(questionNum);
        user.setQuestionAnswer(questionAnswer);
        Log.i("user_test",user.getUserEmail());
        Call<HashMap<String,String>> call = api.Ispassword(user);
        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(response.code() == 200 ){
                    Intent intent = new Intent(FindPasswordActivity.this,FindPasswordResultActivity.class);
                    intent.putExtra("userEmail",userEmail);
                    startActivity(intent);
                    finish();
                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(FindPasswordActivity.this);
                    builder.setTitle("조회 실패");
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
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.i("FindpasswordActivity_getispassword_method","Fail");
            }
        });
    }
}