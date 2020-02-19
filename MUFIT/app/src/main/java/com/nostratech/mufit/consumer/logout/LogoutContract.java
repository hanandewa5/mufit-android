package com.nostratech.mufit.consumer.logout;

public class LogoutContract {

    public interface View {
        void showLogoutSuccessMessage();
    }

    public interface Presenter {
        void logout();
    }
}
