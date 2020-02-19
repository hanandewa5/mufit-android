package com.nostratech.mufit.consumer.news;

import android.content.Context;

import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.NewsModel;

import id.mufit.core.base.MvpView;

public class NewsPresenter extends MyBasePresenter implements NewsInterface.Presenter {

    private NewsInterface.View view;
    private NewsModel model;

    public NewsPresenter(Context context, MvpView mvpView, NewsInterface.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void initializeData(NewsModel model) {
        this.model = model;
    }

    @Override
    public void generateNewsUrl() {
        if (!isConnectedToInternet()) {
            getMvpView().showNoInternetError();
        } else {
            String url = model.getSource();
            view.openNewsDetailWebView(url);
        }
    }
}
