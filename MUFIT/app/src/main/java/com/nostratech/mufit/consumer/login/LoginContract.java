package com.nostratech.mufit.consumer.login;

public interface LoginContract {
    interface View {
        void showValidationEmpty(); //email/phone num atau password gak diisi
        void loginSuccess();
        void loginAsAdmin();
    }

    interface Presenter {
       void login(String emailPhone, String password, String deviceId);
    }
}
