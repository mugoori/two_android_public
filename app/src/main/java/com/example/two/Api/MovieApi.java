package com.example.two.Api;

import com.example.two.model.ActorList;
import com.example.two.model.MovieList;
import com.example.two.model.MovieRankList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

//    @GET("/recommendfirst")
//    Call<MovieList>getMovie(@Header("Authorization") String token);

    @GET("/recommendsecond")
    Call<MovieList>getRecommendMovie(@Header("Authorization") String token);

    @GET("rank")
    Call<MovieRankList>getrankMovie();

//    @GET("/content/{tmdbcontentId}/actor")
//    Call<ActorList>getactorData(@Path("tmdbcontentId") int tmdbcontentId);



}
