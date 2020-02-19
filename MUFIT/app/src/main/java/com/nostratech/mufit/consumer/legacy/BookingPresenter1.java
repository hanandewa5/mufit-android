//package com.nostratech.mufit.consumer.booking;
//
//import android.content.Context;
//import android.support.annotation.NonNull;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.base.BaseResponseModel;
//import com.nostratech.mufit.consumer.base.BaseViewInterface;
//import com.nostratech.mufit.consumer.model.detail_shift.Day;
//import com.nostratech.mufit.consumer.model.detail_shift.DetailShiftResponseModel;
//import com.nostratech.mufit.consumer.service.ApiClient;
//import com.nostratech.mufit.consumer.service.ApiService;
//import com.nostratech.mufit.consumer.utils.Constants;
//import com.nostratech.mufit.consumer.utils.MufitUtils;
//
//import java.net.SocketTimeoutException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.TimeZone;
//import java.util.concurrent.TimeoutException;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//
//public class BookingPresenter1 implements BookingInterface1.Presenter {
//    private BookingInterface1.View bookingView;
//    private BaseViewInterface baseView;
//    private Context context;
//    private Gson gson;
//
//    BookingPresenter1(BookingInterface1.View bookingView, BaseViewInterface baseView, Context context) {
//        this.bookingView = bookingView;
//        this.baseView = baseView;
//        this.context = context;
//        gson = new Gson();
//    }
//
//    @Override
//    public void getDetailBooking(String access_token, String id, final String day, String bookdate, final Long timeStamp) {
//        baseView.showLoading();
//        if (MufitUtils.isConnected(context)) {
//            ApiService apiService = ApiClient.createService(ApiService.class);
//            Call<BaseResponseModel> call = apiService.getDetailShiftTrainer(access_token, id, day, bookdate);
//            call.enqueue(new Callback<BaseResponseModel>() {
//                @Override
//                public void onResponse(@NonNull Call<BaseResponseModel> call, @NonNull retrofit2.Response<BaseResponseModel> response) {
//                    if (response.isSuccessful()) {
//                        if (response.body() != null) {
//                            if (Constants.TAG_OK.equals(response.body().getMessage())) {
//                                baseView.dismissLoading();
//                                JsonObject jsonObject = gson.toJsonTree(response.body().getResult()).getAsJsonObject();
//                                DetailShiftResponseModel result = gson.fromJson(jsonObject.toString(), DetailShiftResponseModel.class);
//
//                                String dateString = new SimpleDateFormat("EEEE, dd MMMM yyyy", Constants.INDONESIA).format(new Date(timeStamp));
//                                List<String> tempDateList = new ArrayList<>();
//                                tempDateList.add(dateString);
//                                bookingView.setBank(result.getBank().getName(), result.getNoRek());
//                                bookingView.setSpinnerSchedule(tempDateList);
//                                bookingView.setCheckboxSchedule(result.getDay().getShift());
//                                bookingView.setCheckboxSpeciality(result.getTrainerSpeciality());
//                            } else {
//                                baseView.showError(response.body().getResult().toString());
//                            }
//                        }
//                    } else if(response.code() == Constants.STATUS_INTERNAL_SERVER_ERROR){
//                        if (response.errorBody() != null) {
//                            baseView.showError(context.getResources().getString(R.string.error_try_again));
//                        }
//                    }else {
//                        if (response.errorBody() != null) {
//                            baseView.showError(response.errorBody().toString());
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
//
//    @Override
//    public int getDayNumber(String day) {
//        int temp = 1;
//        if (day.equalsIgnoreCase("MONDAY")){
//            temp = 2;
//        } else if (day.equalsIgnoreCase("TUESDAY")){
//            temp = 3;
//        } else if (day.equalsIgnoreCase("WEDNESDAY")){
//            temp = 4;
//        } else if (day.equalsIgnoreCase("THURSDAY")){
//            temp = 5;
//        } else if (day.equalsIgnoreCase("FRIDAY")){
//            temp = 6;
//        } else if (day.equalsIgnoreCase("SATURDAY")){
//            temp = 7;
//        }
//        return temp;
//    }
//}
