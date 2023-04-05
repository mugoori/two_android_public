package com.example.two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.two.Api.LoginApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FindIdActivity extends AppCompatActivity {
    EditText name;
    Spinner question;
    EditText questionAnswer;
    Button btn;

    int questionNum;
    String Username,UserquestionAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        name = findViewById(R.id.editTextTextPersonName);
        question = findViewById(R.id.spinner);
        questionAnswer = findViewById(R.id.editTextTextPersonName8);
        btn = findViewById(R.id.button);

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
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Username = name.getText().toString().trim();
                UserquestionAnswer = questionAnswer.getText().toString().trim();

                IsId();
            }
        });

    }

    public void IsId(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(FindIdActivity.this);
        LoginApi api = retrofit.create(LoginApi.class);
        User user = new User(Username,questionNum,UserquestionAnswer);
        Call<HashMap<String,String>> call = api.IsId(user);

        call.enqueue(new Callback<HashMap<String,String>>() {
            @Override
            public void onResponse(Call<HashMap<String,String>> call, Response<HashMap<String,String>> response) {

                if(response.code()==200){
                    String userEmail = response.body().get("userEmail");
                    Log.i("test123",userEmail);
                    Intent intent = new Intent(FindIdActivity.this,FindIdResultActivity.class);
                    intent.putExtra("userEmail",userEmail);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(FindIdActivity.this, "아이디가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HashMap<String,String>> call, Throwable t) {
                Log.i("result","fail_call_isId");
            }
        });
    }
}