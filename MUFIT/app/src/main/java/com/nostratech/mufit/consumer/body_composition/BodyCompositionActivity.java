package com.nostratech.mufit.consumer.body_composition;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.BaseLoadingWebView;
import com.nostratech.mufit.consumer.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
public class BodyCompositionActivity extends BaseLoadingWebView
        implements BodyCompositionContract.View {

    private static final String TAG = BodyCompositionActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    private BodyCompositionContract.Presenter bodyCompositionPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcm);
        ButterKnife.bind(this);
        initToolbar(mToolbar);
        progressBar.setMax(100);

        String b2bId = getIntent().getStringExtra(Constants.B2B.KEY_USER_ID);

        bodyCompositionPresenter = new BodyCompositionPresenter(this, this, this);
        bodyCompositionPresenter.generateBodyCompositionUrl(b2bId);
        setProgressBarCustom(progressBar);
    }

    @Override
    public void openBodyCompositionWebView(String url) {
        mWebView.loadUrl(url);
        mWebView.getSettings().setJavaScriptEnabled(true);
        loadingWebView(mWebView);
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }
}

