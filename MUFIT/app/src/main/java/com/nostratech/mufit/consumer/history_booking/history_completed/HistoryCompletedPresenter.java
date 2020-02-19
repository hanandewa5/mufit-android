package com.nostratech.mufit.consumer.history_booking.history_completed;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.HistoryBookingModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.PaginatedResponseModel;
import retrofit2.Call;

/**
 * Created by Ahmadan Ditiananda on 5/24/2018.
 */

public class HistoryCompletedPresenter extends MyBasePresenter implements HistoryCompletedContract.Presenter {

    private HistoryCompletedContract.View view;
    private ArrayList<HistoryBookingModel> historyBookingModels;
    private int limit = 5;

    public HistoryCompletedPresenter(Context context, MvpView mvpView, HistoryCompletedContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void getCompletedBooking(String status, int counterPage) {
        if(counterPage >= 1){
            view.showRecyclerViewLoading();
        } else {
            getMvpView().showLoading();
        }
        if(isConnectedToInternet()) {
            Call<PaginatedResponseModel> call = getApiService().getHistoryBooking(getAccessToken(), status, counterPage, limit);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<HistoryBookingModel>>() {
                }.getType();
                historyBookingModels = getGson().fromJson(jsonArray, dataType);

                boolean endOfPage = response.body().getPages() == counterPage;

                view.doShowHistoryBooking(historyBookingModels, endOfPage);
            }));
        }
        else {
            getMvpView().showNoInternetError();
        }

    }

    @Override
    public int getLimit() {
        return limit;
    }
}
