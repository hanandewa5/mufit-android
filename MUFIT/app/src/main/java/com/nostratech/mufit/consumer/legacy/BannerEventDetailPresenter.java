//package com.nostratech.mufit.consumer.legacy;
//
//import android.content.Context;
//
//import com.google.gson.JsonObject;
//import com.nostratech.mufit.consumer.base.MyBasePresenter;
//import com.nostratech.mufit.consumer.model.banner_event_detail.BannerEventDetailModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.advertisement.AdvertisementDetailModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.voucher_detail.VoucherDetailModel;
//import com.nostratech.mufit.consumer.service.ApiService;
//
//import id.mufit.core.base.MvpView;
//import id.mufit.core.network.ApiClient;
//import id.mufit.core.network.RetrofitCallback;
//import id.mufit.core.network.models.StandardResponseModel;
//import retrofit2.Call;
//
//public class BannerEventDetailPresenter extends MyBasePresenter implements BannerEventDetailInterface.Presenter {
//
//    private final BannerEventDetailInterface.View view;
//    private final ApiService apiService;
//    private String eventId;
//    private String bannerType;
//
//    private VoucherDetailModel voucherModel;
//
//    BannerEventDetailPresenter(Context context, MvpView mvpView, BannerEventDetailInterface.View view) {
//        super(context, mvpView);
//        this.view = view;
//        apiService = ApiClient.getInstance().createService(ApiService.class);
//    }
//
//    @Override
//    public void getShowEventDetail(String id) {
//        getMvpView().showLoading();
//        if(isConnectedToInternet()){
//            Call<StandardResponseModel> call = apiService.getDetailEvent(getAccessToken(), id);
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
//                BannerEventDetailModel bannerEventDetailModel = getGson().fromJson(jsonObject.toString()
//                        ,BannerEventDetailModel.class);
//                view.doShowEventDetail(bannerEventDetailModel);
//            }));
//        }
//        else {
//            getMvpView().showNoInternetError();
//        }
//    }
//
//    @Override
//    public void getShowVoucherDetail(String id) {
//        getMvpView().showLoading();
//        if(isConnectedToInternet()){
//            Call<StandardResponseModel> call = apiService.getDetailVoucher(getAccessToken(), id);
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult()).getAsJsonObject();
//                 voucherModel = getGson().fromJson(jsonObject.toString(),
//                        VoucherDetailModel.class);
//                view.doShowVoucherDetail(voucherModel);
//            }));
//        }
//        else {
//            getMvpView().showNoInternetError();
//        }
//    }
//
//    @Override
//    public void getAdvertisementDetail(String id) {
//        getMvpView().showLoading();
//        if(isConnectedToInternet()) {
//            Call<StandardResponseModel> call = apiService.getDetailAdvertisement(getAccessToken(), id);
//            call.enqueue(new RetrofitCallback<>(this, response -> {
//                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult())
//                        .getAsJsonObject();
//                AdvertisementDetailModel model = getGson().fromJson(jsonObject.toString(),
//                        AdvertisementDetailModel.class);
//                view.doShowAdvertisementDetail(model);
//            }));
//        }
//        else {
//            getMvpView().showNoInternetError();
//        }
//    }
//
//    @Override
//    public void bookEvent() {
//        if(bannerType.equalsIgnoreCase("event")){
//            //If user is logged in
//            if(getAccessToken() != null){
//                view.navigateToBookingEvent(eventId);
//            } else {
//                view.navigateToLogin(eventId, bannerType);
//            }
//        } else if (bannerType.equalsIgnoreCase("voucher")){
//            view.navigateToSearchTrainer(voucherModel.getCode(), voucherModel.getType(), voucherModel.getValue());
//        } else {
//            getMvpView().showGenericError("Advertisements should not be able to book event");
//        }
//    }
//
//    @Override
//    public void loadBanner(String bannerType, String id) {
//        this.bannerType = bannerType;
//        this.eventId = id;
//        switch (bannerType) {
//            case "event":
//                getShowEventDetail(id);
//                view.showMap();
//                break;
//            case "voucher":
//                getShowVoucherDetail(id);
//                view.hideMap();
//                break;
//            case "advertisement":
//                getAdvertisementDetail(id);
//                view.hideMap();
//                break;
//        }
//    }
//}
