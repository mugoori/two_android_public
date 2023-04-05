package com.example.two.Api;

import com.example.two.model.ContentReview;
import com.example.two.model.ContentReviewRes;
import com.example.two.model.reView;
import com.example.two.model.reViewList;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ContentReviewApi {

    @GET("content/{contentId}/review")
    Call<ContentReviewRes> getAllReview(@Path("contentId") int contentId,
                                        @Query("page") int page);

    @PUT("content/{contentId}/review/{contentReviewId}")
    Call<reView> UpdateReview(@Header("Authorization") String token,
                              @Path("contentId") int Id,
                              @Path("contentReviewId") int reviewId,
                              @Body ContentReview contentReview);

    @DELETE("content/{contentId}/review/{contentReviewId}")
    Call<reView> DeleteReview(@Header("Authorization") String token,
                              @Path("contentId") int Id,
                              @Path("contentReviewId") int reviewId);
    @GET("content/review/me")
    Call<reView> myReview(@Header("Authorization") String token,
                                @Query("page") int page);

}
