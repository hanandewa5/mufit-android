//package com.nostratech.mufit.consumer.legacy;
//
//import android.content.Context;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
//import com.google.gson.reflect.TypeToken;
//import com.nostratech.mufit.consumer.base.MyBasePresenter;
//import com.nostratech.mufit.consumer.model.CreateBookingResponseModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingRequestModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event.EventBookingSpecialityRequestModel;
//import com.nostratech.mufit.consumer.model.booking.CreateBookingRequestModel;
//import com.nostratech.mufit.consumer.model.booking.PaymentMethodModel;
//
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
//import id.mufit.core.base.MvpView;
//import id.mufit.core.network.RetrofitCallback;
//import id.mufit.core.network.models.StandardResponseModel;
//import retrofit2.Call;
//
//public class BookingPresenter2 extends MyBasePresenter implements BookingContract2.Presenter {
//    private BookingContract2.View view;
//    private String lastBookingSecureId;
//
//    BookingPresenter2(Context context, MvpView mvpView, BookingContract2.View view) {
//        super(context, mvpView);
//        this.view = view;
//    }
//
//
//    @Override
//    public void createEventBooking(String customerName, String customerPhone,
//                                   String event, List<EventBookingSpecialityRequestModel> specialityList,
//                                   String trainer) {
//        getMvpView().showLoading();
//        if(isConnectedToInternet()) {
//            Call<StandardResponseModel> call = getApiService().createEventBooking(getAccessToken(),
//                    new EventBookingRequestModel(customerName, customerPhone, event, specialityList, trainer));
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
//                CreateBookingResponseModel model = getGson().fromJson(jsonObject, CreateBookingResponseModel.class);
//                lastBookingSecureId = model.getId();
//                view.successBooking(model.getOrderId());
//            }));
//        }
//        else {
//            getMvpView().showNoInternetError();
//        }
//    }
//
//    @Override
//    public void cancelBookingUnprocessed() {
//        if(isConnectedToInternet()){
//            Call<StandardResponseModel> call = getApiService().setCancelTransaction(lastBookingSecureId, getAccessToken());
//            call.enqueue(new RetrofitCallback<>(this, response -> view.successCancelBookingUnprocessed()));
//        }
//        else {
//            getMvpView().showNoInternetError();
//        }
//    }
//
//    @Override
//    public void createBooking(CreateBookingRequestModel createBookingRequestModel) {
//        getMvpView().showLoading();
//        if (isConnectedToInternet()) {
//            Call<StandardResponseModel> call = getApiService().createBooking(getAccessToken(), createBookingRequestModel);
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
//                CreateBookingResponseModel model = getGson().fromJson(jsonObject, CreateBookingResponseModel.class);
//                lastBookingSecureId = model.getId();
//                String orderId = model.getOrderId();
//                view.successBooking(orderId);
//            }));
//        } else {
//            getMvpView().showNoInternetError();
//        }
//    }
//
//    @Override
//    public void getPaymentMethodList(int price) {
//        getMvpView().showLoading();
//        if(isConnectedToInternet()){
//            Call<StandardResponseModel>call = getApiService().getPaymentMethod(price, getAccessToken());
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
//                Type dataType = new TypeToken<Collection<PaymentMethodModel>>() {}.getType();
//                ArrayList<PaymentMethodModel> paymentMethods = getGson().fromJson(jsonArray, dataType);
//                view.doShowPaymentMethod(paymentMethods);
//            }));
//        }
//        else{
//            getMvpView().showNoInternetError();
//        }
//    }
//}
//
//
//
////    @Override
////    public void checkVoucher(String voucher) {
////        // baseView.showLoading();
////        if (MufitUtils.isConnected(context)) {
////            ApiService apiService = ApiClient.createService(ApiService.class);
////            Call<BaseResponseModel> call = apiService.checkVoucher(sessionManager.getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN), voucher);
////            call.enqueue(new Callback<BaseResponseModel>() {
////                @Override
////                public void onResponse(@NonNull Call<BaseResponseModel> call, @NonNull retrofit2.Response<BaseResponseModel> response) {
////                    if (response.isSuccessful()) {
////                        //Log.d("FRISTY LOG", "response check voucher " +response);
////                        if (response.body() != null) {
////                            if (Constants.TAG_OK.equals(response.body().getMessage())) {
////                                JsonObject jsonObject = gson.toJsonTree(response.body().getResult()).getAsJsonObject();
////                                CheckVoucherResponseModel result = gson.fromJson(jsonObject.toString(), CheckVoucherResponseModel.class);
////                                //baseView.dismissLoading();
////                                //Log.d("FRISTY LOG", "result.getValue() check voucher " + result.getValue());
////                                view.validVoucher(result.getValue());
////                            } else {
////                                view.notValidVoucher();
////                            }
////                        }
////                    } else {
////                        if (response.errorBody() != null) {
////                            baseView.showError(response.errorBody().toString());
////                        }
////                    }
////                }
////
////                @Override
////                public void onFailure(@NonNull Call<BaseResponseModel> call, @NonNull Throwable t) {
////                    if (t instanceof TimeoutException) {
////                        baseView.showError(context.getResources().getString(R.string.error_try_again));
////                    } else if (t instanceof SocketTimeoutException) {
////                        baseView.showError(context.getResources().getString(R.string.error_try_again));
////                    } else {
////                        baseView.showError(t.getMessage());
////                    }
////                }
////            });
////        } else {
////            baseView.noInternetError();
////        }
////    }
//
////    //todo: fristy check voucher from my vouhcer
////    @Override
////    public void checkVoucherFromMyVoucher(String voucher, String trainerSpecialityID) {
////        // baseView.showLoading();
////        if (MufitUtils.isConnected(context)) {
////            ApiService apiService = ApiClient.createService(ApiService.class);
////            Call<BaseResponseModel> call = apiService.checkVoucherFromMyVoucher(sessionManager.getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN), voucher, trainerSpecialityID);
////            call.enqueue(new Callback<BaseResponseModel>() {
////                @Override
////                public void onResponse(@NonNull Call<BaseResponseModel> call, @NonNull retrofit2.Response<BaseResponseModel> response) {
////                    if (response.isSuccessful()) {
////                        //Log.d("FRISTY LOG", "response check voucher from my voucher " +response);
////                        if (response.body() != null) {
////                            if (Constants.TAG_OK.equals(response.body().getMessage())) {
////                                JsonObject jsonObject = gson.toJsonTree(response.body().getResult()).getAsJsonObject();
////                                //Log.d("FRISTY LOG", "json check voucher from my voucher " +jsonObject);
////                                CheckVoucherResponseModel result = gson.fromJson(jsonObject.toString(), CheckVoucherResponseModel.class);
////                                //baseView.dismissLoading();
////                                view.validVoucher(result.getValue());
////                            } else {
////                                view.notValidVoucher();
////                            }
////                        }
////                    } else {
////                        if (response.errorBody() != null) {
////                            baseView.showError(response.errorBody().toString());
////                        }
////                    }
////                }
////
////                @Override
////                public void onFailure(@NonNull Call<BaseResponseModel> call, @NonNull Throwable t) {
////                    if (t instanceof TimeoutException) {
////                        baseView.showError(context.getResources().getString(R.string.error_try_again));
////                    } else if (t instanceof SocketTimeoutException) {
////                        baseView.showError(context.getResources().getString(R.string.error_try_again));
////                    } else {
////                        baseView.showError(t.getMessage());
////                    }
////                }
////            });
////        } else {
////            baseView.noInternetError();
////        }
////    }
////    @Override
////    public void observeVoucher(final EditText textVoucher) {
////        textVoucherStream = RxTextView.textChanges(textVoucher)
////                .debounce(2, TimeUnit.SECONDS)
////                .map(new Function<CharSequence, Boolean>() {
////                    @Override
////                    public Boolean apply(CharSequence s) {
////                        return !TextUtils.isEmpty(s);
////                    }
////                }).subscribe(new Consumer<Boolean>() {
////                    @Override
////                    public void accept(Boolean aBoolean) {
////                        if (aBoolean) {
////                            checkVoucher(textVoucher.getText().toString());
////                        } else {
////                            view.emptyVoucher();
////                        }
////                    }
////                });
////    }
////
////    //todo: fristy check voucher from my vouhcer
////    @Override
////    public void observeVoucherFromMyVoucher(final EditText textVoucher, final String trainerSpecialityID) {
////        textVoucherStream = RxTextView.textChanges(textVoucher)
////                .debounce(2, TimeUnit.SECONDS)
////                .map(new Function<CharSequence, Boolean>() {
////                    @Override
////                    public Boolean apply(CharSequence s) {
////                        return !TextUtils.isEmpty(s);
////                    }
////                }).subscribe(new Consumer<Boolean>() {
////                    @Override
////                    public void accept(Boolean aBoolean) {
////                        if (aBoolean) {
////                            checkVoucherFromMyVoucher(textVoucher.getText().toString(), trainerSpecialityID);
////                        } else {
////                            view.emptyVoucher();
////                        }
////                    }
////                });
////    }
