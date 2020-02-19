package com.nostratech.mufit.consumer.logout;

import android.content.Context;

import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.LogoutRequestModel;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class LogoutPresenter extends MyBasePresenter implements LogoutContract.Presenter {

    private LogoutContract.View view;

    public LogoutPresenter(Context context, MvpView mvpView, LogoutContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void logout() {
        getMvpView().showLoading();
        if(isConnectedToInternet()){
            Call<StandardResponseModel> call = getApiService().logout(new LogoutRequestModel(getAccessToken()));
            call.enqueue(new RetrofitCallback<>(this, response -> {
                getMvpView().dismissLoading();

                //Delete cache
                getmAppCache().clearSession();

                view.showLogoutSuccessMessage();
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }
}