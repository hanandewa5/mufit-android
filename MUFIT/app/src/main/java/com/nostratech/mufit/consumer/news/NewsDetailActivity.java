package com.nostratech.mufit.consumer.news;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.BaseLoadingWebView;
import com.nostratech.mufit.consumer.model.NewsModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends BaseLoadingWebView implements NewsInterface.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    public static final String EXTRA_NEWS_MODEL = "newsModel";
    private static final String TAG = NewsDetailActivity.class.getSimpleName();
    private NewsInterface.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        initToolbar(toolbar);
        Bundle data = getIntent().getExtras();
        NewsModel newsModel = (NewsModel) data.getSerializable(EXTRA_NEWS_MODEL);
        presenter = new NewsPresenter(this, this, this);
        presenter.initializeData(newsModel);
        presenter.generateNewsUrl();
        progressBar.setMax(100);
        setProgressBarCustom(progressBar);
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

    @Override
    public void openNewsDetailWebView(String url) {
        Log.i(TAG, "Opening webview: " + url);
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        loadingWebView(webView);
    }
}
