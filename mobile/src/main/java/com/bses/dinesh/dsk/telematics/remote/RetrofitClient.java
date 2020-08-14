package com.bses.dinesh.dsk.telematics.remote;

import com.bses.dinesh.dsk.BuildConfig;
import com.bses.dinesh.dsk.utils.ApplicationConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static Retrofit retrofithome = null;

    public static Retrofit getClient(String baseUrl) {
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationConstants.SERVER_URL + baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
//        okHttpClient.addInterceptor(interceptor);
//
//        retrofit = new Retrofit.Builder()
//                .baseUrl(BuildConfig.SERVER_URL + baseUrl)
//                .client(okHttpClient.build())
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();

        return retrofit;
    }

    public static Retrofit getClientHome(String baseUrl) {
        Gson gson = new GsonBuilder().setLenient().create();

        if (retrofithome == null) {
            retrofithome = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofithome;
    }
}