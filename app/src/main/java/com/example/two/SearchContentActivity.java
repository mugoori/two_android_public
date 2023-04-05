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
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.two.Api.ContentApi;
import com.example.two.Api.ContentReviewApi;
import com.example.two.Api.DetailApi;
import com.example.two.Api.MovieApi;
import com.example.two.Api.NetworkClient1;
import com.example.two.Api.NetworkClient2;
import com.example.two.adapter.ContentReviewAdapter;
import com.example.two.config.Config;
import com.example.two.model.Actor;
import com.example.two.model.ActorList;
import com.example.two.model.ContentReview;
import com.example.two.model.ContentReviewRes;
import com.example.two.model.Res;
import com.example.two.model.User;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchContentActivity extends AppCompatActivity {
    ImageView posterView;
    ImageView imgback;

    TextView txtTitle;
    TextView txtContent;
    TextView txtDate;
    TextView txtRate;

    int Id;
    String title;
    String date;
    String content;
    String rating;
    String imgurl;

    String gerne;

    String token;

    ImageButton btnChoice;
    ImageButton btnReview;

    FrameLayout frame1;

    int count=0;
    ArrayList<Actor> actorArrayList = new ArrayList<>();

    CircleImageView circle1;

    CircleImageView circle2;

    CircleImageView circle3;

    CircleImageView circle4;

    String language = "ko-KR";
    int tmdbcontentId;

    RecyclerView ReviewRecyclerView;

    int page = 0;



    ArrayList<ContentReview> contentReviewArrayList=new ArrayList<>();
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
        setContentView(R.layout.activity_search_content);

        imgback = findViewById(R.id.imgback);

        posterView = findViewById(R.id.posterView);
        txtTitle = findViewById(R.id.txtOtt);
        txtContent = findViewById(R.id.txtContent);
        txtDate = findViewById(R.id.txtDate);
        txtRate = findViewById(R.id.txtRate);
        btnChoice = findViewById(R.id.btnChoice);
        btnReview = findViewById(R.id.btnReview);
        frame1= findViewById(R.id.frame1);

        ReviewRecyclerView = findViewById(R.id.reviewRecyclerview);
        ReviewRecyclerView.setHasFixedSize(true);
        ReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ReviewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

        user = (User)getIntent().getSerializableExtra("user");

        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);
        circle3 = findViewById(R.id.circle3);
        circle4 = findViewById(R.id.circle4);

        Id = getIntent().getIntExtra("Id",0);
        title =getIntent().getStringExtra("title");
        date = getIntent().getStringExtra("year");
        content = getIntent().getStringExtra("content");
        rating = getIntent().getStringExtra("rating");
        imgurl = getIntent().getStringExtra("ImgUrl");
        gerne = getIntent().getStringExtra("genre");
        tmdbcontentId = getIntent().getIntExtra("tmdbcontentId",0);
        getDetailActor();
        txtTitle.setText(title);
        txtDate.setText(date);
        txtContent.setText(content);
        txtRate.setText(rating);
        Glide.with(SearchContentActivity.this)
                .load(imgurl)
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(posterView);

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                setResult(100,intent);
                finish();
            }
        });

        btnChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (count == 0) {

                        isLike();
                        Toast.makeText(SearchContentActivity.this, "찜했습니다", Toast.LENGTH_SHORT).show();
                        frame1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF737E")));
                        btnChoice.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF737E")));
                        btnChoice.setImageResource(R.drawable.baseline_bookmark_24);
                        count = 1;

                    }else{

                        disLike();
                        Toast.makeText(SearchContentActivity.this, "찜취소했습니다", Toast.LENGTH_SHORT).show();
                        frame1.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        btnChoice.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFF")));
                        btnChoice.setImageResource(R.drawable.baseline_bookmark_border_24);
                        count=0;
                    }


            }
        });
        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchContentActivity.this,ReviewAddActivity.class);
                intent.putExtra("Id",Id);
                intent.putExtra("title",title);
                intent.putExtra("date",date);
                intent.putExtra("imgUrl",imgurl);
                intent.putExtra("genre",gerne);
                launcher.launch(intent);
            }
        });

        getReviewData();

    }



    public void isLike(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(SearchContentActivity.this);

        ContentApi api = retrofit.create(ContentApi.class);

        Log.i("AAA", api.toString());
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");
        Call<Res> call =api.contentLike("Bearer "+token,Id);

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                if (response.isSuccessful()) {
                    return;



                } else {
                    Toast.makeText(SearchContentActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {

            }
        });
    }

    public void disLike(){

        Retrofit retrofit = NetworkClient2.getRetrofitClient(SearchContentActivity.this);

        ContentApi api = retrofit.create(ContentApi.class);

        Log.i("AAA", api.toString());
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        token = sp.getString("AccessToken","");
        Call<Res> call =api.contentDisLike("Bearer "+token,Id);

        call.enqueue(new Callback<Res>() {
            @Override
            public void onResponse(Call<Res> call, Response<Res> response) {
                if (response.isSuccessful()) {
                    return;



                } else {
                    Toast.makeText(SearchContentActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<Res> call, Throwable t) {

            }
        });



    }

    private void getDetailActor(){

        Retrofit retrofit = NetworkClient1.getRetrofitClient(SearchContentActivity.this);

        DetailApi api = retrofit.create(DetailApi.class);

        Log.i("AAA", api.toString());

        Call<ActorList> call = api.getActor(tmdbcontentId,Config.key,language);

        call.enqueue(new Callback<ActorList>() {
            @Override
            public void onResponse(Call<ActorList> call, Response<ActorList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요

                    // 데이터를 받았으니 리사이클러 표시

                    actorArrayList.addAll(response.body().getCast());

                    Actor actor1 = actorArrayList.get(0);
                    Glide.with(SearchContentActivity.this)
                            .load("https://image.tmdb.org/t/p/w45"+actor1.getProfile_path())
                            .placeholder(R.drawable.baseline_person_outline_24)
                            .into(circle1);

                    Actor actor2 = actorArrayList.get(1);
                    Glide.with(SearchContentActivity.this)
                            .load("https://image.tmdb.org/t/p/w45"+actor2.getProfile_path())
                            .placeholder(R.drawable.baseline_person_outline_24)
                            .into(circle2);

                    Actor actor3 = actorArrayList.get(2);
                    Glide.with(SearchContentActivity.this)
                            .load("https://image.tmdb.org/t/p/w45"+actor3.getProfile_path())
                            .placeholder(R.drawable.baseline_person_outline_24)
                            .into(circle3);

                    Actor actor4 = actorArrayList.get(3);
                    Glide.with(SearchContentActivity.this)
                            .load("https://image.tmdb.org/t/p/w45"+actor4.getProfile_path())
                            .placeholder(R.drawable.baseline_person_outline_24)
                            .into(circle4);





                    // 오프셋 처리하는 코드




                } else {
                    Toast.makeText(SearchContentActivity.this, "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ActorList> call, Throwable t) {


            }


        });

    }

    public void getReviewData(){
        Retrofit retrofit = NetworkClient2.getRetrofitClient(SearchContentActivity.this);
        ContentReviewApi api = retrofit.create(ContentReviewApi.class);
        Call<ContentReviewRes> call = api.getAllReview(Id,page);
        call.enqueue(new Callback<ContentReviewRes>() {
            @Override
            public void onResponse(Call<ContentReviewRes> call, Response<ContentReviewRes> response) {
                if(response.code()==200){
                    List<ContentReview> contentReviewList = response.body().getContentReviewList();
                    if ( contentReviewList != null) {
                    contentReviewArrayList.addAll(contentReviewList);
                    contentReviewAdapter = new ContentReviewAdapter(SearchContentActivity.this,contentReviewArrayList,user,0);
                    ReviewRecyclerView.setAdapter(contentReviewAdapter);

                    }
                }
            }

            @Override
            public void onFailure(Call<ContentReviewRes> call, Throwable t) {

            }
        });
    }
    private void addReviewData() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(SearchContentActivity.this);
        ContentReviewApi api = retrofit.create(ContentReviewApi.class);
        Call<ContentReviewRes> call = api.getAllReview(Id,page);
        call.enqueue(new Callback<ContentReviewRes>() {
            @Override
            public void onResponse(Call<ContentReviewRes> call, Response<ContentReviewRes> response) {
                if(response.code()==200){
                    List<ContentReview> contentReviewList = response.body().getContentReviewList();
                    if ( contentReviewList != null) {
                        contentReviewArrayList.addAll(contentReviewList);
                        contentReviewAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ContentReviewRes> call, Throwable t) {

            }
        });
    }
}