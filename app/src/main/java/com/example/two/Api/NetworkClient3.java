package com.example.two.Api;

import android.content.Context;

import com.example.two.config.Config;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient3 {

        public static Retrofit retrofit;

        public static Retrofit getRetrofitClient(Context context){
                if(retrofit == null) {
                        // 통신 로그 확인할때 필요한 코드
                        HttpLoggingInterceptor loggingInterceptor =
                                new HttpLoggingInterceptor();
                        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                        // 네트워크 연결관련 코드
                        OkHttpClient httpClient = new OkHttpClient.Builder()
                                .connectTimeout(1, TimeUnit.MINUTES)
                                .readTimeout(1, TimeUnit.MINUTES)
                                .writeTimeout(1, TimeUnit.MINUTES)
                                .addInterceptor(loggingInterceptor)
                                .build();

                        Gson gson = new Gson().newBuilder().setLenient().create();


                        //네트워크로 데이터 보내고 받는
                        //레트로핏 라이브러리 관련 코드

                        retrofit = new Retrofit.Builder()
                                .baseUrl(Config.DOMAIN3)
                                .client(httpClient)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();


                }
                return retrofit;
        }

}
