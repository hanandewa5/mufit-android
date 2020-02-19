package com.nostratech.mufit.consumer.profile;

import com.nostratech.mufit.consumer.model.ProfileModel;

import java.io.File;

public interface ProfileContract {
    interface View {
        void showProfileDetail(ProfileModel profileModel);
        void showButtonCheckBcm(String b2bId);
        void hideButtonCheckBcm();
        void showEditProfileSuccessMessage();
    }

    interface Presenter {
        void getProfileDetail();
        void initiateUpdateProcess(String id, String fullName, String phone, String email,
                                   File profileImage, Integer version);

        void updateProfile(String address, String currentPassword,
                           String fullName, String id, String newPassword,
                           String newPasswordConfirmation, String phone, String photoSelfie, Integer version );
    }
}
