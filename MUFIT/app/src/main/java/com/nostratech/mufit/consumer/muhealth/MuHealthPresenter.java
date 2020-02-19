package com.nostratech.mufit.consumer.muhealth;

import android.content.Context;
import android.util.Log;

import com.nostratech.mufit.consumer.base.MyBasePresenter;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;

public class MuHealthPresenter extends MyBasePresenter implements MuHealthContract.Presenter {
    private MuHealthContract.View view;

    MuHealthPresenter(Context context, MvpView mvpView,MuHealthContract.View view){
        super(context,mvpView);
        this.view = view;
    }

    @Override
    public void testAja() {
        Log.e("Test",getApiService().getCategory().toString());
        Call<StandardResponseModel> call = getApiService2().testApiCuy();
        call.enqueue(new RetrofitCallback<>(this,response -> {
            Log.d("RES API",response.toString());
        }));
    }
}
