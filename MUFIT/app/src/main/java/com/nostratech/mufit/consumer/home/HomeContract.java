package com.nostratech.mufit.consumer.home;

import android.content.Context;

import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.model.category.CategoryResponseModel;
import com.nostratech.mufit.consumer.model.home.HomeTrainerListModel;
import com.nostratech.mufit.consumer.model.home.running_event.RunningEventModel;

import java.util.List;

public interface HomeContract {
    interface View {
        void doShowListTrainer (List<HomeTrainerListModel> homeTrainerListModel);;
        void doShowListRunningEvent (List<RunningEventModel> runningEventModels);
        void showHotNews(NewsModel hotNews);
//        void updateEmailToSession(ProfileModel profileModel);
        void getTotalUserVoucher(Integer totalUserVoucher);
        void showBannerLoading();
        void dismissBannerLoading();
        void showListTrainerLoading();
        void dismissListTrainerLoading();
        void showWhatHotsLoading();
        void dismissWhatHotsLoading();
        void showListCategories(List<CategoryResponseModel> categories);
        void showConsName(String name);

        void openMyVoucherActivity();
        void openMyVoucherUserNotLoggedIn();
    }

    interface Presenter {
        void getListTrainer(int counterPage);
//        void getListSpeciality(ApiService apiService, String token);
        void getListRunningEvent ();
        void getHotNews();
        int getLimit();
//        void getProfileDetail(ApiService apiService, String token);
        void getTotalMyVoucher();
        void getListCategory();
        void getFirstName(Context context);
        void onMyVoucherClick();
    }
}
