package com.example.two.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.two.Api.MovieApi;
import com.example.two.Api.NetworkClient1;
import com.example.two.Api.NetworkClient2;
import com.example.two.Api.NetworkClient3;
import com.example.two.MovieContentActivity;
import com.example.two.R;
import com.example.two.RankALLActivity;
import com.example.two.adapter.MainAdapter;
import com.example.two.config.Config;
import com.example.two.model.Movie;
import com.example.two.model.MovieList;
import com.example.two.model.MovieRank;
import com.example.two.model.MovieRankList;
import com.example.two.model.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    ImageView rankPoster1;
    ImageView rankPoster2;
    ImageView rankPoster3;

    TextView rankTitle1;
    TextView rankTitle2;
    TextView rankTitle3;

    TextView rankRate1;
    TextView rankRate2;
    TextView rankRate3;

    ImageView imgLogout;
    ImageView imgRank;

    User user;
    RecyclerView recyclerView;
    MainAdapter adapter;
    ArrayList<Movie> movieArrayList = new ArrayList<>();
    ArrayList<MovieRank> movierankArrayList = new ArrayList<>();

    ArrayList<User> userArrayList = new ArrayList<>();
    int page=0;
    String language = "ko-KR";
    String desc = "popularity.desc";

    private Movie selectedMovie;

    String AccessToken;

    HomeFragment fragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        SharedPreferences sp = this.getActivity().getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");

        Bundle bundle = getArguments();
        user = bundle.getParcelable("user");

        rankPoster1 = view.findViewById(R.id.rankPoster1);
        rankPoster2 = view.findViewById(R.id.rankPoster2);
        rankPoster3 = view.findViewById(R.id.rankPoster3);

        rankTitle1 = view.findViewById(R.id.rankTitle1);
        rankTitle2 = view.findViewById(R.id.rankTitle2);
        rankTitle3 = view.findViewById(R.id.rankTitle3);

        rankRate1 = view.findViewById(R.id.rankRate1);
        rankRate2 = view.findViewById(R.id.rankRate2);
        rankRate3 = view.findViewById(R.id.rankRate3);

        imgLogout = view.findViewById(R.id.imgLogout);
        imgRank = view.findViewById(R.id.imgRank);

        getrankMovieData();

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getNetworkData();

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
                    if(page != -1) {
                        addNetworkData();
                    }
                }
            }
        });

        imgRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RankALLActivity.class);
                startActivity(intent);
            }
        });
//        imgLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getLogout();
//            }
//        });
        return view;
    }
    public void getrankMovieData() {
        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());

        MovieApi api = retrofit.create(MovieApi.class);

//        Log.i("AAA", api.toString());

        Call<MovieRankList> call = api.getrankMovie();
        Log.i("CCC", call.toString());
        call.enqueue(new Callback<MovieRankList>() {

            @Override
            public void onResponse(Call<MovieRankList> call, Response<MovieRankList> response) {

                if (response.isSuccessful()) {

                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로

                    // 데이터를 받았으니 리사이클러 표시

                    movierankArrayList.addAll(response.body().getRank());
                    Log.i("AAA", movierankArrayList.toString());
                    // 오프셋 처리하는 코드
                    Glide.with(getActivity())
                            .load(movierankArrayList.get(0).getImgUrl())
                            .into(rankPoster1);
                    rankTitle1.setText(movierankArrayList.get(0).getTitle());
                    rankRate1.setText(movierankArrayList.get(0).getContentRating());

                    Glide.with(getActivity())
                            .load(movierankArrayList.get(1).getImgUrl())
                            .into(rankPoster2);
                    rankTitle2.setText(movierankArrayList.get(1).getTitle());
                    rankRate2.setText(movierankArrayList.get(1).getContentRating());

                    Glide.with(getActivity())
                            .load(movierankArrayList.get(2).getImgUrl())
                            .into(rankPoster3);
                    rankTitle3.setText(movierankArrayList.get(2).getTitle());
                    rankRate3.setText(movierankArrayList.get(2).getContentRating());




                } else {
                    Toast.makeText(getActivity(), "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<MovieRankList> call, Throwable t) {
                Log.i("DDD", String.valueOf(t));


            }

        });
    }

    public void getNetworkData() {
        Retrofit retrofit = NetworkClient3.getRetrofitClient(getActivity());

        MovieApi api = retrofit.create(MovieApi.class);

        Log.i("AAA", api.toString());
        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");

        Call<MovieList> call = api.getRecommendMovie("Bearer "+AccessToken);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요
                    movieArrayList.clear();

                    // 데이터를 받았으니 리사이클러 표시

                    movieArrayList.addAll(response.body().getRecommendList());
                    Log.i("정상적으로 실행은 하니?", String.valueOf(response.code()));

//                    movieArrayList.get(page);
//                    Log.i("page",String.valueOf(page));
                    // 오프셋 처리하는 코드


                    adapter = new MainAdapter(HomeFragment.this,movieArrayList);
                    recyclerView.setAdapter(adapter);


                } else {
                    Toast.makeText(getActivity(), "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.i("error", String.valueOf(t));

            }


        });
    }

    public void addNetworkData() {
        Retrofit retrofit = NetworkClient3.getRetrofitClient(getActivity());

        MovieApi api = retrofit.create(MovieApi.class);

        Log.i("AAA", api.toString());
        if(page == 0){
            page = 1;
        }else{
            page = page+1;
        }
        SharedPreferences sp = getActivity().getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
        AccessToken = sp.getString("AccessToken","");

        Call<MovieList> call = api.getRecommendMovie("Bearer "+AccessToken);

        call.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {

                if (response.isSuccessful()) {
                    // getNetworkData는 항상처음에 데이터를 가져오는 동작 이므로
                    // 초기화 코드가 필요

                    // 데이터를 받았으니 리사이클러 표시

                    movieArrayList.addAll(response.body().getRecommendList());
                    movieArrayList.get(page);
                    Log.i("page",String.valueOf(page));

                    // 오프셋 처리하는 코드
                    adapter.notifyDataSetChanged();



                } else {
                    Toast.makeText(getActivity(), "문제가 있습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {


            }


        });
    }

    //    public void getLogout(){
//        Retrofit retrofit = NetworkClient2.getRetrofitClient(getActivity());
//        LoginApi api = retrofit.create(LoginApi.class);
//
//        Call<UserRes> call = api.Logout("Bearer "+AccessToken);
//        call.enqueue(new Callback<UserRes>() {
//            @Override
//            public void onResponse(Call<UserRes> call, Response<UserRes> response) {
//
//                SharedPreferences sp = ((MainActivity)getActivity()).getSharedPreferences(Config.PREFERENCE_NAME,MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("AccessToken",null);
//                editor.apply();
//                Intent intent = new Intent(((MainActivity)getActivity()), LoginActivity.class);
//                startActivity(intent);
//                ((MainActivity)getActivity()).finish();
//            }
//
//            @Override
//            public void onFailure(Call<UserRes> call, Throwable t) {
//
//            }
//        });
//    }
//    public void isDetail(int index){
//        selectedMovie = movieArrayList.get(index);
//        int Id = movieArrayList.get(index).getId();
//        Log.i("ID",String.valueOf(Id));
//        Intent intent = new Intent(getActivity(), MovieContentActivity.class);
//        intent.putExtra("id",Id);
//        startActivity(intent);
//
//    }
}