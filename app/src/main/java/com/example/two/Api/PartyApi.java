package com.example.two.Api;

import com.example.two.model.Party;
import com.example.two.model.PartyListRes;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PartyApi {
    @POST("party")
    Call<HashMap<String,String>> setParty(@Header("Authorization") String token,
                                          @Body Party party);

    @GET("party")
    Call<PartyListRes> getMemeberme(@Header("Authorization") String token,
                                    @Query("page") int page);

    @GET("party/captain")
    Call<PartyListRes> getCaptainme(@Header("Authorization") String token,
                                    @Query("page") int page);

}
