package com.nostratech.mufit.consumer.news_promo;

import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.model.PublicVoucherModel;

import java.util.List;

public interface NewsPromoContract {
    interface View {
        void addNews(List<NewsModel> newsList, boolean endOfList);
        void addVouchers(List<PublicVoucherModel> voucherList, boolean endOfList);

        //TODO: remove if unneeded
        void showInitialNews(List<NewsModel> newsList);
        void showInitialVouchers(List<PublicVoucherModel> voucherList);
    }

    interface Presenter {
        void loadNewsAndVouchers();
        void loadNews();
        void loadVouchers();
    }
}
