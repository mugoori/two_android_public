package com.example.two;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import com.example.two.Api.ContentReviewApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.adapter.ContentReviewAdapter;
import com.example.two.config.Config;
import com.example.two.model.ContentReview;
import com.example.two.model.User;
import com.example.two.model.reView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyReviewActivity extends AppCompatActivity {

    ImageView imageView24;

    RecyclerView ruRecyclerview;
    String token;
    int page = 0;
    ArrayList<ContentReview> contentReviewArrayList = new ArrayList<>();
    ContentReviewAdapter contentReviewAdapter;
    User user;
    ContentReview contentReview;
    public ActivityResultLauncher<Intent> launcher =
            registerForActivityResult( new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>(){
                        @Override
                        public void onActivityResult(ActivityResult result){
                            if(result.getResultCode() == 100){
                                Intent intent = result.getData();
                                contentReview =(ContentReview) intent.getSerializableExtra("contentReview");
                                Log.i("asdasasd",contentReview.getContent());
                                contentReviewArrayList.add(contentReview);
                                contentReviewAdapter.notifyDataSetChanged();
                            }else if(result.getResultCode() == 101){
                                Intent intent = result.getData();
                                contentReview =(ContentReview) intent.getSerializableExtra("contentReview");
                                int posion = intent.getIntExtra("contentReviewPosition",0);
                                contentReviewArrayList.remove(posion);
                                contentReviewArrayList.add(posion,contentReview);
                                contentReviewAdapter.notifyDataSetChanged();
                            }
                        }
                    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_review);
        imageView24 = findViewById(R.id.imageView24);

        ruRecyclerview = findViewById(R.id.RUrecyclerview);
        ruRecyclerview.setHasFixedSize(true);
        ruRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        ruRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int totalCount = recyclerView.getAdapter().getItemCount();
                if (lastPosition + 1 == totalCount) {
                    // 네트워크 통해서 데이터를 받아오고, 화면에 표시!
                    page +=1;
                    addReviewData();
                }
            }
        });
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");

        user = (User) getIntent().getSerializableExtra("user");

        getMyReviewData();

        imageView24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(100,intent);
                finish();
            }
        });
    }

    public void getMyReviewData(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(MyReviewActivity.this);
        ContentReviewApi api = retrofit.create(ContentReviewApi.class);
        Call<reView> call = api.myReview("Bearer "+token,page);
        call.enqueue(new Callback<reView>() {
            @Override
            public void onResponse(Call<reView> call, Response<reView> response) {
                if(response.code()==200){
                    List<ContentReview> list = response.body().getContentReviewList();
                    contentReviewArrayList.addAll(list);
                    contentReviewAdapter = new ContentReviewAdapter(MyReviewActivity.this,
                            contentReviewArrayList,user,1);
                    ruRecyclerview.setAdapter(contentReviewAdapter);
                }
            }

            @Override
            public void onFailure(Call<reView> call, Throwable t) {

            }
        });
    }
    public void addReviewData(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(MyReviewActivity.this);
        ContentReviewApi api = retrofit.create(ContentReviewApi.class);
        Call<reView> call = api.myReview("Bearer "+token,page);
        call.enqueue(new Callback<reView>() {
            @Override
            public void onResponse(Call<reView> call, Response<reView> response) {
                if(response.code()==200){
                    List<ContentReview> list = response.body().getContentReviewList();

                    if(list != null) {
                        contentReviewArrayList.addAll(list);
                        contentReviewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<reView> call, Throwable t) {

            }
        });
    }
}