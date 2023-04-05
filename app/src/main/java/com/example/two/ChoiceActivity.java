package com.example.two;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.two.Api.ContentApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.Api.SearchApi;
import com.example.two.adapter.ChoiceAdapter;
import com.example.two.adapter.SeachAdapter;
import com.example.two.config.Config;
import com.example.two.model.Choice;
import com.example.two.model.ChoiceList;
import com.example.two.model.SeachList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChoiceActivity extends AppCompatActivity {

    ImageView imageView;


    RecyclerView recyclerView;

    ArrayList<Choice> choiceArrayList = new ArrayList<>();

    String token;

    ChoiceAdapter adapter;

    int page =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        imageView = findViewById(R.id.imageView);

        recyclerView = findViewById(R.id.recyclerView);
        getLikeData();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChoiceActivity.this));

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

                Log.i("lastPosition" , String.valueOf(lastPosition));
                Log.i("totalcount", String.valueOf(totalCount));

                // 스크롤을 데이터 맨 끝까지 한 상태.
                if (lastPosition + 1 == totalCount) {
                    // 네트워크 통해서 데이터를 받아오고, 화면에 표시!
                    page +=1;
                    addLikeData();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(100,intent);
                finish();
            }
        });



    }

    private void getLikeData() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(ChoiceActivity.this);

        ContentApi api = retrofit.create(ContentApi.class);

        Log.i("AAA", api.toString());


        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");

        Call<ChoiceList> call = api.getcontentLike("Bearer "+token,page);

        call.enqueue(new Callback<ChoiceList>() {
            @Override
            public void onResponse(Call<ChoiceList> call, Response<ChoiceList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요


                    Log.i("SIGN","OK");

                    // 데이터를 받았으니 리사이클러 표시

                    choiceArrayList.addAll(response.body().getContentLike_list());


                    adapter = new ChoiceAdapter(ChoiceActivity.this,choiceArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(ChoiceActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ChoiceList> call, Throwable t) {
                Log.i("ERRER", String.valueOf(t));

            }


        });
    }

    private void addLikeData() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(ChoiceActivity.this);

        ContentApi api = retrofit.create(ContentApi.class);

        Log.i("AAA", api.toString());


        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");

        Call<ChoiceList> call = api.getcontentLike("Bearer "+token,page);

        call.enqueue(new Callback<ChoiceList>() {
            @Override
            public void onResponse(Call<ChoiceList> call, Response<ChoiceList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요


                    Log.i("SIGN","OK");

                    // 데이터를 받았으니 리사이클러 표시

                    choiceArrayList.addAll(response.body().getContentLike_list());


                    adapter = new ChoiceAdapter(ChoiceActivity.this,choiceArrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();



                } else {
                    Toast.makeText(ChoiceActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ChoiceList> call, Throwable t) {
                Log.i("ERRER", String.valueOf(t));

            }


        });
    }
}