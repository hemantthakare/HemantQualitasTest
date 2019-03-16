package com.example.qualitastest.view;

import com.example.qualitastest.model.CanadaBean;

import java.util.List;

public interface CanadaView {

    void isSuccess(String title, List<CanadaBean.Row> rows);

    void onFailure(String message);

    void showProgressBar();

    void hideProgrssBar();

}
