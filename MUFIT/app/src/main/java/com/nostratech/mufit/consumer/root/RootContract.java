package com.nostratech.mufit.consumer.root;

class RootContract {

    interface View {
        void goToHome();

        void goToHistory();

        void goToSchedule();

        void goToLogin(int flag);

        void goToMuhealth();

        void goToMuhealthOnboarding();

        void switchPage(int menuResId);

        void userIsLoggedIn();

        void userIsNotLoggedIn();
    }

    interface Presenter {
        void onHomeClick();

        void onHistoryClick();

        void onScheduleClick();

        void onLoginClick();

        void onMuhealthClick();

        void checkLoginStatus();

        void onLoginCancelled();
    }

}
