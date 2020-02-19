package com.nostratech.mufit.consumer.rate_review;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.HistoryDetailModel;
import com.nostratech.mufit.consumer.model.RateReviewRequestModel;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

/**
 * Created by Ahmadan Ditiananda on 5/30/2018.
 */

public class RateReviewPresenter extends MyBasePresenter implements RateReviewContract.Presenter {

    private RateReviewContract.View view;

    private String bookingId;

    public RateReviewPresenter(Context context, MvpView mvpView, RateReviewContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    //Show details such as voucher code, booking status, cover pic, etc
    @Override
    public void getBookingDetail(String id) {
        getMvpView().showLoading();
        if (isConnectedToInternet()) {
            this.bookingId = id;

            Call<StandardResponseModel> call = getApiService().getHistoryBookingDetail(getAccessToken(), id);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();

                HistoryDetailModel model = getGson().fromJson(jsonObject, HistoryDetailModel.class);

                if (model.getRating() != null) view.showTrainerRating(model.getRating());
                view.showTrainerDetail(model.getTrainerName(), model.getPhotoTrainer(), model.getCoverPicTrainerUrl());
                getMvpView().dismissLoading();
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void submitReview(int rating, String review) {
        if (isConnectedToInternet()) {

            boolean emptyReview = TextUtils.isEmpty(review);
            boolean emptyRating = rating == 0;

            if (emptyRating && emptyReview){
                view.showErrorEmptyRatingReview();
                return;
            }

            if (emptyRating) {
                view.showErrorEmptyRating();
                return;
            }

            if (emptyReview) {
                view.showErrorEmptyReview();
                return;
            }

            getMvpView().showLoading();

            Call<StandardResponseModel> call = getApiService().rateReview(getAccessToken(),
                    new RateReviewRequestModel(bookingId, rating, review));
            call.enqueue(new RetrofitCallback<>(this, response -> {
                getMvpView().dismissLoading();
                view.showReviewSubmissionSuccess();
            }));

        } else {
            getMvpView().showNoInternetError();
        }
    }


}
