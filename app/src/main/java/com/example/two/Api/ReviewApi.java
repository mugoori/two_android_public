package com.example.two.Api;


import com.example.two.model.reView;
import com.example.two.model.reViewList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewApi {

    @POST("content/{contentId}/review")
    Call<reView> makeReview(@Path("contentId") int Id,
                            @Header("Authorization") String token,
                            @Body Map<String, String> map);


    @GET("content/{contentId}/review")
    Call<reViewList> getReview(@Path("contentId") int Id,
                               @Query("page") int page);

//    @GET("content/review/me")
//    Call<MyReviewList> myReview(@Header("Authorization") String token,
//                                @Query("page") int page);

    @PUT("content/{contentId}/review/{contentReviewId}")
    Call<reView> UpdateReview(@Header("Authorization") String token,
                              @Path("contentId") int Id,
                              @Path("contentReviewId") int reviewId,
                              @Body Map<String, String> map);

    @DELETE("content/{contentId}/review/{contentReviewId}")
    Call<reView> DeleteReview(@Header("Authorization") String token,
                              @Path("contentId") int Id,
                              @Path("contentReviewId") int reviewId);
}
