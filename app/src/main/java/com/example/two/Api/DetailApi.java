package com.example.two.Api;

import com.example.two.model.ActorList;
import com.example.two.model.DetailList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DetailApi {

    @GET("movie/{movie_id}")
    Call<DetailList> getDetail(@Path("movie_id") int Id,
                               @Query("api_key") String keys,
                               @Query("language")String language);
    @GET("movie/{movie_id}/credits")
    Call<ActorList> getActor(@Path("movie_id") int Id,
                             @Query("api_key") String keys,
                             @Query("language")String language);
}
