package com.example.two;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.two.Api.NetworkClient2;
import com.example.two.Api.SearchApi;
import com.example.two.adapter.SeachAdapter;
import com.example.two.model.Seach;
import com.example.two.model.SeachList;
import com.example.two.model.SeachList2;
import com.example.two.model.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TvALLActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView imageView9;

    SeachAdapter adapter;
    ArrayList<Seach> seachArrayList2 = new ArrayList<>();
    String Keyword;

    int offset= 0;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_all);
        recyclerView = findViewById(R.id.recyclerView);
        imageView9 = findViewById(R.id.imageView9);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TvALLActivity.this));

        Intent intent = new Intent();
        user = (User)getIntent().getSerializableExtra("user");
        Keyword=getIntent().getStringExtra("keyword");
        Log.i("STRING",Keyword);
        getNetworkSearchTvData(Keyword);

        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(100,intent);
                finish();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // 맨 마지막 데이터가 화면에 보이면!!!!
                // 네트워크 통해서 데이터를 추가로 받아와라!!
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();

                // 스크롤을 데이터 맨 끝까지 한 상태.
                if (lastPosition + 1 == totalCount) {
                    // 네트워크 통해서 데이터를 받아오고, 화면에 표시!

                    addNetworkSearchTvData(Keyword);

                }
            }
        });

    }



    private void getNetworkSearchTvData(String keyword) {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(TvALLActivity.this);

        SearchApi api = retrofit.create(SearchApi.class);

        Log.i("AAA", api.toString());

        String type = "tv";


        Map<String, String> map = new LinkedHashMap<>();
        map.put("keyword", Keyword);
        map.put("genre", "");
        map.put("limit", "");
        map.put("rating","");
        map.put("year","");
        map.put("offset","");
        map.put("filtering","");
        map.put("sort","");




        Call<SeachList2> call = api.getSeachTv(type,map);

        call.enqueue(new Callback<SeachList2>() {
            @Override
            public void onResponse(Call<SeachList2> call, Response<SeachList2> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요
                    seachArrayList2.clear();

                    Log.i("SIGN","OK");

                    // 데이터를 받았으니 리사이클러 표시

                    seachArrayList2.addAll(response.body().getTv());




                    adapter = new SeachAdapter(TvALLActivity.this,seachArrayList2,user);

                    recyclerView.setAdapter(adapter);






                } else {
                    Toast.makeText(TvALLActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<SeachList2> call, Throwable t) {
                Log.i("ERRER", String.valueOf(t));

            }


        });
    }

    private void addNetworkSearchTvData(String keyword) {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(TvALLActivity.this);

        SearchApi api = retrofit.create(SearchApi.class);

        Log.i("AAA", api.toString());

        String type = "tv";


        Map<String, String> map = new LinkedHashMap<>();
        map.put("keyword", Keyword);
        map.put("genre", "");
        map.put("limit", "");
        map.put("rating","");
        map.put("year","");
        map.put("offset",String.valueOf(offset+10));
        map.put("filtering","");
        map.put("sort","");




        Call<SeachList2> call = api.getSeachTv(type,map);

        call.enqueue(new Callback<SeachList2>() {
            @Override
            public void onResponse(Call<SeachList2> call, Response<SeachList2> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요


                    Log.i("SIGN","OK");

                    // 데이터를 받았으니 리사이클러 표시

                    seachArrayList2.addAll(response.body().getTv());




                    adapter = new SeachAdapter(TvALLActivity.this,seachArrayList2,user);

                    recyclerView.setAdapter(adapter);






                } else {
                    Toast.makeText(TvALLActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<SeachList2> call, Throwable t) {
                Log.i("ERRER", String.valueOf(t));

            }


        });
    }
}