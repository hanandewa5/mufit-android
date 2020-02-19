package com.nostratech.mufit.consumer.news_promo;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.model.PublicVoucherModel;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.PaginatedResponseModel;
import retrofit2.Call;

class NewsPromoPresenter extends MyBasePresenter implements NewsPromoContract.Presenter {

    private NewsPromoContract.View view;

    private final int PAGE_LIMIT = 5;

    //Is incremented when either loadNews/loadVoucher has finished
    //Used to dismiss the initial shimmering loading effect
    private TaskCounter taskCounter;

    private int pageCounterNews = 0;
    private int pageCounterPromo = 0;

    NewsPromoPresenter(Context context,
                              MvpView mvpView,
                              NewsPromoContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    class TaskCounter{
        private int numberOfTasks;
        private Runnable onFinish;

        public TaskCounter(int numberOfTasks, Runnable onFinish){
            this.numberOfTasks = numberOfTasks;
            this.onFinish = onFinish;
        }

        void finishTask(){
            this.numberOfTasks--;
            if(numberOfTasks == 0){
                onFinish.run();
            }
        }
    }


    @Override
    public void loadNewsAndVouchers() {
        getMvpView().showLoading();
        taskCounter = new TaskCounter(2, this::finishInitialLoad);
        if(isConnectedToInternet()){
            loadNews();
            loadVouchers();
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void loadNews() {

        Call<PaginatedResponseModel> call = getApiService().getActiveNews(getAccessToken(), pageCounterNews, PAGE_LIMIT);
        call.enqueue(new RetrofitCallback<>(this, response -> {
            boolean endOfList = false;
            int currentPage = response.body().getPages();
            if (currentPage == pageCounterNews + 1) {
                endOfList = true;
            }
            JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
            Type dataType = new TypeToken<Collection<NewsModel>>() {
            }.getType();
            List<NewsModel> newsList = getGson().fromJson(jsonArray, dataType);

            view.addNews(newsList, endOfList);

            pageCounterNews++;

            //Signal that loadNews has completed
            if(taskCounter != null) taskCounter.finishTask();
        }));
    }

    @Override
    public void loadVouchers() {
        Call<PaginatedResponseModel> call = getApiService().getActiveVouchers(getAccessToken(), pageCounterPromo, PAGE_LIMIT, "public");
        call.enqueue(new RetrofitCallback<>(this, response -> {
            boolean endOfList = false;
            int currentPage = response.body().getPages();

            //Page counter will always be 1 number behind
            if (currentPage == pageCounterPromo + 1) {
                endOfList = true;
            }
            JsonArray jsonArray = getGson().toJsonTree(response.body().getResult()).getAsJsonArray();
            Type dataType = new TypeToken<Collection<PublicVoucherModel>>() {
            }.getType();
            List<PublicVoucherModel> voucherList = getGson().fromJson(jsonArray, dataType);

            view.addVouchers(voucherList, endOfList);
            pageCounterPromo++;

            //Signal that loadVouchers has completed
            if(taskCounter != null) taskCounter.finishTask();
        }));
    }

    private void finishInitialLoad(){
        getMvpView().dismissLoading();
        this.taskCounter = null;
    }
}
