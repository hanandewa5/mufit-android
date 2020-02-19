package com.nostratech.mufit.consumer.news;

import com.nostratech.mufit.consumer.model.NewsModel;

public interface NewsInterface {
    interface View {
        void openNewsDetailWebView(String url);
    }

    interface Presenter {
        void initializeData(NewsModel model);
        void generateNewsUrl();
    }
}
