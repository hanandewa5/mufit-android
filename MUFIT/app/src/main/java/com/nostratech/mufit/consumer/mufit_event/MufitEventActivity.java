package com.nostratech.mufit.consumer.mufit_event;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.banner.event.EventDetailActivity;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.model.EventModel;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.InfiniteScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MufitEventActivity extends MyToolbarBackActivity implements MufitEventContract.View,
        MufitEventAdapter.OnEventClickListener,
        InfiniteScrollListener.OnLoadMoreListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //@BindView(R.id.swipeRefresh_news_promo)
    //SwipeRefreshLayout swipeRefreshNewsPromo;
    @BindView(R.id.rv_mufit_event)
    RecyclerView rvMufitEvent;
    @BindView(R.id.shimmer_layout)
    ShimmerFrameLayout mShimmerLayout;
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;

    private MufitEventContract.Presenter presenter;

    private MufitEventAdapter adapter;

    private InfiniteScrollListener infiniteScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mufit_event);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        presenter = new MufitEventPresenter(this, this, this);
        presenter.getMufitEvent();

        //set recyclerview for pagination

        configureRvAdapter();

        //swipe to refresh data

//        swipeRefreshNewsPromo.setColorSchemeResources(R.color.orange);
//        swipeRefreshNewsPromo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshNewsPromo.setRefreshing(false);
//                recyclerViewAdvancedAdapterNewsPromo.clearList();
//                endlessScrollListener.resetState();
////                newsPromoPresenter.getMyVoucher(apiService,
////                        getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN), 0, 100);
//            }
//        });
    }

    private void configureRvAdapter() {
        showLoading();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,  LinearLayoutManager.VERTICAL,false);

        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);
        infiniteScrollListener.setLoaded();

        rvMufitEvent.addOnScrollListener(infiniteScrollListener);
        rvMufitEvent.setHasFixedSize(true);
        rvMufitEvent.setLayoutManager(layoutManager);

        adapter = new MufitEventAdapter(this);
        rvMufitEvent.setAdapter(adapter);
    }

    @Override
    public void showLoading() {
        mShimmerLayout.setVisibility(View.VISIBLE);
        mShimmerLayout.startShimmerAnimation();
    }

    @Override
    public void dismissLoading() {
        mShimmerLayout.stopShimmerAnimation();
        mShimmerLayout.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetError() {
        //TODO: No internet error layout
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public void showEvent(List<EventModel> myEventList, boolean endOfPage) {
        dismissLoading();
        rvMufitEvent.post(() -> {
            if (endOfPage) infiniteScrollListener.addEndOfRequests();
            adapter.updateList(myEventList);

            if (myEventList.size() == 0){
                layoutEmpty.setVisibility(View.VISIBLE);
                rvMufitEvent.setVisibility(View.GONE);
            }else {
                layoutEmpty.setVisibility(View.GONE);
                rvMufitEvent.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void showRecyclerViewLoading() {
        rvMufitEvent.post(() -> adapter.startLoading());
    }

    @Override
    public void dismissRecyclerViewLoading() {
        adapter.stopLoading();
        infiniteScrollListener.setLoaded();
    }

//    @Override
//    public void onClick(EventModel model) {
//        Intent i = new Intent(this, BannerEventDetailActivity.class);
//        i.putExtra(BannerEventDetailActivity.EXTRA_EVENT_TYPE, "event");
//        i.putExtra(BannerEventDetailActivity.EXTRA_EVENT_ID, model);
//        showActivity(i);
//    }


    @Override
    public void onLoadMore() {
        presenter.getMufitEvent();
    }

    @Override
    public void onClick(String eventId) {
        Intent i = new Intent(this, EventDetailActivity.class);
        i.putExtra(Constants.Event.ID, eventId);
        showActivity(i);
    }
}
