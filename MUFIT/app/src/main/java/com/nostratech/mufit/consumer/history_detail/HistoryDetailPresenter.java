package com.nostratech.mufit.consumer.history_detail;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.constants.BookingStatusConstants;
import com.nostratech.mufit.consumer.model.CancelBookingRequestModel;
import com.nostratech.mufit.consumer.model.HistoryDetailModel;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.CurrencyFormatter;
import com.nostratech.mufit.consumer.utils.date.DateFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

/**
 * Created by Ahmadan Ditiananda on 5/28/2018.
 */

class HistoryDetailPresenter extends MyBasePresenter implements HistoryDetailContract.HistoryDetailPresenter {

    private HistoryDetailContract.View view;
    private DateFormatter dateFormatter;
    private CurrencyFormatter currencyFormatter;

    HistoryDetailPresenter(Context context, MvpView mvpView, HistoryDetailContract.View view) {
        super(context, mvpView);
        this.view = view;
        this.dateFormatter = new DateFormatter();
        this.currencyFormatter = new CurrencyFormatter();
    }


    @Override
    public void getHistoryDetailBooking(String id) {
        getMvpView().showLoading();
        if(isConnectedToInternet()){
            Call<StandardResponseModel> call = getApiService().getHistoryBookingDetail(getAccessToken(), id);
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
                HistoryDetailModel model = getGson().fromJson(jsonObject.toString(), HistoryDetailModel.class);

                view.showTrainerInfo(model.getPhotoTrainer(), model.getCoverPicTrainerUrl(), model.getTrainerName());

                String[] addressArr = model.getAddress().split(",");

                String address = addressArr.length < 6 ? model.getAddress() : addressArr[3] + ", " + addressArr[4];

                String startTime = model.getBookingShiftList().get(0).getStartTime();
                String endTime = model.getBookingShiftList().get(0).getEndTime();
                String trainingTime = dateFormatter.beautifyTime(startTime, ":") + " - " + dateFormatter.beautifyTime(endTime, ":");

                long date = model.getBookingDate();
                DateFormat dateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
                String trainingDate = dateFormat.format(date);

                view.showTrainingDetail(address, trainingDate, trainingTime);


                String status = model.getStatusBooking();

                //Append EVENT to status code if it's an event
                if(model.getBookingType().equalsIgnoreCase("event")){
                    view.showBookingStatus("EVENT - " + status);
                } else {
                    view.showBookingStatus(status);
                }

                showOrHideCancelBookingButton(status, trainingDate + " " + startTime);

                String voucherCode = model.getVoucherCode() != null
                        ? model.getVoucherCode()
                        : "-";
                String discountValue = model.getDiscount() != null
                        ? currencyFormatter.getRupiahString(model.getDiscount())
                        : "0";

                String serviceFee = model.getServiceFee() != null
                        ? currencyFormatter.getRupiahString(model.getServiceFee())
                        : "0";

                int grandTotalInt = model.getGrandTotal() + (model.getServiceFee() == null ? 0 : model.getServiceFee());
                String grandTotal = currencyFormatter.getRupiahString(grandTotalInt);

                view.showPaymentDetail(voucherCode, discountValue, serviceFee, grandTotal);

                if(status.equals(BookingStatusConstants.RATED))
                    view.showRatingAndReview(model.getRating(), model.getReview());

                view.showPurchasedSpecialities(model.getBookingSpecialityList());

                getMvpView().dismissLoading();

            }));
        }
        else {
            getMvpView().showNoInternetError();
        }
    }

    private void showOrHideCancelBookingButton(String status, String bookingDateTimeString){
        switch(status){
            case BookingStatusConstants.PAID:
                DateFormat fullDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm:ss", Locale.getDefault());
                try {
                    Date fullDate = fullDateFormat.parse(bookingDateTimeString);
                    if(new Date().getTime() + Constants.ONE_DAY_IN_MILLIS > fullDate.getTime()){
                        //Hide if start time is less time than 24 hours from current time
                        view.hideCancelBookingButton();
                    } else {
                        view.showCancelBookingButton();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case BookingStatusConstants.BOOKED:
                view.showCancelBookingButton();
                break;
            case BookingStatusConstants.START:
            case BookingStatusConstants.CANCEL:
            case BookingStatusConstants.RATED:
            case BookingStatusConstants.BOOKED_CANCEL:
                view.hideCancelBookingButton();
                break;
        }
    }

    @Override
    public void cancelBooking(String description, String id) {
        getMvpView().showLoading();
        if(isConnectedToInternet()) {
            if (TextUtils.isEmpty(description)) {
                view.showValidationEmpty();
            } else {
                Call<StandardResponseModel> call = getApiService().cancelBooking(getAccessToken(),
                        new CancelBookingRequestModel(description, id));
                call.enqueue(new RetrofitCallback<>(this, response -> view.cancelBookingSuccess()));
            }
        }
        else {
            getMvpView().showNoInternetError();
        }
    }

//    @Override
//    public void uploadImageFromDetail(ApiService apiService, MultipartBody.Part image, String type) {
//        if(MufitUtils.isConnected(context)){
//            Call<BaseResponseModel> call  = apiService.uploadImage(image, type);
//            call.enqueue(new Callback<BaseResponseModel>() {
//                @Override
//                public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
//                    if(response.isSuccessful()){
//                        if(response.body().getMessage().equals(Constants.TAG_OK)){
//                            String url = response.body().getResult().toString();
//                            View.uploadImageSuccess(url);
//                        }
//                        else
//                        {
//                            if (response.body() != null) {
//                                baseViewInterface.showError(response.body().getResult().toString());
//                            }
//                        }
//                    } else {
//                        if (response.errorBody() != null) {
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
//        }
//        else {
//            baseViewInterface.noInternetError();
//        }
//    }

//    @Override
//    public void uploadPaymentFromDetail(ApiService apiService, String token,
//                                        String id, String paymentPhoto, Integer version, String paymentDesc) {
//        baseViewInterface.showLoading();
//        if(MufitUtils.isConnected(context)){
//            Call<BaseResponseModel> call = apiService.uploadPayment(token,
//                    new UploadPaymentRequestModel(id, paymentPhoto,version, paymentDesc));
//            call.enqueue(new Callback<BaseResponseModel>() {
//                @Override
//                public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
//                    if(response.isSuccessful()){
//                        if(response.body().getMessage().equals(Constants.TAG_OK)) {
//                            if (response.body() != null) {
//                                View.uploadPaymentSuccess();
//                            } else {
//                                baseViewInterface.showError(response.body().getResult().toString());
//                            }
//                        }
//                    }
//                    else {
//                        if (response.errorBody() != null) {
//                            baseViewInterface.showError(response.errorBody().toString());
//                        }
//                    }
//                }
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
//        }
//        else {
//            baseViewInterface.noInternetError();
//        }
//    }
}
