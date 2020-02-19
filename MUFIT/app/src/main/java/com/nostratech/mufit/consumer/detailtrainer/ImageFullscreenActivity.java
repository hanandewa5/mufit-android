package com.nostratech.mufit.consumer.detailtrainer;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageFullscreenActivity extends MyToolbarBackActivity {

    @BindView(R.id.toolbar_menu)
    Toolbar toolbar;
    @BindView(R.id.profile_trainer_detail)
    ImageView imageTrainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        Bundle bundle = getIntent().getExtras();

        GlideApp.with(this)
                .load(bundle.getString("image"))
                .thumbnail(0.1f)
                .into(imageTrainer);
        Log.d("image", "onCreate: "+bundle.get("image"));
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
