package com.nostratech.mufit.consumer.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.banner.advertisement.AdvertisementDetailActivity;
import com.nostratech.mufit.consumer.banner.event.EventDetailActivity;
import com.nostratech.mufit.consumer.banner.voucher.VoucherDetailActivity;
import com.nostratech.mufit.consumer.base.MyBaseFragment;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.model.category.CategoryResponseModel;
import com.nostratech.mufit.consumer.model.home.HomeTrainerListModel;
import com.nostratech.mufit.consumer.model.home.running_event.RunningEventModel;
import com.nostratech.mufit.consumer.mufit_event.MufitEventActivity;
import com.nostratech.mufit.consumer.my_voucher.MyVoucherActivity;
import com.nostratech.mufit.consumer.news.NewsDetailActivity;
import com.nostratech.mufit.consumer.news_promo.NewsPromoActivity;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.EndlessScrollListener;
import com.nostratech.mufit.consumer.utils.FontUtils;
import com.nostratech.mufit.consumer.utils.ShowcaseDismissedListener;
import com.nostratech.mufit.consumer.utils.TutorialBuilder;
import com.nostratech.mufit.consumer.utils.TutorialManager;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ViewListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.mufit.core.data.AppCache;
import id.mufit.core.dialog.BaseMufitDialog;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class FragmentRootHome extends MyBaseFragment implements HomeContract.View,
        HomeHotNewsAdapter.OnNewsClickListener,
        HomeSubCategoryAdapter.OnCategoryClickedListener{

//    @BindView(R.id.text_greetings)
//    TextView textGreetings;
//    @BindView(R.id.text_greetings_name)
//    TextView textGreetingsName;

    @BindView(R.id.swipeRefresh_home)
    SwipeRefreshLayout swipeRefreshHome;
    @BindView(R.id.layout_scroll)
    NestedScrollView layoutScroll;
    @BindView(R.id.layout_noInternet)
    LinearLayout layoutNoInternet;
    @BindView(R.id.carousel_event)
    CarouselView carouselEvent;

    @BindView(R.id.rv_ourTrainer)
    RecyclerView rvOurTrainer;
    @BindView(R.id.bagde_notification_my_voucher)
    View bagdeNotificationMyVoucher;
    @BindView(R.id.rv_subcategories)
    RecyclerView rvSubCategories;

    @BindView(R.id.whatsHot_label)
    TextView textTitleHotNews;
    @BindView(R.id.whatsHot_layout)
    ViewGroup layoutWhatsHot;
    @BindView(R.id.whatsHot_articleImage)
    ImageView imageWhatsHot;
    @BindView(R.id.whatsHot_articleTitle)
    TextView textWhatsHotArticleTitle;
    @BindView(R.id.whatsHot_articleDescription)
    TextView textWhatsHotArticleDescription;

//    @BindView(R.id.rv_hotnews)
//    RecyclerView rvHotNews;

    @BindView(R.id.shimmer_layout_banner)
    ShimmerFrameLayout preloadBanner;
    @BindView(R.id.shimmer_layout_what_hots)
    ShimmerFrameLayout preloadWhatHots;
    @BindView(R.id.shimmer_layout_list_trainer)
    ShimmerFrameLayout preloadListTrainer;
    @BindView(R.id.layout_preload_list_trainer)
    LinearLayout mpreloadListTrainer;
    @BindView(R.id.preload_banner)
    LinearLayout mpreloadBanner;
    @BindView(R.id.image_home_menu_mufitEvent)
    CircleImageView mufitEvent;
    @BindView(R.id.text_searchAll)
    TextView textSearchAll;
    @BindView(R.id.text_categoriesTitle)
    TextView textCategoriesTitle;

    private HomeTrainerListAdapter homeTrainerListAdapter;
    private HomeSubCategoryAdapter homeCategoryAdapter;
    private com.nostratech.mufit.consumer.utils.EndlessScrollListener endlessScrollListener;
    private HomeContract.Presenter homePresenter;

    //Counter for Trainer List
    private int trainerListPageCounter = 0;
    TutorialManager tutorialManager;
    private OnTutorialFinishListener listener;

    private BaseMufitDialog dialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_home, container, false);

        ButterKnife.bind(this,view);

        tutorialManager = new TutorialManager(getActivity());

        presentShowcaseSequence();

        configureTrainerListRv();

        homePresenter = new HomePresenter(getActivity(), this, this);
        homePresenter.getListRunningEvent();
        homePresenter.getListTrainer(trainerListPageCounter);
        homePresenter.getHotNews();
        homePresenter.getFirstName(getActivity());
        homePresenter.getListCategory();
        homePresenter.getTotalMyVoucher();

        //swipe to refresh
        swipeRefreshHome.setColorSchemeResources(R.color.orange);
        swipeRefreshHome.setOnRefreshListener(() -> {

            //TODO : set counter menjadi 0 lagi. mulai dari index pertama lagi, clear list adapter trainer, reset state endless scroll
            trainerListPageCounter = 0;
            homeTrainerListAdapter.clearList();
            endlessScrollListener.resetState();

            homePresenter.getFirstName(getActivity());

            layoutScroll.setVisibility(View.VISIBLE);
            layoutNoInternet.setVisibility(View.GONE);

            homePresenter.getListRunningEvent();

            showListTrainerLoading();

            homePresenter.getListTrainer(trainerListPageCounter);

            homePresenter.getHotNews();

            homePresenter.getListCategory();

            swipeRefreshHome.setRefreshing(false);
        });


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void showConsName(String name) {
//        textGreetingsName.setText(" " + name + "!");
    }

    @Override
    public void openMyVoucherActivity() {
        Intent i=new Intent(getContext(), MyVoucherActivity.class);
        i.putExtra("previousActivity", Constants.IntentExtras.VOUCHER_ACTIVITY);
        startActivity(i);
    }

    @Override
    public void openMyVoucherUserNotLoggedIn() {
        navigateToLogin(LoginActivity.OPEN_VOUCHER_ACTIVITY);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoInternetError() {
        //TODO: Implement No Internet Error
    }

    @Override
    public void showGenericError(String errorMessage) {
        //Clear previous dialog before showing a new one
        if(dialog!= null) dialog.dismiss();
        dialog = new MufitDialogOneButtonWithText(getContext(),
                null,
                errorMessage);
    }

    @Override
    public void doShowListTrainer(List<HomeTrainerListModel> homeTrainerListModel) {
//        dismissLoading();
//        showListTrainerLoading();
        dismissListTrainerLoading();
        //kalo jumlah adapter item sebelumnya kurang dari counter page + 1, baru dimasukan ke list
        if (homeTrainerListAdapter.getItemCount() < ((trainerListPageCounter + 1) * homePresenter.getLimit())) {
            homeTrainerListAdapter.updateList(homeTrainerListModel);
        }
        trainerListPageCounter++;
    }

    @Override
    public void doShowListRunningEvent(List<RunningEventModel> runningEventModels) {
//        dismissLoading();
        if(runningEventModels.size() == 0){
            preloadBanner.setVisibility(View.GONE);
        } else {
            carouselEvent.setViewListener(new CarouselViewListener(runningEventModels));
            carouselEvent.setPageCount(runningEventModels.size());
            carouselEvent.setImageClickListener(new CarouselOnClickListener(runningEventModels));
        }
    }

    @Override
    public void showHotNews(NewsModel hotNews) {
        if(hotNews == null){
            //Hide the label and layout what's hot
            textTitleHotNews.setVisibility(View.GONE);
            layoutWhatsHot.setVisibility(View.GONE);
//            rvHotNews.setVisibility(View.GONE);
        } else {
            textWhatsHotArticleDescription.setText(hotNews.getDescription());
            textWhatsHotArticleTitle.setText(hotNews.getTitle());

            String imageUrl = hotNews.getImageUrl();
            if (imageUrl != null && !imageUrl.isEmpty()) {
                GlideApp.with(getContext())
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .into(imageWhatsHot);
            }

            layoutWhatsHot.setOnClickListener(v -> onNewsClick(hotNews));
//            HomeHotNewsAdapter adapter = new HomeHotNewsAdapter(hotNews, this);
//            rvHotNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//            rvHotNews.setHasFixedSize(true);
//            rvHotNews.setAdapter(adapter);
        }
    }

    @Override
    public void getTotalUserVoucher(Integer totalUserVoucher) {
        dismissLoading();
        if (totalUserVoucher != 0) {
            bagdeNotificationMyVoucher.setVisibility(View.VISIBLE);
        }
        else {
            bagdeNotificationMyVoucher.setVisibility(View.GONE);
        }

    }

    @Override
    public void showBannerLoading() {
        mpreloadBanner.setVisibility(View.VISIBLE);
        //preloadBanner.setVisibility(View.VISIBLE);
        preloadBanner.startShimmerAnimation();
    }

    @Override
    public void dismissBannerLoading() {
        preloadBanner.stopShimmerAnimation();
        //preloadBanner.setVisibility(View.GONE);
        mpreloadBanner.setVisibility(View.GONE);
    }

    @Override
    public void showListTrainerLoading() {
        mpreloadListTrainer.setVisibility(View.VISIBLE);
        preloadListTrainer.startShimmerAnimation();
    }

    @Override
    public void dismissListTrainerLoading() {
        preloadListTrainer.stopShimmerAnimation();
        mpreloadListTrainer.setVisibility(View.GONE);

    }

    @Override
    public void showWhatHotsLoading() {
        preloadWhatHots.setVisibility(View.VISIBLE);
        preloadWhatHots.startShimmerAnimation();
        layoutWhatsHot.setVisibility(View.GONE);
    }

    @Override
    public void dismissWhatHotsLoading() {
        preloadWhatHots.stopShimmerAnimation();
        preloadWhatHots.setVisibility(View.GONE);
        layoutWhatsHot.setVisibility(View.VISIBLE);
    }

    @Override
    public void showListCategories(List<CategoryResponseModel> categories) {
        homeCategoryAdapter = new HomeSubCategoryAdapter(categories);
        homeCategoryAdapter.setListener(this);

        rvSubCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvSubCategories.setHasFixedSize(true);
        rvSubCategories.setAdapter(homeCategoryAdapter);
    }

    @OnClick({R.id.text_searchAll, R.id.image_home_menu_trainer, R.id.image_home_menu_mufitEvent, R.id.image_home_menu_NewsPromo, R.id.image_home_menu_my_voucher})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text_searchAll:
            case R.id.image_home_menu_trainer:
                startActivity(new Intent(getContext(), SearchTrainerActivity.class));
                break;
            case R.id.image_home_menu_mufitEvent:
                showActivity(new Intent(getContext(), MufitEventActivity.class));
                break;
            case R.id.image_home_menu_NewsPromo:
                startActivity(new Intent(getContext(), NewsPromoActivity.class));
                break;
            case R.id.image_home_menu_my_voucher:
                homePresenter.onMyVoucherClick();
                break;
        }
    }

    private void configureTrainerListRv() {
//        dismissListTrainerLoading();
        showListTrainerLoading();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvOurTrainer.setLayoutManager(linearLayoutManager);
        homeTrainerListAdapter = new HomeTrainerListAdapter(new AppCache(getActivity()));
        rvOurTrainer.setAdapter(homeTrainerListAdapter);
        rvOurTrainer.setNestedScrollingEnabled(false);
        endlessScrollListener = new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                dismissListTrainerLoading();
                homePresenter.getListTrainer(trainerListPageCounter);
            }
        };
        rvOurTrainer.addOnScrollListener(endlessScrollListener);
    }

    //Play carousel when user returns to this page
    @Override
    public void onResume() {
        super.onResume();
        carouselEvent.playCarousel();
    }

    //Pause carousel when user navigates to a new page (i.e. BannerEventDetailActivity)
    @Override
    public void onPause() {
        super.onPause();
        carouselEvent.pauseCarousel();
    }

    @Override
    public void onNewsClick(NewsModel model) {
        Intent i = new Intent(getContext(), NewsDetailActivity.class);
        i.putExtra(NewsDetailActivity.EXTRA_NEWS_MODEL, model);
        startActivity(i);
    }

    @Override
    public void onCategoryCardClicked(CategoryResponseModel model) {
        Intent i = new Intent(getActivity(), SubCategoriesActivity.class);
        i.putExtra(SubCategoriesActivity.EXTRA_CATEGORY_MODEL, model);
        showActivity(i);
    }

    public void setListener(OnTutorialFinishListener listener) {
        this.listener = listener;
    }

    private class CarouselViewListener implements ViewListener {

        private List<RunningEventModel> runningEventModels;

        CarouselViewListener(List<RunningEventModel> runningEventModels){
            this.runningEventModels = runningEventModels;
        }

        @Override
        public View setViewForPosition(int position) {
            View customView = getLayoutInflater().inflate(R.layout.event_slider, null);
            ImageView imageView = customView.findViewById(R.id.image_eventSlider);
            final RunningEventModel event = runningEventModels.get(position);
            GlideApp.with(getContext())
                    .load(event.getUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.grey)
                    .centerCrop()
                    .into(imageView);
            //set view attributes here
            return customView;
        }
    }

    private class CarouselOnClickListener implements ImageClickListener{

        private List<RunningEventModel> runningEventModels;

        private CarouselOnClickListener(List<RunningEventModel> runningEventModels) {
            this.runningEventModels = runningEventModels;
        }

        @Override
        public void onClick(int position) {
            final RunningEventModel event = runningEventModels.get(position);
            Intent i = null;
            switch(event.getType()){
                case Constants.EventType.VOUCHER:
                    i = new Intent(getContext(), VoucherDetailActivity.class);
                    i.putExtra(Constants.Voucher.ID, event.getId());
                    i.putExtra(Constants.Voucher.IMAGE, event.getUrl());
                    break;
                case Constants.EventType.ADVERTISEMENT:
                    i = new Intent(getContext(), AdvertisementDetailActivity.class);
                    i.putExtra(Constants.Advertisement.ID, event.getId());
                    i.putExtra(Constants.Advertisement.IMAGE, event.getUrl());
                    break;
                case Constants.EventType.EVENT:
                    i = new Intent(getContext(), EventDetailActivity.class);
                    i.putExtra(Constants.Event.ID, event.getId());
//                    i.putExtra("event_id", event.getId());
//                    i.putExtra("event_image", event.getUrl());
//                    i.putExtra("event_type", event.getType());
                    break;
            }

            showActivity(i);
        }
    }

    public void presentShowcaseSequence() {
        if (tutorialManager.isTutorialHomeFinished()) return;

        TutorialBuilder tb = new TutorialBuilder(getActivity(), FontUtils.getTruenoMedium(getContext()));

        tb.addSequenceItem(mufitEvent,
                getString(R.string.next),
                getString(R.string.event_showcase_text),
                null);

        tb.addSequenceItemRectangleShape(textSearchAll,
                getString(R.string.next),
                getString(R.string.search_all_showcase_text),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        layoutScroll.post(new Runnable() {
                            public void run() {
                                layoutScroll.fullScroll(layoutScroll.FOCUS_DOWN);

                                Handler handler = new Handler();

                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCategoryHome();
                                    }
                                }, 100);

                            }
                        });
                    }
                },
                false);
        tb.start();
    }

    public void showCategoryHome() {
        TutorialBuilder tb = new TutorialBuilder(getActivity(), FontUtils.getTruenoMedium(getContext()));

        tb.addSequenceItemRectangleShape(rvSubCategories,
                getString(R.string.next),
                getString(R.string.categories_showcase_text),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        listener.onTutorialFinished();

                    }
                },
                true);

        tb.start();
    }

    public interface OnTutorialFinishListener{
        void onTutorialFinished();
    }
}
