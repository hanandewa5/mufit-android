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

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseFragment;
import com.nostratech.mufit.consumer.history_booking.history_ongoing.HistoryOnGoingAdapter;
import com.nostratech.mufit.consumer.history_booking.history_ongoing.HistoryOnGoingInterface;
import com.nostratech.mufit.consumer.history_booking.history_ongoing.HistoryOnGoingPresenter;
import com.nostratech.mufit.consumer.history_detail.HistoryDetailActivity;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.model.HistoryBookingModel;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.InfiniteScrollListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HistoryOnGoingFragment extends MyBaseFragment implements HistoryOnGoingInterface.View,
        InfiniteScrollListener.OnLoadMoreListener,
        HistoryOnGoingAdapter.OnCardClickedListener{

    @BindView(R.id.rvOngoingBooking)
    RecyclerView rvOngoingBooking;
    @BindView(R.id.text_historyOngoingEmpty)
    TextView textHistoryOngoingEmpty;
    @BindView(R.id.layout_parentOnGoing)
    RelativeLayout layoutParentOnGoing;
    @BindView(R.id.swipeRefresh_Ongoing)
    SwipeRefreshLayout swipeRefreshOngoing;
    @BindView(R.id.empty_logoOngoing)
    ImageView emptyLogoOngoing;
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    @BindView(R.id.btn_ongoingToListTrainer)
    Button btnOngoingToListTrainer;
    @BindView(R.id.text_noInternet)
    TextView textNoInternet;
    @BindView(R.id.layout_noInternet)
    LinearLayout layoutNoInternet;
    @BindView(R.id.progressBar_loading)
    ProgressBar progressBarLoading;
    @BindView(R.id.shimmer_layout_ongoing)
    ShimmerFrameLayout shimmerLayoutOnGoing;

    private Unbinder unbinder = null;
    private HistoryOnGoingInterface.Presenter onGoingPresenter;
    private HistoryOnGoingAdapter historyOnGoingAdapter;

    private InfiniteScrollListener infiniteScrollListener;

    //pagination

    private int counterPage = 0;

    private boolean isInitialized = false;

    @Override
    public void onResume() {
        super.onResume();
        if (!isInitialized) {
            onGoingPresenter.getOnGoingBooking("ongoing", counterPage);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_on_going, container, false);
        unbinder = ButterKnife.bind(this, view);

        configureRvAdapter();

        onGoingPresenter = new HistoryOnGoingPresenter(getActivity(), this, this);

        swipeRefreshOngoing.setColorSchemeResources(R.color.orange);
        swipeRefreshOngoing.setOnRefreshListener(this::refresh);

        return view;
    }

    public void refresh(){
        rvOngoingBooking.setVisibility(View.VISIBLE);
        layoutNoInternet.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.GONE);

        swipeRefreshOngoing.setRefreshing(false);
        counterPage = 0;
        historyOnGoingAdapter.clearList();
        infiniteScrollListener.reset();
        onGoingPresenter.getOnGoingBooking("ongoing", counterPage);
    }

    private void configureRvAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);
        infiniteScrollListener.setLoaded();
        rvOngoingBooking.setLayoutManager(layoutManager);
        historyOnGoingAdapter = new HistoryOnGoingAdapter(this);
        rvOngoingBooking.addOnScrollListener(infiniteScrollListener);
        rvOngoingBooking.setAdapter(historyOnGoingAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        //progressBarLoading.setVisibility(View.VISIBLE);
        shimmerLayoutOnGoing.setVisibility(View.VISIBLE);
        shimmerLayoutOnGoing.startShimmerAnimation();
        rvOngoingBooking.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        //progressBarLoading.setVisibility(View.GONE);
        shimmerLayoutOnGoing.stopShimmerAnimation();
        shimmerLayoutOnGoing.setVisibility(View.GONE);
        rvOngoingBooking.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetError() {
        layoutNoInternet.setVisibility(View.VISIBLE);
        shimmerLayoutOnGoing.stopShimmerAnimation();
        shimmerLayoutOnGoing.setVisibility(View.GONE);
    }

    @Override
    public void doShowHistoryBooking(List<HistoryBookingModel> historyBookingModels, boolean endOfPage) {
        rvOngoingBooking.post(() -> {
            if (endOfPage) infiniteScrollListener.addEndOfRequests();

            if (counterPage >= 1) {
                dismissRecyclerViewLoading();
            } else {
                dismissLoading();
            }

            if (historyBookingModels.size() > 0) {
                if (historyOnGoingAdapter.getItemCount() < ((counterPage + 1) * onGoingPresenter.getLimit())) {
                    layoutEmpty.setVisibility(View.GONE);
                    historyOnGoingAdapter.updateList(historyBookingModels);
                }
                counterPage++;
            }
            if (historyOnGoingAdapter.getItemCount() == 0) {
                layoutEmpty.setVisibility(View.VISIBLE);
                layoutNoInternet.setVisibility(View.GONE);
            }

            isInitialized = true;
        });
    }

    @Override
    public void showRecyclerViewLoading() {
        rvOngoingBooking.post(() -> historyOnGoingAdapter.startLoading());
    }

    @Override
    public void dismissRecyclerViewLoading() {
        historyOnGoingAdapter.stopLoading();
        infiniteScrollListener.setLoaded();
    }


    @OnClick(R.id.btn_ongoingToListTrainer)
    public void onViewClicked() {
        startActivity(new Intent(getContext(), SearchTrainerActivity.class));
    }

    @Override
    public void onLoadMore() {
        onGoingPresenter.getOnGoingBooking( "ongoing", counterPage);
    }

    @Override
    public void onCardClicked(String bookingId) {
        Intent i = new Intent(getActivity(), HistoryDetailActivity.class);
        i.putExtra(HistoryDetailActivity.EXTRA_BOOKING_ID, bookingId);
        getActivity().startActivityForResult(i, RootActivity.HISTORY_DETAIL_REQCODE);
    }

}
