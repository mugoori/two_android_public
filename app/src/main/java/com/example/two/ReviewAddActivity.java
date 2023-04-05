package com.example.two;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.two.Api.NetworkClient2;
import com.example.two.Api.ReviewApi;
import com.example.two.config.Config;
import com.example.two.model.ContentReview;
import com.example.two.model.reView;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ReviewAddActivity extends AppCompatActivity {


    Button btnSave;

    String token;
    int Id;

    String title;
    String content;

    String imgurl;

    ImageView reviewPoster;
    TextView reviewTitle;
    TextView reviewInfo;

    float ratingscore;


    EditText editReview;


    RatingBar ratingBar2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_add);

        reviewPoster = findViewById(R.id.reviewPoster);
        reviewTitle = findViewById(R.id.title);
        reviewInfo = findViewById(R.id.reviewInfo);
        btnSave = findViewById(R.id.btnSave);
        editReview= findViewById(R.id.editReview);
        ratingBar2 = findViewById(R.id.ratingBar2);



        ratingBar2.setOnRatingBarChangeListener(new Listener());

        Glide.with(ReviewAddActivity.this)
                .load(getIntent().getStringExtra("imgUrl"))
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(reviewPoster);

        reviewTitle.setText(getIntent().getStringExtra("title"));
        reviewInfo.setText(getIntent().getStringExtra("date").substring(0,4)+"/"+getIntent().getStringExtra("genre"));


        // 작성완료 버튼 처리
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeUserReView();

            }
        });
    }

    class Listener implements RatingBar.OnRatingBarChangeListener
    {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            ratingBar2.setRating(rating);
            ratingscore =rating;
            Log.i("RANK", String.valueOf(rating));

        }
    }

    public void makeUserReView(){
        Intent intent = getIntent();
        Id = intent.getIntExtra("Id",0);

        title = intent.getStringExtra("title");
        content = editReview.getText().toString().trim();
        Map<String, String> map = new LinkedHashMap<>();
        map.put("title", title );
        map.put("content", content);
        map.put("userRating", String.valueOf(ratingscore));

        Retrofit retrofit = NetworkClient2.getRetrofitClient(ReviewAddActivity.this);

        ReviewApi api = retrofit.create(ReviewApi.class);

        Log.i("AAA", api.toString());
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");



        Call<reView> call =api.makeReview(Id,"Bearer "+token,map);

        call.enqueue(new Callback<reView>() {
            @Override
            public void onResponse(Call<reView> call, Response<reView> response) {
                if (response.code() == 200) {

                    ContentReview contentReview = response.body().getContentReviewList().get(0);
                    Log.i("정상적 작동 여부..",contentReview.getContent());
                    Intent intent1 = new Intent();
                    intent1.putExtra("contentReview",(Serializable) contentReview);
                    setResult(100,intent1);
                    finish();



                } else {
                    Toast.makeText(ReviewAddActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<reView> call, Throwable t) {

            }
        });

    }
}