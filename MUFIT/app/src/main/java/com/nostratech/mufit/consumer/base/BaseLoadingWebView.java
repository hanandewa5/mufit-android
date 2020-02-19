package com.nostratech.mufit.consumer.base;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

public abstract class BaseLoadingWebView extends MyToolbarBackActivity {

    private ProgressBar progressBar;
    private WebView webView;
    private int darkRed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setProgressBarCustom(ProgressBar progressBar) {
        this.progressBar = progressBar;

        darkRed = Color.parseColor("#B53000");
        progressBar.getProgressDrawable().setColorFilter(
                darkRed, PorterDuff.Mode.MULTIPLY);
    }

    public void loadingWebView(WebView webiew){
        this.webView = webiew;
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
                if (newProgress == 100){
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
