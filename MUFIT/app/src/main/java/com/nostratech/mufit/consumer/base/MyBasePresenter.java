package com.nostratech.mufit.consumer.base;

import android.content.Context;
import android.util.Log;

import com.nostratech.mufit.consumer.BuildConfig;
import com.nostratech.mufit.consumer.service.ApiClient;
import com.nostratech.mufit.consumer.service.ApiClient2;
import com.nostratech.mufit.consumer.service.ApiService;
import com.nostratech.mufit.consumer.service.ApiService2;
import com.nostratech.mufit.consumer.utils.cache.MyAppCache;

import java.util.ArrayList;
import java.util.List;

import id.mufit.core.base.BasePresenter;
import id.mufit.core.base.MvpView;
//import id.mufit.core.network.ApiClient;
import retrofit2.Call;

public abstract class MyBasePresenter extends BasePresenter<MyAppCache> {

    private static final String TAG = MyBasePresenter.class.getSimpleName();

    private final ApiService apiService;
    private final ApiService2 apiService2;
    private List<Call> ongoingRequests;

    public MyBasePresenter(Context context, MvpView mvpView) {
        super(context, mvpView);
        apiService = ApiClient.createService(ApiService.class);
        apiService2 = ApiClient.createService(ApiService2.class);
        ongoingRequests = new ArrayList<>();
    }

    protected ApiService getApiService(){
        return apiService;
    }

    protected ApiService2 getApiService2(){
        return apiService2;
    }

    protected String getAccessToken(){
        return getmAppCache().getUserAccessToken();
    }

    protected boolean isLoggedIn(){
        return getmAppCache().getUserAccessToken() != null;
    }

    @Override
    protected MyAppCache initCache(Context context) {
        return new MyAppCache(context);
    }

    protected void addRequest(Call call){
        ongoingRequests.add(call);
    }

    protected void removeRequest(Call call){
        ongoingRequests.remove(call);
    }

    public void cancelAllRequests(){
        for(Call call : ongoingRequests){
            call.cancel();
            ongoingRequests.remove(call);
        }
    }

    @Override
    public void onRequestCancelled() {
        if(BuildConfig.DEBUG) Log.d(TAG, "Request cancelled");
    }
}
