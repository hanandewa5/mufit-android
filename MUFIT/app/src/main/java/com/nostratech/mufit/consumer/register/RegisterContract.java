package com.nostratech.mufit.consumer.register;

import android.widget.EditText;

interface RegisterContract {
    interface View {
        void showValidationEmpty();
        void showPasswordNotMatch();
        void showSuccessRegistration();
        void goToLoginActivity();
        void showEmailAlert(boolean value);
        void showPhoneAlert(boolean value);
        void showPasswordAlert(boolean value);
        void showPasswordConfirmationAlert(boolean value);
        void showButtonEnabled(boolean value);
    }

    interface Presenter {
//        void checkEmail(String email, String phone, String password, String confirmationPassword, String fullName);
        void doRegister(String email, String phone, String password, String confirmationPassword, String fullName);
        void observeFieldEmpty(EditText textFullname, EditText textEmail, EditText textPhone, EditText textPassword, EditText textConfirmPassword);
        void observeEmail(EditText textEmail);
        void observePhone(EditText textPhone);
        void observePassword(EditText textPassword);
        void observeConfirmationPassword(EditText textPassword, EditText textConfirmationPassword);
        void observeInvalidStream(EditText textFullname, EditText textEmail, EditText textPhone, EditText textPassword, EditText textConfirmPassword);
    }
}