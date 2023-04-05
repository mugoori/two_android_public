package com.example.two.Api;
import com.example.two.model.User;
import com.example.two.model.UserList;
import com.example.two.model.UserRes;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface UserApi {




        @GET("user")
        Call<UserList> getUserInfo(@Header("Authorization") String token);

        @Multipart
        @PUT("user")
        Call<UserRes> update(@Header("Authorization") String token, @Part("data") RequestBody data, @Part MultipartBody.Part profileImg );

        @Multipart
        @PUT("user")
        Call<UserRes> updateNotChangeProfile( @Header("Authorization") String token, @Part("data") RequestBody data );

        @DELETE("user")
        Call<UserRes> delete( @Header("Authorization") String token );


}
