package com.example.two.Api;

import com.example.two.model.User;
import com.example.two.model.UserRes;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RegisterApi {

    @Multipart
    @POST("register")
    Call<UserRes> resgister(@Part("data") RequestBody data  ,
                            @Part MultipartBody.Part profileImg);

//    @Multipart
//    @POST("register")
//    Call<JsonObject> resgister(@Part("name") RequestBody name,
//                               @Part("nickname") RequestBody nickname,
//                               @Part("userEmail") RequestBody email,
//                               @Part("password") RequestBody password,
//                               @Part("gender") RequestBody gender,
//                               @Part("age") RequestBody age,
//                               @Part("questionNum") RequestBody questionNum,
//                               @Part("questionAnswer") RequestBody answer,
//                               @Part MultipartBody.Part profileImg);

    @POST("isEmail")
    Call<HashMap<String,String>> IsEmail(@Body User user);

    @POST("isNickname")
    Call<HashMap<String,String>> IsNickname(@Body User user);


}
