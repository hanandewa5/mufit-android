package com.nostratech.mufit.consumer.muhealth.onboarding;

import android.content.Context;
import android.os.Handler;

import com.nostratech.mufit.consumer.base.MyBasePresenter;

import id.mufit.core.base.MvpView;

class OnboardingPresenter extends MyBasePresenter implements OnboardingContract.Presenter {

    private OnboardingContract.View view;

    OnboardingPresenter(Context context, MvpView mvpView, OnboardingContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void submitData(String height, String weight) {

        if(height.isEmpty() || weight.isEmpty()) {
            view.showErrorDataEmpty();
            return;
        }

        getMvpView().showLoading();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            getmAppCache().setMuhealthOnboarded(true);
            view.showOnboardingSuccess();
            getMvpView().dismissLoading();
        }, 2000);
    }
}
