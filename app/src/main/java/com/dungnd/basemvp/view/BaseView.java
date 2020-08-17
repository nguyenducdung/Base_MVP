package com.dungnd.basemvp.view;

public interface BaseView {

    void showLoading();

    void hiddenLoading();

    void showDialog(String title, String message);

    void showDialog(String message);

    void initView();
}
