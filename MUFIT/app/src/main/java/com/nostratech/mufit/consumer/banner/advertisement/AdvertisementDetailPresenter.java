package com.nostratech.mufit.consumer.banner.advertisement;

import android.content.Context;

import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.banner_event_detail.advertisement.AdvertisementDetailModel;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

class AdvertisementDetailPresenter extends MyBasePresenter implements AdvertisementDetailContract.Presenter {

    private AdvertisementDetailContract.View view;

    AdvertisementDetailPresenter(Context context, MvpView mvpView, AdvertisementDetailContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void loadAdvertisementDetail(String id) {
        getMvpView().showLoading();
        if(isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getDetailAdvertisement(getAccessToken(), id);
            call.enqueue(new RetrofitCallback<>(this, response -> {

                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult())
                        .getAsJsonObject();
                AdvertisementDetailModel model = getGson().fromJson(jsonObject.toString(),
                        AdvertisementDetailModel.class);
                view.showAdvertisementDetail(model.getName(), model.getDescription());

                getMvpView().dismissLoading();
            }));
        }
        else {
            getMvpView().showNoInternetError();
        }
    }
}
