package com.nostratech.mufit.consumer.change_password;

import android.content.Context;
import android.text.TextUtils;

import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.ChangePasswordRequestModel;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class ChangePasswordPresenter extends MyBasePresenter implements ChangePasswordContract.Presenter {

    private ChangePasswordContract.View view;

    ChangePasswordPresenter(Context context, MvpView mvpView, ChangePasswordContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void changePassword(String oldPassword, String newPassword, String confirmationNewPassword) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {

            if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword)
                    || TextUtils.isEmpty(confirmationNewPassword)) {
                view.showValidationEmpty();
                return;
            }

            ChangePasswordRequestModel model =
                    new ChangePasswordRequestModel(oldPassword, newPassword, confirmationNewPassword);

            Call<StandardResponseModel> call = getApiService().changePassword(getAccessToken(), model);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                getMvpView().dismissLoading();
                view.changePasswordSuccess();
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }
}

