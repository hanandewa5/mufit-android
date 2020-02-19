package com.nostratech.mufit.consumer.body_composition;

import android.content.Context;

import com.nostratech.mufit.consumer.BuildConfig;
import com.nostratech.mufit.consumer.base.MyBasePresenter;

import id.mufit.core.base.MvpView;

class BodyCompositionPresenter extends MyBasePresenter implements BodyCompositionContract.Presenter {

    private BodyCompositionContract.View view;
    private static final String BODY_COMP_ENDPOINT = "body-composition-detail/";

    public BodyCompositionPresenter(Context context, MvpView mvpView, BodyCompositionContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void generateBodyCompositionUrl(String b2bId) {
        if(!isConnectedToInternet()){
            getMvpView().showNoInternetError();
        } else {

            String url = BuildConfig.ADMIN_PAGE_BASE_URL +
                    BODY_COMP_ENDPOINT +
                    b2bId +
                    "/" +
                    getmAppCache().getUserAccessToken();
            view.openBodyCompositionWebView(url);
        }
    }
}
