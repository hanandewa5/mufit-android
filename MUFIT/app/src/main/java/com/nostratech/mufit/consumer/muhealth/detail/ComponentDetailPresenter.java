package com.nostratech.mufit.consumer.muhealth.detail;

import android.content.Context;
import android.os.Handler;

import com.nostratech.mufit.consumer.base.MyBasePresenter;

import id.mufit.core.base.MvpView;

public class ComponentDetailPresenter extends MyBasePresenter implements ComponentDetailContract.Presenter {

    private ComponentDetailContract.View view;

    public ComponentDetailPresenter(Context context, MvpView mvpView, ComponentDetailContract.View view) {
        super(context, mvpView);
        this.view = view;
    }


    @Override
    public void loadComponentDetail(int id) {
        getMvpView().showLoading();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            getMvpView().dismissLoading();

            // TODO: Replace with API
            view.showChartProgress();
            view.showChartLeaderboard();
            view.showBarIdealValue(0, 0, 0, null);
        }, 2000);
    }
}
