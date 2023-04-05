package com.example.two;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.two.Api.ContentReviewApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.config.Config;
import com.example.two.model.ContentReview;
import com.example.two.model.reView;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewUpdateActivity extends AppCompatActivity {

    CircleImageView circleImageView;

    TextView textView;

    EditText editText;

    Button button;

    ContentReview contentReview;

    String token;

    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_update);

        circleImageView = findViewById(R.id.updateprofile);
        textView = findViewById(R.id.txttitle);
        editText = findViewById(R.id.editcontent);
        button = findViewById(R.id.btnupdate);

        Intent intent = getIntent();
        contentReview = (ContentReview) intent.getSerializableExtra("contentReview");
        position = intent.getIntExtra("contentReviewPosition",0);
        textView.setText(contentReview.getTitle());
        editText.setText(contentReview.getContent());

        Glide.with(ReviewUpdateActivity.this)
                .load(contentReview.getProfileImgUrl())
                .into(circleImageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewUpdate();
            }
        });

    }

    public void reviewUpdate(){

        int contentId = Integer.parseInt(contentReview.getContentId());
        int reviewId = Integer.parseInt(contentReview.getContentReviewId());

        String title = textView.getText().toString().trim();
        String content = editText.getText().toString().trim();

        contentReview.setTitle(title);
        contentReview.setContent(content);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");

        Retrofit retrofit = NetworkClient2.getRetrofitClient(ReviewUpdateActivity.this);

        ContentReviewApi api = retrofit.create(ContentReviewApi.class);

        Call<reView> call =api.UpdateReview("Bearer "+token,contentId,reviewId,contentReview);

        call.enqueue(new Callback<reView>() {
            @Override
            public void onResponse(Call<reView> call, Response<reView> response) {

                ContentReview contentReview = response.body().getContentReviewList().get(0);

                Intent intent = new Intent();
                intent.putExtra("contentReview",contentReview);
                intent.putExtra("contentReviewPosition",position);
                setResult(101,intent);
                finish();
            }

            @Override
            public void onFailure(Call<reView> call, Throwable t) {
                return;
            }
        });
    }
}