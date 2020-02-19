package com.nostratech.mufit.consumer.detailtrainer;

import com.nostratech.mufit.consumer.model.DetailTrainerModel;

public interface DetailTrainerContract {
    interface View {
        void setProfile(DetailTrainerModel model);
        void navigateToBookingPackage();
//        void setProfile(String name, String urlPhoto, String urlCover, String description);
    }

    interface Presenter {
        void getTrainerProfile(String idTrainer);
        void onBookingPackageClick();
    }
}
