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

import com.example.two.Api.NetworkClient2;
import com.example.two.Api.PartyApi;
import com.example.two.adapter.OttAdapter;
import com.example.two.config.Config;
import com.example.two.model.Chat;
import com.example.two.model.PartyListRes;
import com.example.two.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UseOTTActivity extends AppCompatActivity {

    String token;

    RecyclerView capRecyclerView;
    ArrayList<Chat> capArrayList = new ArrayList<>();
    RecyclerView memRecyclerView;
    ArrayList<Chat> memArrayList = new ArrayList<>();
    OttAdapter memAdapter;
    OttAdapter capAdapter;
    int memPage=0;
    int capPage=0;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_ottactivity);

        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");

        user = (User) getIntent().getSerializableExtra("user");

        capRecyclerView = findViewById(R.id.capRecyclerView);
        capRecyclerView.setHasFixedSize(true);
        capRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getCapData();
        memRecyclerView = findViewById(R.id.memRecyclerView);
        memRecyclerView.setHasFixedSize(true);
        memRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getMemData();
    }

    public void getMemData(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(UseOTTActivity.this);
        PartyApi api = retrofit.create(PartyApi.class);
        Call<PartyListRes> call = api.getMemeberme("Bearer "+token,memPage);
        call.enqueue(new Callback<PartyListRes>() {
            @Override
            public void onResponse(Call<PartyListRes> call, Response<PartyListRes> response) {
                if(response.code() == 200){
                    List<Chat> list =  response.body().getPartyList();
                    if(list != null) {

                        memArrayList.addAll(list);
                        memAdapter = new OttAdapter(UseOTTActivity.this, memArrayList, user);
                        memRecyclerView.setAdapter(memAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<PartyListRes> call, Throwable t) {

            }
        });
    }
    public void getCapData(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(UseOTTActivity.this);
        PartyApi api = retrofit.create(PartyApi.class);
        Call<PartyListRes> call = api.getCaptainme("Bearer "+token,capPage);
        call.enqueue(new Callback<PartyListRes>() {
            @Override
            public void onResponse(Call<PartyListRes> call, Response<PartyListRes> response) {
                if(response.code() == 200){
                    List<Chat> list =  response.body().getPartyList();
                    if(list != null) {
                        capArrayList.addAll(list);
                        capAdapter = new OttAdapter(UseOTTActivity.this, capArrayList, user);
                        capRecyclerView.setAdapter(capAdapter);



                    }
                }
            }

            @Override
            public void onFailure(Call<PartyListRes> call, Throwable t) {

            }
        });
    }


}