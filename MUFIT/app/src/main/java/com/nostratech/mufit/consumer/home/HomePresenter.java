package com.nostratech.mufit.consumer.home;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.model.ProfileModel;
import com.nostratech.mufit.consumer.model.category.CategoryResponseModel;
import com.nostratech.mufit.consumer.model.home.HomeSpecialityListModel;
import com.nostratech.mufit.consumer.model.home.HomeTrainerListModel;
import com.nostratech.mufit.consumer.model.home.running_event.RunningEventModel;
import com.nostratech.mufit.consumer.utils.JsonUtils;
import com.nostratech.mufit.consumer.utils.MufitUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.PaginatedResponseModel;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class HomePresenter extends MyBasePresenter implements HomeContract.Presenter {

    private HomeContract.View view;
    private List<HomeTrainerListModel> models = new ArrayList<>();
    private List<HomeSpecialityListModel> modelsSpeciality = new ArrayList<>();
    private List<RunningEventModel> modelsEvent = new ArrayList<>();
    private int limit = 5;

    public HomePresenter(Context context, MvpView mvpView, HomeContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void getListRunningEvent() {
//        baseViewInterface.showLoading();
        view.showBannerLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getListRunningEvent(getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<RunningEventModel>>() {
                }.getType();
                modelsEvent = getGson().fromJson(jsonArray, dataType);
                view.dismissBannerLoading();
                view.doShowListRunningEvent(modelsEvent);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void getHotNews() {
        // loading for hot news
        view.showWhatHotsLoading();
        if(isConnectedToInternet()){
            Call<PaginatedResponseModel> call = getApiService().getLastNews();
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                NewsModel newsModel = getGson().fromJson(jsonObject.toString(), NewsModel.class);
                view.dismissWhatHotsLoading();
                view.showHotNews(newsModel);
            }));
        }
    }

    @Override
    public void getListTrainer(int counterPage) {
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getListTrainerForHome(getAccessToken(), counterPage, limit);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
                Type dataType = new TypeToken<Collection<HomeTrainerListModel>>() {
                }.getType();
                models = getGson().fromJson(jsonArray, dataType);
                if (models.size() > 0) view.doShowListTrainer(models);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public int getLimit() {
        return limit;
    }

//    @Override
//    public void getProfileDetail(ApiService apiService, String token) {
////        baseViewInterface.showLoading();
//        if(MufitUtils.isConnected(context)) {
//            Call<BaseResponseModel> call = apiService.getProfileDetail(token);
//            call.enqueue(new Callback<BaseResponseModel>() {
//                @Override
//                public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
//                    if(response.isSuccessful()){
//                        if (response.body().getMessage().equals(Constants.TAG_OK)){
//                            JsonObject jsonObject = gson.toJsonTree(response.body().getResult())
//                                    .getAsJsonObject();
//                            profileModel = gson.fromJson(jsonObject.toString(),
//                                    ProfileModel.class);
////                            view.updateEmailToSession(profileModel);
//                        } else {
//                            if(response.body() != null) {
//                                baseViewInterface.showError(response.body().getResult().toString());
//                            }
//                        }
//                    }else{
//                        if(response.errorBody() != null){
//                            baseViewInterface.showError(response.errorBody().toString());
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<BaseResponseModel> call, Throwable t) {
//                    if (t instanceof TimeoutException) {
//                        baseViewInterface.showError(context.getResources().getString(R.string.error_try_again));
//                    } else if (t instanceof SocketTimeoutException) {
//                        baseViewInterface.showError(context.getResources().getString(R.string.error_try_again));
//                    } else {
//                        baseViewInterface.showError(t.getMessage());
//                    }
//                }
//            });
//        }else {
//            baseViewInterface.noInternetError();
//        }
//    }

    @Override
    public void getTotalMyVoucher() {
        if (!isLoggedIn()) return;

        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getTotalMyVoucher(getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                Integer totalUserVoucher = getGson().toJsonTree(response.body().getResult()).getAsInt();
                view.getTotalUserVoucher(totalUserVoucher);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void getListCategory() {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getCategory();
            call.enqueue(new RetrofitCallback<>(this, response ->{
                List<CategoryResponseModel> listCategory = JsonUtils.parseJsonArray(getGson(),
                        response, CategoryResponseModel.class);
                view.showListCategories(listCategory);
            }));

        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void getFirstName(Context context) {
        if(!isLoggedIn()){
            view.showConsName(context.getString(R.string.guest));
        } else {
            if (MufitUtils.isConnected(context)) {
                getMvpView().showLoading();
                Call<StandardResponseModel> call = getApiService().getProfileDetail(getAccessToken());
                call.enqueue(new RetrofitCallback<>(this, response -> {
                    JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                    Type type = new TypeToken<ProfileModel>() {
                    }.getType();

                    ProfileModel model = getGson().fromJson(jsonObject, type);
                    String name = model.getFullName();
                    String[] nameArr = name.split(" ");
                    name = nameArr[0];
                    view.showConsName(name);
                    getMvpView().dismissLoading();
                }));
            }
        }


    }

    @Override
    public void onMyVoucherClick() {
        if(isLoggedIn()) view.openMyVoucherActivity();
        else view.openMyVoucherUserNotLoggedIn();
    }
}
