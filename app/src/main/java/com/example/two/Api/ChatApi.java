package com.example.two.Api;

import com.example.two.model.Chat;
import com.example.two.model.ChatRoomList;
import com.example.two.model.PartyCheckRes;
import com.example.two.model.PartyRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ChatApi {

    @GET("partyBoard")
    Call<ChatRoomList>getChatingList(@Query("page") int page);

    @POST("partyBoard")
    Call<PartyRes>makeChating(@Header("Authorization") String token,
                              @Body Chat chat);
    @GET("party")
    Call<ChatRoomList>getMyChatingList(@Header("Authorization") String token,
                                       @Query("page") int page);

    @GET("party/{partyBoardId}/check")
    Call<PartyCheckRes> getCheckParty(@Path("partyBoardId") int partyBoardId);
}
