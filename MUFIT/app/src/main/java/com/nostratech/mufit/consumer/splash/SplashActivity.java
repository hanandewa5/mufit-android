package com.nostratech.mufit.consumer.splash;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.crashlytics.android.Crashlytics;
import com.nostratech.mufit.consumer.BuildConfig;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseActivity;
import com.nostratech.mufit.consumer.history_detail.HistoryDetailActivity;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.Constants;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends MyBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (BuildConfig.BUILD_TYPE.equalsIgnoreCase("RELEASE")){
            Fabric.with(this, new Crashlytics());
        }
        Bundle intentExtras = getIntent().getExtras();

        //Open HistoryDetailActivity when user receives Firebase push notification
        if (intentExtras != null) {
            String bookingId = intentExtras.getString(Constants.FirebaseNotificationDataPayload.BOOKING_ID);
            if (bookingId == null) {
                launchRootActivity();
            } else {

                Intent root = new Intent(this, RootActivity.class);
                root.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Intent i = new Intent(this, HistoryDetailActivity.class);
                i.putExtra(HistoryDetailActivity.EXTRA_BOOKING_ID, bookingId);

                TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                stackBuilder.addNextIntentWithParentStack(root);
                stackBuilder.addNextIntent(i);
                stackBuilder.startActivities();
            }
        } else {
            launchRootActivity();
        }
    }

    private void launchRootActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showActivity(new Intent(SplashActivity.this, RootActivity.class));
            }
        }, 1000);
    }

    @Override
    public void onBackPressed() {
        // avoid back before finish splashscreen
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoInternetError() {

    }
}
