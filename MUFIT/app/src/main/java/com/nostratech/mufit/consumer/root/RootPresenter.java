package com.nostratech.mufit.consumer.root;

import android.content.Context;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.login.LoginActivity;

import id.mufit.core.base.MvpView;

public class RootPresenter extends MyBasePresenter implements RootContract.Presenter {

    private RootContract.View rootView;

//    /**
//     * 0 = Home
//     * 1 = History
//     * 2 = Schedule
//     *
//     * Used to re-select the Navigation View after user dismisses Options or returns to Root from LoginActivity
//     */
//    private int lastOpenedPageIndex = -1;

    public RootPresenter(Context context, MvpView mvpView, RootContract.View rootView) {
        super(context, mvpView);
        this.rootView = rootView;
    }

    @Override
    public void onHomeClick() {
        rootView.goToHome();
    }

    @Override
    public void onHistoryClick() {
        if(isLoggedIn()){
            rootView.goToHistory();
        } else {
            rootView.goToLogin(LoginActivity.OPEN_HISTORY);
        }
    }

    @Override
    public void onScheduleClick() {
        if(isLoggedIn()){
            rootView.goToSchedule();
        } else {
            rootView.goToLogin(LoginActivity.OPEN_SCHEDULE);
        }
    }

    @Override
    public void onLoginClick() {
        rootView.goToLogin(LoginActivity.OPEN_HOME);
    }

    @Override
    public void onMuhealthClick() {

        if(!isLoggedIn()) {
            rootView.goToLogin(LoginActivity.OPEN_MUHEALTH);
            return;
        }

        if(!getmAppCache().getMuhealthOnboarded()) {
            rootView.goToMuhealthOnboarding();
            return;
        }

        rootView.goToMuhealth();
    }
//
//    @Override
//    public void onMenuClick(int lastPageIndex) {
//        lastOpenedPageIndex = lastPageIndex;
//        rootView.showMenu();
//    }
//
//    @Override
//    public void onMenuDismissed() {
//        int menuResId = -1;
//        switch (lastOpenedPageIndex) {
//            case 0:
//                menuResId = R.id.navigation_home;
//                break;
//            case 1:
//                menuResId = R.id.navigation_history;
//                break;
//            case 2:
//                menuResId = R.id.navigation_schedule;
//                break;
//        }
//
//        rootView.switchPage(menuResId);
//    }

    @Override
    public void checkLoginStatus() {
        if(isLoggedIn()) rootView.userIsLoggedIn();
        else rootView.userIsNotLoggedIn();
    }

    @Override
    public void onLoginCancelled() {
        rootView.switchPage(R.id.navigation_home);
    }

}
