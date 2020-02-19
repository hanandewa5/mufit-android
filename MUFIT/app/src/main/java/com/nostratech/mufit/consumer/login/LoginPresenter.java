package com.nostratech.mufit.consumer.login;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.LoginRequestModel;
import com.nostratech.mufit.consumer.model.LoginResponseModel;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class LoginPresenter extends MyBasePresenter implements LoginContract.Presenter {
    private LoginContract.View view;

    LoginPresenter(Context context, MvpView mvpView, LoginContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void login(final String username, final String password, String deviceId) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                view.showValidationEmpty();
            } else {
                Call<StandardResponseModel> call = getApiService().login(new LoginRequestModel(username, password, deviceId));
                call.enqueue(new RetrofitCallback<>(this, response -> {

                    JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                    LoginResponseModel result = getGson().fromJson(jsonObject.toString(), LoginResponseModel.class);

                    if (result.getClientId().equals("admin")) {
                        view.loginAsAdmin();
                    }

                    // save token, name, and client id to preference
                    else {
                        getmAppCache().setUserAccessToken(result.getAccessToken());
                        getmAppCache().setUserFullName(result.getFullName());
                        getmAppCache().setEmail(result.getEmail());
                        getmAppCache().setPhoneNumber(result.getPhone());
                        view.loginSuccess();
                    }
                }));


            }
        }
        else {
            getMvpView().showNoInternetError();
        }
    }

}
