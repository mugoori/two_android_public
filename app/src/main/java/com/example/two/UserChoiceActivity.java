package com.example.two;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.two.Api.LoginApi;
import com.example.two.Api.NetworkClient2;
import com.example.two.config.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class UserChoiceActivity extends AppCompatActivity {
    ImageView actionView;
    ImageView adventureView;
    ImageView comedyView;
    ImageView documentaryView;
    ImageView dramaView;
    ImageView fantasyView;
    ImageView historyView;
    ImageView horrorView;
    ImageView mysteryView;
    ImageView crimeView;
    ImageView romanceView;
    ImageView scienceView;
    ImageView thrillerView;
    ImageView warView;
    ImageView westernView;
    ImageView musicView;
    ImageView kidsView;
    ImageView familyView;
    ImageView animationView;
    ImageView realityView;

    Button btnGenreChoice;

    int count = 0;

    ArrayList<String> genre = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_choice);
        actionView=findViewById(R.id.actionView);
        adventureView=findViewById(R.id.adventureView);
        comedyView=findViewById(R.id.comedyView);
        documentaryView=findViewById(R.id.documentaryView);
        dramaView=findViewById(R.id.dramaView);
        fantasyView=findViewById(R.id.fantasyView);
        historyView=findViewById(R.id.historyView);
        horrorView=findViewById(R.id.horrorView);
        mysteryView=findViewById(R.id.mysteryView);
        crimeView=findViewById(R.id.crimeView);
        romanceView=findViewById(R.id.romanceView);
        scienceView=findViewById(R.id.scienceView);
        warView=findViewById(R.id.warView);
        westernView=findViewById(R.id.westernView);
        musicView=findViewById(R.id.musicView);
        kidsView=findViewById(R.id.kidsView);
        familyView=findViewById(R.id.familyView);
        animationView=findViewById(R.id.animationView);
        realityView=findViewById(R.id.realityView);
        thrillerView=findViewById(R.id.thrillerView);
        btnGenreChoice=findViewById(R.id.btnGenreChoice);


        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/3PCRWLeqp5y20k6XVzcamZR3BWF.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(actionView);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());

                if(view.isSelected()){
                    actionView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("0");
                }
                else {
                    actionView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("0");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/rKgvctIuPXyuqOzCQ16VGdnHxKx.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .override(300,300)
                .into(adventureView);

        adventureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    adventureView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("1");

                }
                else {
                    adventureView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    Log.i("count", String.valueOf(count));
                    genre.remove("1");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/1XSYOP0JjjyMz1irihvWywro82r.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .override(300,300)
                .into(comedyView);

        comedyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    comedyView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("3");
                }
                else {
                    comedyView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("3");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/seeEc3xNttsx308AB0iFtqKVyjG.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(documentaryView);

        documentaryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    documentaryView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("5");
                }
                else {
                    documentaryView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("5");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/q0xV5Lnq30JiegbVGBOvVwrgUDT.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(dramaView);

        dramaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    dramaView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("6");
                }
                else {
                    dramaView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("6");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/67QFqWFl8AJCeKBs1nadkU9145y.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(fantasyView);

        fantasyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    fantasyView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("8");
                }
                else {
                    fantasyView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("8");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/lwybGlEEJtXZM3ynY19PNwNlPn9.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(historyView);

        historyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    historyView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("9");
                }
                else {
                    historyView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("9");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/mCDSOfcVJfMkGUNrNpXWFO7oNBY.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(horrorView);

        horrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    horrorView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("10");
                }
                else {
                    horrorView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("10");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/kVwudGk77ilXN4TwJn0B3pZtS6N.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(mysteryView);

        mysteryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    mysteryView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("12");
                }
                else {
                    mysteryView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("12");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/cmWTZj9zzT9KFt3XyL0gssL7Ig8.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(crimeView);

        crimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    crimeView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("4");
                }
                else {
                    crimeView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("4");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/aqqXcUvcfJmGXJKBmEjUXThXAZv.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(romanceView);

        romanceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    romanceView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("13");
                }
                else {
                    romanceView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("13");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/z56bVX93oRG6uDeMACR7cXCnAbh.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(scienceView);

        scienceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    scienceView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("14");
                }
                else {
                    scienceView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("14");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/66mrr3AK6grmkfGJ1wlCP8dkzjM.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(warView);

        warView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    warView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("17");
                }
                else {
                    warView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("17");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/qX7hudNY428lYyj8zz3w5Qgfsrd.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(westernView);

        westernView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    westernView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("18");
                }
                else {
                    westernView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("18");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/pQu93NuwR90AaCULzglVD5Ge4Ml.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(musicView);

        musicView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    musicView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("11");
                }
                else {
                    musicView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("11");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/juAmsE4GC6gql0L1HsDmABkLWuX.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(kidsView);

        kidsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    kidsView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("26");
                }
                else {
                    kidsView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("26");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/sLTAEFtjentQ5satiGdmv7o2f1C.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(familyView);

        familyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    familyView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("7");
                }
                else {
                    familyView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("7");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/2SsEKPFypasKs4fPV8Nqrl9LD55.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(animationView);

        animationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    animationView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("2");
                }
                else {
                    animationView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("2");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/ArntYxWZXArwegRh9IaOq2KD1JR.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(realityView);

        realityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    realityView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("29");
                }
                else {
                    realityView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("29");
                }

            }
        });

        Glide.with(UserChoiceActivity.this)
                .load("https://image.tmdb.org/t/p/w500/8XDLgSlM6hb01iEWNRbqxKdFnGD.jpg")
                .placeholder(R.drawable.baseline_person_outline_24)
                .into(thrillerView);

        thrillerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(!view.isSelected());
                if(view.isSelected()){
                    thrillerView.setColorFilter(Color.parseColor("#B3000000"));
                    count+=1;
                    genre.add("16");
                }
                else {
                    thrillerView.setColorFilter(Color.parseColor("#00000000"));
                    count = count - 1;
                    genre.remove("16");
                }

            }
        });

        btnGenreChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Log.i("genresize", String.valueOf(genre.size()));
                if(genre.size() != 3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserChoiceActivity.this);
                    builder.setTitle("장르 선택");
                    builder.setMessage("장르 3가지 선택해주세요.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }else {

                    setgenre();
                }
            }
        });

    }

    public void setgenre(){
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);

        String AccessToken = sp.getString("AccessToken","");

        Log.i("Accesstoken",AccessToken);

        Retrofit retrofit = NetworkClient2.getRetrofitClient(UserChoiceActivity.this);

        LoginApi api = retrofit.create(LoginApi.class);
        HashMap<String,ArrayList<String>> dataset = new HashMap<>();
        dataset.put("genre",genre);
        Call<HashMap<String,String>> call = api.userGenre("Bearer "+AccessToken,
                dataset);

        call.enqueue(new Callback<HashMap<String, String>>() {
            @Override
            public void onResponse(Call<HashMap<String, String>> call, Response<HashMap<String, String>> response) {
                if(response.code()==200){
                    Intent intent = new Intent(UserChoiceActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<HashMap<String, String>> call, Throwable t) {
                Log.i("userChoice_genre","fail");
            }
        });
    }
}