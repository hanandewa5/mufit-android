package com.nostratech.mufit.consumer.news_promo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.banner.voucher.VoucherDetailActivity;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.model.PublicVoucherModel;
import com.nostratech.mufit.consumer.news.NewsDetailActivity;
import com.nostratech.mufit.consumer.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsPromoActivity extends MyToolbarBackActivity implements NewsPromoContract.View,
        PublicVoucherAdapter.OnVoucherClickListener,
        NewsAdapter.OnNewsClickListener {

    public static String TAG = NewsPromoActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //    @BindView(R.id.swipeRefresh_news_promo)
//    SwipeRefreshLayout swipeRefreshNewsPromo;
    @BindView(R.id.label_whatshot)
    TextView textWhatsHot;
    @BindView(R.id.text_promo)
    TextView textPromo;
    @BindView(R.id.rv_hotnews)
    RecyclerView rvHotNews;
    @BindView(R.id.rv_news_promo)
    RecyclerView rvNewsPromo;
    @BindView(R.id.shimmer_layout_news_promo)
    ShimmerFrameLayout shimmerLayoutNewsPromo;
    @BindView(R.id.layout_empty_promo)
    LinearLayout layoutEmptyPromo;

    private PublicVoucherAdapter voucherAdapter;
    private NewsAdapter newsAdapter;
    private NewsPromoContract.Presenter presenter;
    Boolean emptyNews = false;
    Boolean emptyVoucher = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_promo);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        presenter = new NewsPromoPresenter(this,
                this,
                this);
        presenter.loadNewsAndVouchers();

        configureRvHotNews();
        configureRvNewsAndPromo();
    }

    @Override
    public void showLoading() {
        shimmerLayoutNewsPromo.setVisibility(View.VISIBLE);
        shimmerLayoutNewsPromo.startShimmerAnimation();
    }

    @Override
    public void dismissLoading() {
        shimmerLayoutNewsPromo.stopShimmerAnimation();
        shimmerLayoutNewsPromo.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }


    public void addNews(List<NewsModel> newsList, boolean isEndOfList) {

        if (newsList.size() == 0) {
            // show empty news
            emptyNews = true;
            textWhatsHot.setVisibility(View.GONE);
            rvHotNews.setVisibility(View.GONE);
            if (emptyVoucher){
                layoutEmptyPromo.setVisibility(View.VISIBLE);
            }
        }else {
            newsAdapter.insertNewData(newsList);
            textWhatsHot.setVisibility(View.VISIBLE);
            rvHotNews.setVisibility(View.VISIBLE);
        }

        if (isEndOfList) {
            //Remove load more text
            newsAdapter.removeLoadMore();
        }

    }


    @Override
    public void addVouchers(List<PublicVoucherModel> voucherList, boolean isEndOfList) {

        if (voucherList.size() == 0) {
            emptyVoucher = true;
            textPromo.setVisibility(View.GONE);
            rvNewsPromo.setVisibility(View.GONE);
            if (emptyNews){
                layoutEmptyPromo.setVisibility(View.VISIBLE);
            }
        } else{
            textPromo.setVisibility(View.VISIBLE);
            rvNewsPromo.setVisibility(View.VISIBLE);
            voucherAdapter.insertNewData(voucherList);
        }

        if (isEndOfList) {
            //Remove load more text
            voucherAdapter.removeLoadMore();
        }
    }

    private void configureRvHotNews(){
        newsAdapter = new NewsAdapter(new ArrayList<>(), this);

        rvHotNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHotNews.setHasFixedSize(false);
        rvHotNews.setAdapter(newsAdapter);
        rvHotNews.setNestedScrollingEnabled(false);

    }

    private void configureRvNewsAndPromo(){
        voucherAdapter = new PublicVoucherAdapter(new ArrayList<>(), this);

        //Init rv
        rvNewsPromo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvNewsPromo.setHasFixedSize(true);
        rvNewsPromo.setAdapter(voucherAdapter);
        rvNewsPromo.setNestedScrollingEnabled(false);
    }

    @Override
    public void showInitialNews(List<NewsModel> newsList) {

        //Init adapter
        newsAdapter = new NewsAdapter(newsList, this);

        //Init rv
        rvHotNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvHotNews.setHasFixedSize(false);
        rvHotNews.setAdapter(newsAdapter);

    }

    @Override
    public void showInitialVouchers(List<PublicVoucherModel> voucherList) {

        //Init adapter
        voucherAdapter = new PublicVoucherAdapter(voucherList, this);

        //Init rv
        rvNewsPromo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvNewsPromo.setHasFixedSize(true);
        rvNewsPromo.setAdapter(voucherAdapter);
    }

    @Override
    public void onVoucherClick(String voucherId, String imageUrl) {
        Intent i = new Intent(this, VoucherDetailActivity.class);
        i.putExtra(Constants.Voucher.ID, voucherId);
        i.putExtra(Constants.Voucher.IMAGE, imageUrl);
        showActivity(i);
    }

    @Override
    public void onLoadMoreClick() {
        presenter.loadVouchers();
    }

    @Override
    public void onNewsLoadMore() {
        presenter.loadNews();
    }

    @Override
    public void onNewsClick(NewsModel model) {
        Intent i = new Intent(this, NewsDetailActivity.class);
        i.putExtra(NewsDetailActivity.EXTRA_NEWS_MODEL, model);
        showActivity(i);
    }
}
