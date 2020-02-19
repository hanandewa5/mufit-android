package com.nostratech.mufit.consumer.detailtrainer;

import android.content.Context;

import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.DetailTrainerModel;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

class DetailTrainerPresenter extends MyBasePresenter implements DetailTrainerContract.Presenter {
    private DetailTrainerContract.View dTrainerView;

    public DetailTrainerPresenter(Context context, MvpView mvpView, DetailTrainerContract.View dTrainerView) {
        super(context, mvpView);
        this.dTrainerView = dTrainerView;
    }

    @Override
    public void getTrainerProfile(String idTrainer) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().getDetailTrainer(getAccessToken(), idTrainer);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                DetailTrainerModel result = getGson().fromJson(jsonObject.toString(), DetailTrainerModel.class);
                dTrainerView.setProfile(result);
                getMvpView().dismissLoading();
            }));
        }
        else {
            getMvpView().showNoInternetError();
        }
//            call.enqueue(new Callback<BaseResponseModel>() {
//                @Override
//                public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
//                    if(response.isSuccessful()){
//                        //Log.d("FRISTY LOG", "response getDetailTrainer " + response);
//                        if(response.body().getMessage().equals(Constants.TAG_OK)){
//
//                        }
//                        else {
//                            if(response.body() != null) {
//                                baseView.showError(response.body().getResult().toString());
//                                fpView.showEmptySchedule();
//                            }
//                        }
//                    }
//                    else{
//                        if(response.errorBody() != null){
//                            baseView.showError(response.errorBody().toString());
//                            fpView.showEmptySchedule();
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<BaseResponseModel> call, Throwable t) {
//                    if (t instanceof TimeoutException) {
//                        baseView.showError(context.getResources().getString(R.string.error_try_again));
//                    } else if (t instanceof SocketTimeoutException) {
//                        baseView.showError(context.getResources().getString(R.string.error_try_again));
//                    } else {
//                        baseView.showError(t.getMessage());
//                    }
//                }
//            });
//        }
}

    @Override
    public void onBookingPackageClick() {
        if(isLoggedIn()){
            dTrainerView.navigateToBookingPackage();
        } else {
            getMvpView().onUserUnauthorized();
        }
    }

//    @Override
//    public void getTrainerProfile(String access_token, String id, final String day, String bookdate) {
//        baseView.showLoading();
//            if (MufitUtils.isConnected(context)) {
//                ApiService apiService = ApiClient.createService(ApiService.class);
//            Call<BaseResponseModel> call = apiService.getDetailShiftTrainer(access_token, id, day, bookdate);
//            call.enqueue(new Callback<BaseResponseModel>() {
//                @Override
//                public void onResponse(@NonNull Call<BaseResponseModel> call, @NonNull retrofit2.Response<BaseResponseModel> response) {
//                    if (response.isSuccessful()) {
//                        //Log.d("FRISTY LOG", "response getTrainerProfile " + response);
//                        if (response.body() != null) {
//                            if (Constants.TAG_OK.equals(response.body().getMessage())) {
//                                baseView.showLogDebug(DetailTrainerPresenter.class.getSimpleName(), response.body().getResult().toString());
//                                JsonObject jsonObject = gson.toJsonTree(response.body().getResult()).getAsJsonObject();
//                                DetailShiftResponseModel result = gson.fromJson(jsonObject.toString(), DetailShiftResponseModel.class);
//                                baseView.dismissLoading();
//
//                                String textTrainerName = result.getName();
//                                String trainerPhoto = result.getUrlPhotoTrainer();
//                                //TODO: Pake cover pic trainer yg sebenarnya kalau API udah ada
//                                String trainerCover = result.getTrainerSpeciality().get(0).getBackground();
//                                String trainerDescription = result.getDescription();
//
//                                dTrainerView.setProfile(textTrainerName, trainerPhoto, trainerCover, trainerDescription);
//                            } else {
//                                baseView.showError(response.body().getResult().toString());
//                            }
//                        }
//                    } else if (response.code() == Constants.STATUS_UNAUTHORIZED) {
//                        dTrainerView.goToLogin();
//                    } else if (response.code() == Constants.STATUS_INTERNAL_SERVER_ERROR) {
//                        if (response.errorBody() != null) {
//                            baseView.showError(context.getResources().getString(R.string.error_try_again));
//                        }
//                    } else if (response.code() == Constants.STATUS_NOT_AVAILABLE) {
//                        if (response.errorBody() != null) {
//                            baseView.showError(context.getResources().getString(R.string.error_try_again));
//                        }
//                    } else {
//                        if (response.errorBody() != null) {
//                            baseView.showError(response.errorBody().toString() + ". status: " + response.code());
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(@NonNull Call<BaseResponseModel> call, @NonNull Throwable t) {
//                    if (t instanceof TimeoutException) {
//                        baseView.showError(context.getResources().getString(R.string.error_try_again));
//                    } else if (t instanceof SocketTimeoutException) {
//                        baseView.showError(context.getResources().getString(R.string.error_try_again));
//                    } else {
//                        baseView.showError(t.getMessage());
//                    }
//                }
//            });
//        } else {
//            baseView.noInternetError();
//        }
//    }
    }

