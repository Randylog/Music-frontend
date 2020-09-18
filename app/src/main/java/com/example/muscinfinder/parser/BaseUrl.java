package com.example.muscinfinder.parser;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//using baseurl to run in android app, adding tokens
public class BaseUrl {
    public static final String base_url="http://192.168.1.33:5000/";
    public static String token = "Bearer ";
    public static Retrofit getInstance() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}