package com.nostratech.mufit.consumer.root;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseActivity;
import com.nostratech.mufit.consumer.detailtrainer.DetailTrainerFragmentAdapter;
import com.nostratech.mufit.consumer.history_booking.FragmentRootHistory;
import com.nostratech.mufit.consumer.home.FragmentRootHome;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.muhealth.MuHealthRootFragment;
import com.nostratech.mufit.consumer.muhealth.onboarding.OnboardingActivity;
import com.nostratech.mufit.consumer.schedule.RootScheduleFragment;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.FontUtils;
import com.nostratech.mufit.consumer.utils.NoScrollViewPager;
import com.nostratech.mufit.consumer.utils.ShowcaseDismissedListener;
import com.nostratech.mufit.consumer.utils.TutorialBuilder;
import com.nostratech.mufit.consumer.utils.TutorialManager;
import com.nostratech.mufit.consumer.utils.ViewPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class RootActivity extends MyBaseActivity implements
        RootContract.View,
        BottomNavigationView.OnNavigationItemSelectedListener,
        FragmentRootHome.OnTutorialFinishListener {

    public enum NavigateTo {
        HISTORY,
        SCHEDULE,
        MUHEALTH
    }

    public static final String EXTRA_REFRESH_FRAGMENT = "refreshFragment";
    public static final int REFRESH_HISTORY = 1;

    public static final String OPEN_HISTORY = "openHistory";


    public static final int HISTORY_DETAIL_REQCODE = 100;
    public static final int LOGIN_ACTIVITY_REQCODE = 101;
    public static final int RATE_REVIEW_REQCODE = 102;
    public static final int OPEN_HISTORY_BOOKING = 180;

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.bottomNav)
    BottomNavigationView navigationView;

    private boolean doubleBackToExit = false;

    FragmentRootHistory fragmentHistory;
    RootScheduleFragment fragmentSchedule;
    FragmentRootHome fragmentRootHome;
    MuHealthRootFragment fragmentMuhealth;
    TutorialManager tutorialManager;

    private final boolean SMOOTH_SCROLL = true;
    //by default is home

    private RootContract.Presenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        ButterKnife.bind(this);
        navigationView.setOnNavigationItemSelectedListener(this);

        tutorialManager = new TutorialManager(this);

        //Show original icon color
        navigationView.setItemIconTintList(null);

        presenter = new RootPresenter(this, this, this);
        presenter.checkLoginStatus();

        setUpViewPager();
        checkIntentExtras();
    }

    private void setUpViewPager(){
        //set viewpager
        DetailTrainerFragmentAdapter adapter = new DetailTrainerFragmentAdapter(getSupportFragmentManager());
        fragmentHistory = new FragmentRootHistory();
        fragmentSchedule = new RootScheduleFragment();
        fragmentRootHome = new FragmentRootHome();
        fragmentRootHome.setListener(this);
        fragmentMuhealth = new MuHealthRootFragment();

        adapter.addFragment(fragmentRootHome, "Home");
        adapter.addFragment(fragmentHistory, "History");
        adapter.addFragment(fragmentSchedule, "Schedule");
        adapter.addFragment(fragmentMuhealth, "Muhealth");
        viewPager.setAdapter(adapter);

        //Disable swipes on view pager
        viewPager.setPagingEnabled(false);

        //number of pages - 1
        viewPager.setOffscreenPageLimit(3);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) ((ViewPagerFragment) fragmentHistory).onFragmentOpenedByUser();
                if (position == 2) ((ViewPagerFragment) fragmentSchedule).onFragmentOpenedByUser();
                if (position == 3) ((ViewPagerFragment) fragmentMuhealth).onFragmentOpenedByUser();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void checkIntentExtras(){
        Bundle data = getIntent().getExtras();

        if(data == null) return;

        //Open history when booking succeeds
        String openHistory = data.getString(OPEN_HISTORY, String.valueOf(OPEN_HISTORY_BOOKING));

        if (openHistory != null) {
            viewPager.setCurrentItem(1,SMOOTH_SCROLL);
            int history = R.id.navigation_history;
            navigationView.setSelectedItemId(history);
        }

        checkNavigationRequest(data);
    }

    /**
     * Other Activities can add an intent containing {@link NavigateTo} to tell
     * {@link RootActivity} which page to open
     */
    private void checkNavigationRequest(Bundle data){
        NavigateTo navigateTo = (NavigateTo) data.getSerializable(Constants.RootNavigation.EXTRA_NAVIGATE_TO);

        if(navigateTo == null) return;

        switch(navigateTo){
            case HISTORY:
                switchPage(R.id.navigation_history);
                break;
            case SCHEDULE:
                switchPage(R.id.navigation_schedule);
                break;
            case MUHEALTH:
                switchPage(R.id.navigation_muhealth);
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_schedule:
                presenter.onScheduleClick();
                return true;
            case R.id.navigation_muhealth:
                presenter.onMuhealthClick();
                return true;
            case R.id.navigation_home:
                presenter.onHomeClick();
                return true;
            case R.id.navigation_history:
                presenter.onHistoryClick();
                return true;
            case R.id.navigation_login:
                presenter.onLoginClick();
                return true;
        }
        return false;
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
        dismissLoading();
        new MufitDialogOneButtonWithText(this,
                getString(R.string.error),
                getString(R.string.no_internet));
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExit = true;
        Toast.makeText(this, getString(R.string.double_back_to_exit), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExit = false, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public void onTutorialFinished() {
        View history = navigationView.findViewById(R.id.navigation_history);
        View schedule = navigationView.findViewById(R.id.navigation_schedule);

        TutorialBuilder tb = new TutorialBuilder(this, FontUtils.getTruenoMedium(this));
        tb.addSequenceItem(history,
                getString(R.string.next),
                getString(R.string.history_showcase_text),
                null);

        tb.addSequenceItem(schedule,
                getString(R.string.finish_showcase),
                getString(R.string.schedule_showcase_text),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        tutorialManager.finishTutorialHome();
                    }
                });

        tb.start();

    }

    //Refreshes the page when user returns from HistoryDetailActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HISTORY_DETAIL_REQCODE || requestCode == RATE_REVIEW_REQCODE){
            if(resultCode == RESULT_OK) {
                fragmentHistory.refreshFragment();
            }
        } else if (requestCode == LOGIN_ACTIVITY_REQCODE){
            presenter.onLoginCancelled();
        }
    }

    @Override
    public void goToHome() {
        viewPager.setCurrentItem(0, SMOOTH_SCROLL);
    }

    @Override
    public void goToHistory() {
        viewPager.setCurrentItem(1, SMOOTH_SCROLL);
    }

    @Override
    public void goToSchedule() {
        viewPager.setCurrentItem(2, SMOOTH_SCROLL);
    }

    @Override
    public void goToLogin(int flag) {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(LoginActivity.FLAG, flag);
        showActivityForResult(i, LOGIN_ACTIVITY_REQCODE);
    }

    @Override
    public void goToMuhealth() {
        viewPager.setCurrentItem(3, SMOOTH_SCROLL);
    }

    @Override
    public void goToMuhealthOnboarding() {
        Intent i = new Intent(this, OnboardingActivity.class);
        showActivity(i);
    }

//    @Override
//    public void showMenu() {
//        MenuSheetView menuSheetView =
//                new MenuSheetView(this, MenuSheetView.MenuType.GRID, getResources().getString(R.string.title_menu),
//                        item -> {
//                            String itemChosen = item.getTitle().toString();
//
//                            if (itemChosen.equalsIgnoreCase(getResources().getString(R.string.logout))) {
//                                MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(this,
//                                        getString(R.string.confirmation),
//                                        getString(R.string.logout_message));
//
//                                dialog.getBtnPositive().setOnClickListener(l -> {
//                                    logoutPresenter.logout();
//                                    dialog.dismiss();
//                                });
//                                dialog.show();
//                            } else if (itemChosen.equalsIgnoreCase(getResources().getString(R.string.settings))) {
//                                startActivity(new Intent(this, SettingsActivity.class));
//                            } else if (itemChosen.equalsIgnoreCase(getResources().getString(R.string.website))) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlWebMufit)));
//                            } else if (itemChosen.equalsIgnoreCase(getResources().getString(R.string.socmed))) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlInstagramMufit)));
//                            } else if (itemChosen.equalsIgnoreCase(getResources().getString(R.string.profile))) {
//                                startActivity(new Intent(this, MuHealthRootFragment.class));
//                            } else if (itemChosen.equalsIgnoreCase(getResources().getString(R.string.faq))) {
//                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlFAQMufit)));
//                            }
//
//                            return true;
//                        });
//    }

    @Override
    public void switchPage(int menuResId) {
        navigationView.setSelectedItemId(menuResId);
    }

    @Override
    public void userIsLoggedIn() {
        navigationView.getMenu().removeItem(R.id.navigation_login);
    }

    @Override
    public void userIsNotLoggedIn() {
        navigationView.getMenu().removeItem(R.id.navigation_muhealth);
    }
}

