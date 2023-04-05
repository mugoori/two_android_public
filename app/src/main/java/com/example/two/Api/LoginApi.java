package com.example.two.Api;

import com.example.two.model.User;
import com.example.two.model.UserRes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface LoginApi {
    @POST("login")
    Call<UserRes> Login(@Body User user);

    @POST("logout")
    Call<UserRes> Logout(@Header("Authorization") String token);

    @POST("isId")
    Call<HashMap<String,String>> IsId(@Body User user);

    @POST("ispassword")
    Call<HashMap<String,String>> Ispassword(@Body User user);

    @PUT("changedpassword")
    Call<HashMap<String,String>> Changepassword(@Body User user);

    @POST("userGenre")
    Call<HashMap<String,String>> userGenre(@Header("Authorization") String token,
                                           @Body HashMap<String,ArrayList<String>> genre);
}
