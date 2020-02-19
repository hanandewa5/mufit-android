package com.nostratech.mufit.consumer.forgot_password;

import android.widget.EditText;

/**
 * Created by Ahmadan Ditiananda on 4/25/2018.
 */

public interface ForgotPasswordContract {
    interface View {
        void showValidationEmpty();
        void sendEmailSuccess();
        void showEmailAlert(boolean value);
        void showButtonEnable(boolean value);
    }

    interface Presenter {
        void sendEmail(String email);
        void observeEmail(EditText textEmail);
    }
}
