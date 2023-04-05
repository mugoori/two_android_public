package com.example.two;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.two.Api.LoginApi;
import com.example.two.Api.MovieApi;
import com.example.two.Api.NetworkClient1;
import com.example.two.Api.NetworkClient2;
import com.example.two.Api.UserApi;
import com.example.two.adapter.MainAdapter;
import com.example.two.config.Config;
import com.example.two.Api.MovieApi;
import com.example.two.fragment.CommunityFragment;
import com.example.two.fragment.HomeFragment;
import com.example.two.fragment.MyFragment;
import com.example.two.fragment.PartyFragment;
import com.example.two.fragment.SearchFragment;
import com.example.two.model.Chat;
import com.example.two.model.Movie;
import com.example.two.model.MovieList;
import com.example.two.model.MovieRank;
import com.example.two.model.MovieRankList;
import com.example.two.model.User;
import com.example.two.model.UserList;
import com.example.two.model.UserRes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {



    public BottomNavigationView navigationView;
    Fragment homeFragment;
    Fragment communityFragment;
    Fragment partyFragment;
    Fragment searchFragment;
    Fragment myFragment;
    String AccessToken;
    ArrayList<User> userArrayList = new ArrayList<>();
    User user;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getUserData();
        navigationView = findViewById(R.id.bottomNavigationView);
        navigationView.setSelectedItemId(R.id.homeFragment);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("user",user);

                int itemId = item.getItemId();

                Fragment fragment;

                if(itemId == R.id.homeMenu){
                    homeFragment = new HomeFragment();
                    homeFragment.setArguments(bundle);
                    fragment = homeFragment;
                    Log.i("fragmnet","home");
                }else if(itemId == R.id.comunityMenu){
                    communityFragment = new CommunityFragment();
                    communityFragment.setArguments(bundle);
                    fragment = communityFragment;
                    Log.i("fragmnet","커뮤니티");
                }else if(itemId == R.id.partyMenu){
                    partyFragment = new PartyFragment();
                    partyFragment.setArguments(bundle);
                    fragment = partyFragment;
                    Log.i("fragmnet","파티");
                }else if(itemId == R.id.searchMenu){
                    searchFragment = new SearchFragment();
                    searchFragment.setArguments(bundle);
                    fragment = searchFragment;
                    Log.i("fragmnet","검색");
                }else{
                    myFragment = new MyFragment();
                    myFragment.setArguments(bundle);
                    fragment = myFragment;
                    Log.i("fragmnet","마이");
                }
                return loadFragment(fragment);

            }
        });
        navigationView.setSelectedItemId(R.id.homeMenu);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerView,fragment)
                    .commit();
            return true;
        }
        else{
            return false;
        }

    }

    public void getUserData(){

        Retrofit retrofit = NetworkClient2.getRetrofitClient(getApplicationContext());

        UserApi api = retrofit.create(UserApi.class);

//        Log.i("AAA", api.toString());
        SharedPreferences sp = getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);

        AccessToken = sp.getString("AccessToken","");

        Call<UserList> call = api.getUserInfo("Bearer "+AccessToken);

        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                userArrayList.addAll(response.body().getUser());
                user = userArrayList.get(0);

            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {

            }
        });
    }

}