package com.kryvuy.weatherapp.api;

import android.app.Application;

import com.kryvuy.weatherapp.start.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Володимир on 4/29/2017.
 */

public class Service_Retrofit extends Application {
    private Retrofit mRetrofit;
    private static Interface_Response sApi_iterface;


    @Override
    public void onCreate() {
        super.onCreate();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(loggingInterceptor);

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constant.BASE_URL)
                .client(client.build())
                .build();
        sApi_iterface = mRetrofit.create(Interface_Response.class);
    }
    public static Interface_Response getService(){
        return sApi_iterface;
    }
}
