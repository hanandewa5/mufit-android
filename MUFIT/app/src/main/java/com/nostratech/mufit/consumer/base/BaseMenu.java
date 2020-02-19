//package com.nostratech.mufit.consumer.base;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.Nullable;
//import android.support.design.widget.BottomNavigationView;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//
//import com.flipboard.bottomsheet.BottomSheetLayout;
//import com.flipboard.bottomsheet.commons.MenuSheetView;
//import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.logout.LogoutPresenter;
//import com.nostratech.mufit.consumer.profile.ProfileActivity;
//import com.nostratech.mufit.consumer.settings.SettingsActivity;
//import com.nostratech.mufit.consumer.legacy.BottomNavigationViewHelper;
//import com.nostratech.mufit.consumer.legacy.SessionManager;
//import com.nostratech.mufit.consumer.utils.dialog.MufitDialogOneButtonWithText;
//import com.nostratech.mufit.consumer.utils.dialog.MufitDialogTwoButtonsWithText;
//
//public abstract class BaseMenu extends BaseActivity implements BaseViewInterface,
//        BottomNavigationView.OnNavigationItemSelectedListener {
//    protected BottomNavigationViewEx navigationView;
//    private BottomSheetLayout bottomSheetLayout;
//    private LogoutPresenter logoutPresenter;
//    private final String urlWebMufit = "https://mufit.id/";
//    private final String urlInstagramMufit = "https://www.instagram.com/mufit.id/?hl=id";
//    private final String urlFAQMufit = "https://mufit.id/faq.html";
//
//    private boolean doubleBackToExit = false;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(getContentViewId());
//        navigationView = findViewById(R.id.navigation);
//        BottomNavigationViewHelper.disableShiftMode(navigationView);
//        navigationView.setOnNavigationItemSelectedListener(this);
//        navigationView.enableAnimation(false);
//        navigationView.enableShiftingMode(false);
//        navigationView.enableItemShiftingMode(false);
//        navigationView.setTypeface(getTfLight());
//
//        //Show original icon color
//        navigationView.setItemIconTintList(null);
//
//        bottomSheetLayout = findViewById(R.id.bottomsheet);
//        bottomSheetLayout.setPeekOnDismiss(true);
//        if(getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN) == null ||
//                getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN).isEmpty()){
//            navigationView.getBottomNavigationItemView(3).setVisibility(View.GONE);
//            navigationView.getBottomNavigationItemView(4).setVisibility(View.VISIBLE);
//        } else {
//            navigationView.getBottomNavigationItemView(3).setVisibility(View.VISIBLE);
//            navigationView.getBottomNavigationItemView(4).setVisibility(View.GONE);
//        }
//        logoutPresenter = new LogoutPresenter(this, getContext());
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        updateNavigationBarState();
//    }
//
//
////    @Override
////    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
////        switch (item.getItemId()) {
////            case R.id.navigation_schedule:
////                if(getNavigationMenuItemId() == R.id.navigation_schedule){
////                    return false;
////                }
////
////                if(!getSessionManager().checkLogin2(2)){
//////                    showActivityFinishClearTask(this, ScheduleActivity.class);
////                    startActivity(new Intent(this, ScheduleActivity.class));
////                    overridePendingTransition(0, 0);
////                    finish();
////                    return true;
////                }
////            case R.id.navigation_menu:
////                item.setIcon(R.drawable.ic_navigasi_menu_orange_2);
////                showMenuSheet(MenuSheetView.MenuType.GRID);
////                return true;
////            case R.id.navigation_home:
////                if(getNavigationMenuItemId() == R.id.navigation_home){
////                    return false;
////                }
////
//////                showActivityFinishClearTask(this, HomeActivity_New.class);
////                startActivity(new Intent(this, RootActivity.class));
////                overridePendingTransition(0, 0);
////                finish();
////                return true;
////            case R.id.navigation_history:
////                if(getNavigationMenuItemId() == R.id.navigation_history){
////                    return false;
////                }
////
////                if(!getSessionManager().checkLogin2(3)){
//////                    showActivityFinishClearTask(this, HistoryBookingActivity.class);
////                    startActivity(new Intent(this, HistoryBookingActivity.class));
////                    overridePendingTransition(0,0);
////                    finish();
////                }
////                return true;
////            case R.id.navigation_login:
////                getSessionManager().checkLogin2(LoginActivity.OPEN_HOME);
////                return true;
////        }
////        return false;
////    }
//
//    private void updateNavigationBarState() {
//        int actionId = getNavigationMenuItemId();
//        selectBottomNavigationBarItem(actionId);
//    }
//
//    void selectBottomNavigationBarItem(int itemId) {
//        Menu menu = navigationView.getMenu();
//        for (int i = 0, size = menu.size(); i < size; i++) {
//            MenuItem item = menu.getItem(i);
//            boolean shouldBeChecked = item.getItemId() == itemId;
//            if (shouldBeChecked) {
//                item.setChecked(true);
//                if(i==0)
//                    item.setIcon(R.drawable.ic_navigasi_home_orange_2);
//                else if (i==1)
//                    item.setIcon(R.drawable.ic_navigasi_history_orange_2);
//                else if (i==2)
//                    item.setIcon(R.drawable.ic_navigasi_schedule_orange_2);
//                else if (i==3)
//                    item.setIcon(R.drawable.ic_navigasi_menu_orange_2);
//                else if (i==4)
//                    item.setIcon(R.drawable.ic_navigasi_login_button_orange_2);
//                break;
//            }
//        }
//    }
//
//    private void showMenuSheet(final MenuSheetView.MenuType menuType) {
//        MenuSheetView menuSheetView =
//                new MenuSheetView(BaseMenu.this, menuType, getResources().getString(R.string.title_menu),
//                        new MenuSheetView.OnMenuItemClickListener() {
//                            @Override
//                            public boolean onMenuItemClick(MenuItem item) {
//                                String itemChosen = item.getTitle().toString();
//
//                                if (itemChosen.equalsIgnoreCase(getResources().getString(R.string.logout))) {
//                                    new MufitDialogTwoButtonsWithText(getContext(),
//                                            getString(R.string.confirmation),
//                                            getString(R.string.logout_message),
//                                            null,
//                                            v -> logoutPresenter.doLogout(getSessionManager()));
//                                }
//                                else if(itemChosen.equalsIgnoreCase(getResources().getString(R.string.settings))){
//                                    startActivity(new Intent(getContext(), SettingsActivity.class));
//                                }
//                                else if(itemChosen.equalsIgnoreCase(getResources().getString(R.string.website))){
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlWebMufit)));
//                                }
//                                else if(itemChosen.equalsIgnoreCase(getResources().getString(R.string.socmed))){
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlInstagramMufit)));
//                                }
//                                else if(itemChosen.equalsIgnoreCase(getResources().getString(R.string.profile))){
//                                    startActivity(new Intent(getContext(), ProfileActivity.class));
//                                }
//                                else if(itemChosen.equalsIgnoreCase(getResources().getString(R.string.faq))){
//                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlFAQMufit)));
//                                }
//
//                                return true;
//                            }
//                        });
//        menuSheetView.inflateMenu(R.menu.menu_element);
//        //Set font family for MenuItems
//        menuSheetView.setTfRegular(getTfLight());
//        bottomSheetLayout.showWithSheetView(menuSheetView);
//    }
//
//    protected abstract int getContentViewId();
//
//    protected abstract int getNavigationMenuItemId();
//
//    @Override
//    public void noInternetError() {
//        dismissLoading();
//        new MufitDialogOneButtonWithText(this,
//                getString(R.string.error),
//                getString(R.string.no_internet),
//                null);
//    }
//    @Override
//    public void showError(String errorMessage) {
//        dismissLoading();
//        new MufitDialogOneButtonWithText(this,
//                getString(R.string.error),
//                errorMessage,
//                null);
//    }
//
//    @Override
//    public void showLoading() {
//        showProgressDialog(getContext());
//    }
//
//    @Override
//    public void dismissLoading() {
//        dismissProgressDialog();
//    }
//
//    @Override
//    public void hideKeyboard() {
//        hideKeyboard(getCurrentFocus());
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (doubleBackToExit) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExit = true;
//        Toast.makeText(this, getString(R.string.double_back_to_exit), Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(() -> doubleBackToExit =false, 2000);
//    }
//}
