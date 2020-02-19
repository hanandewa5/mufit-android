//package com.nostratech.mufit.consumer.legacy;
//
//import com.nostratech.mufit.consumer.model.banner_event_detail.BannerEventDetailModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.advertisement.AdvertisementDetailModel;
//import com.nostratech.mufit.consumer.model.banner_event_detail.voucher_detail.VoucherDetailModel;
//
//public interface BannerEventDetailInterface {
//
//    interface View {
//        void doShowEventDetail(BannerEventDetailModel bannerEventDetailModel);
//
//        void doShowVoucherDetail(VoucherDetailModel voucherDetailModel);
//
//        void doShowAdvertisementDetail(AdvertisementDetailModel advertisementDetailModel);
//
//        void showMap();
//
//        void hideMap();
//
//        void navigateToBookingEvent(String eventId);
//
//        void navigateToLogin(String eventId, String eventType);
//
//        void navigateToSearchTrainer(String voucherCode, String voucherDiscountType, int discountValue);
//    }
//
//    interface Presenter {
//        void getShowEventDetail(String id);
//
//        void getShowVoucherDetail(String id);
//
//        void getAdvertisementDetail(String id);
//
//        void bookEvent();
//
//        void loadBanner(String bannerType, String id);
//    }
//}
