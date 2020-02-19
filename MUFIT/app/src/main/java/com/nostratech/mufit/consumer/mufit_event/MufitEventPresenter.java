package com.nostratech.mufit.consumer.mufit_event;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.EventModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.PaginatedResponseModel;
import retrofit2.Call;

class MufitEventPresenter extends MyBasePresenter implements MufitEventContract.Presenter {

    private MufitEventContract.View view;
    private ArrayList<EventModel> eventModels;
    private int counterPage = 0;
    private int limit = 5;

    MufitEventPresenter(Context context, MvpView mvpView, MufitEventContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void getMufitEvent() {
        if(counterPage >= 1){
            view.showRecyclerViewLoading();
        } else {
            getMvpView().showLoading();
        }

        Call<PaginatedResponseModel> call = getApiService().getEvent(getAccessToken(), counterPage, limit);
        call.enqueue(new RetrofitCallback<>(this, response ->{
            JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
            Type dataType = new TypeToken<Collection<EventModel>>() {}.getType();
            eventModels = getGson().fromJson(jsonArray, dataType);
            boolean endOfPage = response.body().getPages() == counterPage + 1;
            if(counterPage >= 1){
                view.dismissRecyclerViewLoading();
            } else {
                getMvpView().dismissLoading();
            }
            view.showEvent(eventModels,endOfPage);
            counterPage++;
        }));
    }

    @Override
    public int getLimit() {
        return limit;
    }
}
