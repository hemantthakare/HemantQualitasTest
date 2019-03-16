package com.example.qualitastest.RetroFit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroIntsance {


    private final String url = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json/";

    Retrofit builder = new Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build();

    public RetroConnection getConnection() {

        return builder.create(RetroConnection.class);
    }

}

