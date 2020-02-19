package com.nostratech.mufit.consumer.banner.advertisement;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertisementDetailActivity extends MyToolbarBackActivity implements AdvertisementDetailContract.View {

    @BindView(R.id.adsDetail_toolbar)
    Toolbar toolbar;

    @BindView(R.id.adsDetail_image)
    RoundedImageView imageAds;

    @BindView(R.id.adsDetail_textAdsTitle)
    TextView textTitle;

    @BindView(R.id.adsDetail_textContent)
    TextView textContent;

    private AdvertisementDetailContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement_detail);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        String adsId = getIntent().getStringExtra(Constants.Advertisement.ID);
        String image = getIntent().getStringExtra(Constants.Advertisement.IMAGE);

        GlideApp.with(this)
                .load(image)
                .placeholder(R.drawable.image_preview_bg)
                .into(imageAds);

        presenter = new AdvertisementDetailPresenter(this,this,this);
        presenter.loadAdvertisementDetail(adsId);
    }

    @Override
    public void showAdvertisementDetail(String title, String content) {
        textTitle.setText(title);
        textContent.setText(content);
    }

    @Override
    public void showLoading() {
        showProgressDialog(this);
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }
}
