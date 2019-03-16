package com.example.qualitastest.model;



import com.example.qualitastest.RetroFit.RetroIntsance;
import com.example.qualitastest.presenter.CanadaPresenter;
import com.example.qualitastest.view.CanadaView;

import java.util.Observable;
import java.util.Observer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CanadaModel implements CanadaPresenter {

    private CanadaView canadaView;

    public CanadaModel(CanadaView canadaView) {
        this.canadaView = canadaView;

    }

    @Override
    public void getCandaData() {

        canadaView.showProgressBar();

        new RetroIntsance().getConnection().getCanadaData().enqueue(new Callback<CanadaBean>() {
            @Override
            public void onResponse(Call<CanadaBean> call, Response<CanadaBean> response) {

                if (response.isSuccessful()) {
                    canadaView.hideProgrssBar();
                    canadaView.isSuccess(response.body().getTitle(), response.body().getRows());
                }
            }

            @Override
            public void onFailure(Call<CanadaBean> call, Throwable t) {
                canadaView.hideProgrssBar();
                canadaView.onFailure("Something Went Wrong");

            }
        });

    }
}
