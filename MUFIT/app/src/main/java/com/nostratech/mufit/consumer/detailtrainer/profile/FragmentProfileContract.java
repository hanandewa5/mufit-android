package com.nostratech.mufit.consumer.detailtrainer.profile;

import com.nostratech.mufit.consumer.model.DetailTrainerModel;

public interface FragmentProfileContract {
    interface View {
        void displayImagesAndCertificates(DetailTrainerModel model);
    }

    interface Presenter {

    }
}
