package com.nostratech.mufit.consumer.history_booking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseFragment;
import com.nostratech.mufit.consumer.history_booking.history_completed.HistoryCompletedAdapter;
import com.nostratech.mufit.consumer.history_booking.history_completed.HistoryCompletedContract;
import com.nostratech.mufit.consumer.history_booking.history_completed.HistoryCompletedPresenter;
import com.nostratech.mufit.consumer.history_detail.HistoryDetailActivity;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.model.HistoryBookingModel;
import com.nostratech.mufit.consumer.rate_review.RateReviewActivity;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.InfiniteScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HistoryCompletedFragment extends MyBaseFragment implements HistoryCompletedContract.View,
        InfiniteScrollListener.OnLoadMoreListener,
        HistoryCompletedAdapter.OnCardClickedListener {

    @BindView(R.id.rvCompletedBooking)
    RecyclerView rvCompletedBooking;
    @BindView(R.id.layout_parentCompleted)
    RelativeLayout layoutParentCompleted;
    @BindView(R.id.swipeRefresh_Completed)
    SwipeRefreshLayout swipeRefreshCompleted;
    @BindView(R.id.empty_logoCompleted)
    ImageView emptyLogoCompleted;
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.btn_completedToListTrainer)
    Button btnCompletedToListTrainer;
    @BindView(R.id.text_historyCompletedEmpty)
    TextView textHistoryCompletedEmpty;
    @BindView(R.id.text_noInternet)
    TextView textNoInternet;
    @BindView(R.id.layout_noInternet)
    LinearLayout layoutNoInternet;
    @BindView(R.id.progressBar_loading)
    ProgressBar progressBarLoading;
    @BindView(R.id.shimmer_layout_complete)
    ShimmerFrameLayout shimmerLayoutComplete;

    private Unbinder unbinder;
    private HistoryCompletedPresenter historyCompletedPresenter;
    private HistoryCompletedAdapter historyCompletedAdapter;

    private int counterPage = 0;
    private InfiniteScrollListener infiniteScrollListener;
    private boolean isInitialized = false;

    @Override
    public void onResume() {
        super.onResume();
        if(!isInitialized){
            historyCompletedPresenter.getCompletedBooking("completed", counterPage);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_history_completed, container, false);
        unbinder = ButterKnife.bind(this, view);

        configureRvAdapter();

        historyCompletedPresenter = new HistoryCompletedPresenter(getActivity(), this, this);

        swipeRefreshCompleted.setColorSchemeResources(R.color.orange);
        swipeRefreshCompleted.setOnRefreshListener(this::refresh);

        return view;
    }

    public void refresh(){
        rvCompletedBooking.setVisibility(View.VISIBLE);
        layoutNoInternet.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);
        swipeRefreshCompleted.setRefreshing(false);
        counterPage = 0;
        historyCompletedAdapter.clearList();
        infiniteScrollListener.reset();
        historyCompletedPresenter.getCompletedBooking("completed", counterPage);
    }

    private void configureRvAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);
        infiniteScrollListener.setLoaded();
        rvCompletedBooking.setLayoutManager(layoutManager);
        historyCompletedAdapter = new HistoryCompletedAdapter(this);
        rvCompletedBooking.addOnScrollListener(infiniteScrollListener);
        rvCompletedBooking.setAdapter(historyCompletedAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void doShowHistoryBooking(List<HistoryBookingModel> historyBookingModels, final boolean endOfPage) {
        rvCompletedBooking.post(()->{
            if(endOfPage){
                infiniteScrollListener.addEndOfRequests();
            }
            if(counterPage >= 1) {
                dismissRecyclerViewLoading();
            } else {
                dismissLoading();
            }

            if (historyBookingModels.size() > 0) {
                if (historyCompletedAdapter.getItemCount() < ((counterPage + 1) * historyCompletedPresenter.getLimit())) {
                    historyCompletedAdapter.updateList(historyBookingModels);
                }
                counterPage++;
            }
            if (historyCompletedAdapter.getItemCount() == 0) {
                layoutEmpty.setVisibility(View.VISIBLE);
                layoutNoInternet.setVisibility(View.GONE);
            }

            isInitialized = true;

        });


    }

    @Override
    public void showRecyclerViewLoading() {
        rvCompletedBooking.post(() -> historyCompletedAdapter.startLoading());
    }

    @Override
    public void dismissRecyclerViewLoading() {
        historyCompletedAdapter.stopLoading();
        infiniteScrollListener.setLoaded();
    }

    @Override
    public void showLoading() {
        shimmerLayoutComplete.setVisibility(View.VISIBLE);
        shimmerLayoutComplete.startShimmerAnimation();
        rvCompletedBooking.setVisibility(View.GONE);
        //progressBarLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
        shimmerLayoutComplete.stopShimmerAnimation();
        shimmerLayoutComplete.setVisibility(View.GONE);
        rvCompletedBooking.setVisibility(View.VISIBLE);
        //progressBarLoading.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetError() {
        dismissLoading();
        rvCompletedBooking.setVisibility(View.GONE);
        layoutNoInternet.setVisibility(View.VISIBLE);
        shimmerLayoutComplete.stopShimmerAnimation();
        shimmerLayoutComplete.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_completedToListTrainer)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), SearchTrainerActivity.class));
    }

    @Override
    public void onLoadMore() {
        historyCompletedPresenter.getCompletedBooking("completed", counterPage);
    }

    @Override
    public void onCardClicked(String bookingId) {
        Intent intent = new Intent(getContext(), HistoryDetailActivity.class);
        intent.putExtra(HistoryDetailActivity.EXTRA_BOOKING_ID, bookingId);
        startActivity(intent);
    }

    @Override
    public void onCardCompletedClicked(String bookingId) {
        Intent intent = new Intent(getContext(), RateReviewActivity.class);
        intent.putExtra(RateReviewActivity.EXTRA_BOOKING_ID, bookingId);
        getActivity().startActivityForResult(intent, RootActivity.RATE_REVIEW_REQCODE);
    }
}
