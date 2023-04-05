package com.example.two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FindIdResultActivity extends AppCompatActivity {
    TextView textView;
    Button loginbtn;
    Button ispasswordbtn;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id_result);
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("userEmail");

        textView = findViewById(R.id.textView52);

        textView.setText(userEmail+"입니다.");

        loginbtn =findViewById(R.id.button3);
        ispasswordbtn = findViewById(R.id.button4);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ispasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FindIdResultActivity.this,FindPasswordActivity.class);
                intent1.putExtra("userEmail",userEmail);
                startActivity(intent1);
                finish();
            }
        });

    }
}
