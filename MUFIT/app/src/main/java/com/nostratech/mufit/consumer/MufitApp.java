package com.nostratech.mufit.consumer;

import android.content.Context;
import android.content.res.Configuration;
import androidx.multidex.MultiDexApplication;

import com.nostratech.mufit.consumer.utils.LocaleManager;

import id.mufit.core.network.ApiClient;

public class MufitApp extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiClient.getInstance().init(BuildConfig.API_BASE_URL, false);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleManager.setLocale(base));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleManager.setLocale(this);
    }
}