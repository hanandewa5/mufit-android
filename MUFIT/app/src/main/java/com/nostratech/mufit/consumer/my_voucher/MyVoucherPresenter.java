package com.nostratech.mufit.consumer.my_voucher;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.CheckVoucherResponseModel;
import com.nostratech.mufit.consumer.model.MyVoucherModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

class MyVoucherPresenter extends MyBasePresenter implements MyVoucherContract.Presenter {

    private MyVoucherContract.View view;
    private List<MyVoucherModel> myVoucherModels = new ArrayList<>();

    MyVoucherPresenter(Context context, MvpView mvpView, MyVoucherContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    /**
     * Overriden because {@link #checkVoucher(String)}} returns "Voucher Not Found" as 200 with an error message.
     * In the core-module implementation {@link MvpView#dismissLoading()} is not automatically called in {@link MvpView#showGenericError(String)}
     *
     * @param errorMessage
     */

    //TODO: Check if other errors have this same problem. If they do, override BasePresenter's error200 to include dismissLoading
    @Override
    public void onError200(String errorMessage) {
        super.onError200(errorMessage);
        getMvpView().dismissLoading();
    }

    @Override
    public void checkVoucher(String voucher) {
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().checkVoucher(getAccessToken(), voucher);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                CheckVoucherResponseModel result = getGson().fromJson(jsonObject.toString(), CheckVoucherResponseModel.class);
                //baseView.dismissLoading();
                view.showValidVoucher(result);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void getMyVoucher(int page, int limit) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getDetailMyVoucher(getAccessToken(), page, limit);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<MyVoucherModel>>() {
                }.getType();
                myVoucherModels = getGson().fromJson(jsonArray, dataType);
                view.showVouchers(myVoucherModels);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

}
