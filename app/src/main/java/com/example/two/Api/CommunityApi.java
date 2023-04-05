package com.example.two.Api;

import com.example.two.model.Community;
import com.example.two.model.CommunityRes;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommunityApi {

    // 전체 데이터
    @GET("community")
    Call<CommunityRes> getCommunityAllData(@Query("page") int page);
    // 내 데이터
    @GET("community")
    Call<CommunityRes> getCommunityMeData(@Query("page") int page,
                                          @Header("Authorization") String token);
    // 커뮤니티 글 생성 (이미지 포함)
    @Multipart
    @POST("community")
    Call<CommunityRes> createCommunity(@Header("Authorization") String token,
                                       @Part MultipartBody.Part photo,
                                       @Part("title") RequestBody title ,
                                       @Part("content") RequestBody content);
    // 커뮤니티 글 생성 (이미지 없음)
    @Multipart
    @POST("community")
    Call<CommunityRes> createCommunityNoImage(@Header("Authorization") String token,
                                              @Part("title") RequestBody title ,
                                              @Part("content") RequestBody content);
    // 커뮤니티 글 수정 (이미지 포함)
    @Multipart
    @PUT("community/{communityId}")
    Call<HashMap<String,String>> updateCommunity(@Path("communityId") int communityId ,
                                                 @Header("Authorization") String token,
                                                 @Part MultipartBody.Part photo,
                                                 @Part("title") RequestBody title ,
                                                 @Part("content") RequestBody content);
    // 커뮤니티 글 수정 (이미지 없음)
    @Multipart
    @PUT("community/{communityId}")
    Call<HashMap<String,String>> updateCommunityNoImage(@Path("communityId") int communityId ,
                                                        @Header("Authorization") String token,
                                                        @Part("title") RequestBody title ,
                                                        @Part("content") RequestBody content);

    // 커뮤니티 글 전체에서 검색
    @GET("community")
    Call<CommunityRes> searchCommunityAllData(@Query("page") int page,
                                              @Query("keyword") String keyword);
    // 커뮤니티 글 내가 쓴 글에서 검색
    @GET("community")
    Call<CommunityRes> searchCommunityMeData(@Query("page") int page,
                                             @Query("keyword") String keyword,
                                             @Header("Authorization") String token);

    // 커뮤니티 글 좋아요
    @POST("communityLike/{communityId}")
    Call<HashMap<String,String>> communityLike(@Header("Authorization") String token,
                                               @Query("communityId") int communityId);
    @DELETE("communityLike/{communityId}")
    Call<HashMap<String,String>> communityUnLike(@Header("Authorization") String token,
                                                 @Query("communityId") int communityId);
}
