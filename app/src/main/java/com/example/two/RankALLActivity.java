package com.example.two;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.two.Api.ChatApi;
import com.example.two.Api.MovieApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.adapter.ChatRoomAdapter;
import com.example.two.adapter.RankAllAdapter;
import com.example.two.config.Config;
import com.example.two.model.ChatRoomList;
import com.example.two.model.Movie;
import com.example.two.model.MovieList;
import com.example.two.model.MovieRank;
import com.example.two.model.MovieRankList;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RankALLActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<MovieRank> movieArrayList = new ArrayList<>();
    RankAllAdapter adapter;



    ImageView imgBack;

    String language = "ko-KR";
    String desc = "popularity.desc";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_allactivity);



        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(RankALLActivity.this));
        getMovieNetworkData();

        imgBack = findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });
    }

    private void getMovieNetworkData() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(RankALLActivity.this);

        MovieApi api = retrofit.create(MovieApi.class);

        Log.i("AAA", api.toString());

        Call<MovieRankList> call = api.getrankMovie();

        call.enqueue(new Callback<MovieRankList>() {
            @Override
            public void onResponse(Call<MovieRankList> call, Response<MovieRankList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요
                    movieArrayList.clear();

                    // 데이터를 받았으니 리사이클러 표시

                    movieArrayList.addAll(response.body().getRank());


                    // 오프셋 처리하는 코드


                    adapter = new RankAllAdapter(RankALLActivity.this,movieArrayList);
                    recyclerView.setAdapter(adapter);
                    Log.i("RECYCLE", adapter.toString());

                } else {
                    Toast.makeText(RankALLActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<MovieRankList> call, Throwable t) {


            }


        });
    }
}