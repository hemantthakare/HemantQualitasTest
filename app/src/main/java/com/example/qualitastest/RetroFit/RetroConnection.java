package com.example.qualitastest.RetroFit;

import com.example.qualitastest.model.CanadaBean;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetroConnection {

    @GET("./")
    Call<CanadaBean> getCanadaData();
}
