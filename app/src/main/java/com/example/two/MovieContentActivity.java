package com.example.two;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.two.Api.DetailApi;
import com.example.two.Api.NetworkClient1;
import com.example.two.config.Config;
import com.example.two.model.Actor;
import com.example.two.model.ActorList;
import com.example.two.model.DetailList;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieContentActivity extends AppCompatActivity {

    ImageView posterView;

    TextView txtTitle;
    TextView txtContent;
    TextView txtDate;
    TextView txtRate;
    ImageView imgback;

    ImageButton btnChoice;
    ImageButton btnReview;

    CircleImageView circle1;

    CircleImageView circle2;

    CircleImageView circle3;

    CircleImageView circle4;

    ArrayList <Actor> actorArrayList = new ArrayList<>();

    String language = "ko-KR";

    int Id;

    int index;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_content);

        posterView = findViewById(R.id.posterView);
        txtTitle = findViewById(R.id.txtOtt);
        txtContent = findViewById(R.id.txtContent);
        txtDate = findViewById(R.id.txtDate);
        txtRate = findViewById(R.id.txtRate);
        btnChoice = findViewById(R.id.btnChoice);
        btnReview = findViewById(R.id.btnReview);
        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);
        circle4 = findViewById(R.id.circle4);
        imgback = findViewById(R.id.imgback);
        Intent intent = getIntent();
        Id = intent.getIntExtra("id",0);
        Log.i("ID",String.valueOf(Id));
//        getDetail();
//        getDetailActor();

        // 백 이미지 처리
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(100,intent);
                finish();
            }
        });


        // 찜했어요 버튼 클릭 처리
        btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



        // 리뷰작성 버튼 클릭 처리
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ReviewAdd 액티비티로 이동
                Intent intent = new Intent(MovieContentActivity.this,ReviewAddActivity.class);

                startActivity(intent);

            }
        });
    }

//    private void getDetail() {
//        Retrofit retrofit = NetworkClient1.getRetrofitClient(MovieContentActivity.this);
//
//        DetailApi api = retrofit.create(DetailApi.class);
//
//        Log.i("AAA", api.toString());
//
//        Call<DetailList> call = api.getDetail(Id,Config.key,language);
//
//        call.enqueue(new Callback<DetailList>() {
//            @Override
//            public void onResponse(Call<DetailList> call, Response<DetailList> response) {
//
//                if (response.isSuccessful()) {
//                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
//                    // 초기화 코드가 필요
//                    DetailList detail = response.body();
//
//                    txtTitle.setText(detail.getTitle());
//                    txtDate.setText(detail.getDate());
//                    txtContent.setText(detail.getContent());
//                    txtRate.setText(detail.getRate());
//                    Glide.with(MovieContentActivity.this)
//                            .load("https://image.tmdb.org/t/p/w500"+detail.getPoster_path())
//                            .into(posterView);
//
//
//
//                } else {
//                    Toast.makeText(MovieContentActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<DetailList> call, Throwable t) {
//
//
//            }
//
//
//        });
//    }

//    private void getDetailActor(){
//
//        Retrofit retrofit = NetworkClient1.getRetrofitClient(MovieContentActivity.this);
//
//        DetailApi api = retrofit.create(DetailApi.class);
//
//        Log.i("AAA", api.toString());
//
//        Call<ActorList> call = api.getActor(Id,Config.key,language);
//
//        call.enqueue(new Callback<ActorList>() {
//            @Override
//            public void onResponse(Call<ActorList> call, Response<ActorList> response) {
//
//                if (response.isSuccessful()) {
//                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
//                    // 초기화 코드가 필요
//
//                    // 데이터를 받았으니 리사이클러 표시
//
//                    actorArrayList.addAll(response.body().getCast());
//
//                    Actor actor1 = actorArrayList.get(0);
//                    Glide.with(MovieContentActivity.this)
//                            .load("https://image.tmdb.org/t/p/w45"+actor1.getProfile_path())
//                            .placeholder(R.drawable.baseline_person_outline_24)
//                            .into(circle1);
//
//                    Actor actor2 = actorArrayList.get(1);
//                    Glide.with(MovieContentActivity.this)
//                            .load("https://image.tmdb.org/t/p/w45"+actor2.getProfile_path())
//                            .placeholder(R.drawable.baseline_person_outline_24)
//                            .into(circle2);
//
//                    Actor actor3 = actorArrayList.get(2);
//                    Glide.with(MovieContentActivity.this)
//                            .load("https://image.tmdb.org/t/p/w45"+actor3.getProfile_path())
//                            .placeholder(R.drawable.baseline_person_outline_24)
//                            .into(circle3);
//
//                    Actor actor4 = actorArrayList.get(3);
//                    Glide.with(MovieContentActivity.this)
//                            .load("https://image.tmdb.org/t/p/w45"+actor4.getProfile_path())
//                            .placeholder(R.drawable.baseline_person_outline_24)
//                            .into(circle4);
//
//
//
//
//
//                    // 오프셋 처리하는 코드
//
//
//
//
//                } else {
//                    Toast.makeText(MovieContentActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ActorList> call, Throwable t) {
//
//
//            }
//
//
//        });
//
//    }
}