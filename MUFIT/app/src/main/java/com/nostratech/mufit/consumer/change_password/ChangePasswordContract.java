package com.nostratech.mufit.consumer.change_password;

public interface ChangePasswordContract {
    interface View {

        void changePasswordSuccess();

        void showValidationEmpty();
    }

    interface Presenter {
        void changePassword(String oldPassword,
                            String newPassword,
                            String confirmationNewPassword);
    }
}
