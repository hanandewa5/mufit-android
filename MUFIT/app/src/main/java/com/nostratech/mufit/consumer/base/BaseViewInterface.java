package com.nostratech.mufit.consumer.base;

public interface BaseViewInterface {
    void initTypefaces();
    void showLogDebug(String tag, String message);
    void noInternetError();
    void showLoading();
    void dismissLoading();
    void hideKeyboard();
    void showError(String errorMessage);
}
