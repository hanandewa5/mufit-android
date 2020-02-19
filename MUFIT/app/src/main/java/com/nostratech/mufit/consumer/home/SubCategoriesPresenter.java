package com.nostratech.mufit.consumer.home;

import android.content.Context;

import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.home.HomeSpecialityListModel;

import java.util.ArrayList;
import java.util.List;

import id.mufit.core.base.MvpView;

public class SubCategoriesPresenter extends MyBasePresenter implements SubCategoriesContract.Presenter {

    private SubCategoriesContract.View view;
    private List<HomeSpecialityListModel> modelsSpeciality = new ArrayList<>();
    private int limit = 5;

    public SubCategoriesPresenter(Context context, MvpView mvpView, SubCategoriesContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

//    @Override
//    public void getListSpeciality(ApiService apiService, String token) {
//        if (isConnectedToInternet()) {
//            Call<StandardResponseModel> call = getApiService().getListSpecialityForHome(token);
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
//                Type dataType = new TypeToken<Collection<HomeSpecialityListModel>>() {
//                }.getType();
//                modelsSpeciality = getGson().fromJson(jsonArray, dataType);
//                view.doShowListSpeciality(modelsSpeciality);
//            }));
//        } else {
//            getMvpView().showNoInternetError();
//        }
//    }
}
