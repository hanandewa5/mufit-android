package com.nostratech.mufit.consumer.banner.advertisement;

class AdvertisementDetailContract {

    interface View {
        void showAdvertisementDetail(String title, String content);
    }

    interface Presenter {
        void loadAdvertisementDetail(String id);
    }

}
