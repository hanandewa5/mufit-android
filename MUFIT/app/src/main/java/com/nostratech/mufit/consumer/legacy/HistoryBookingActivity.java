//package com.nostratech.mufit.consumer.history_booking;
//
//import android.Manifest;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.design.widget.TabLayout;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.ViewPager;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.ImageView;
//
//import com.flipboard.bottomsheet.BottomSheetLayout;
//import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.base.BaseMenu;
//import com.nostratech.mufit.consumer.root.RootActivity;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class HistoryBookingActivity extends BaseMenu implements
//        HistoryOnGoingFragment.OnFragmentInteractionListener {
//
//    @BindView(R.id.tabLayout)
//    TabLayout tabLayout;
//    @BindView(R.id.viewPager)
//    ViewPager viewPager;
//    @BindView(R.id.toolbarHistoryBooking)
//    Toolbar toolbarHistoryBooking;
//    @BindView(R.id.navigation)
//    BottomNavigationViewEx navigation;
//    @BindView(R.id.bottomsheet)
//    BottomSheetLayout bottomsheet;
//    @BindView(R.id.logo_mufit)
//    ImageView logoMufit;
//
//    final int REQUEST_GALLERY = 1;
//    private static final String READ_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
//    private static final String WRITE_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
//    String statusFromFirebase;
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        getAccessGalleryPermission();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
//
//        Intent intent = getIntent();
//        statusFromFirebase = intent.getStringExtra("statusFromFirebase");
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                viewPager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//
//        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.on_going)));
//        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.completed)));
//
//        toolbarHistoryBooking.setVisibility(View.VISIBLE);
//
//        setSupportActionBar(toolbarHistoryBooking);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        toolbarHistoryBooking.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        final HistoryPagerAdapter pagerAdapter = new HistoryPagerAdapter(getSupportFragmentManager(),
//                tabLayout.getTabCount());
//        viewPager.setAdapter(pagerAdapter);
//        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        if(statusFromFirebase != null) {
//            viewPager.setCurrentItem("ongoing".equals(statusFromFirebase) ? 0 : 1);
//        }else {
//            tabLayout.getTabAt(0).select();
//        }
//    }
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.legacy_activity_history_booking;
//    }
//
//    @Override
//    protected int getNavigationMenuItemId() {
//        return R.id.navigation_history;
//    }
//
//    @Override
//    public void initTypefaces() {
//
//    }
//
//    @Override
//    public void showLogDebug(String tag, String message) {
//
//    }
//
//    @Override
//    public void noInternetError() {
//        dismissProgressDialog();
//        showSnackbarShort(bottomsheet, getResources().getString(R.string.no_internet));
//    }
//
//
//    @Override
//    public void onFragmentInteraction(Uri uri) {
//
//    }
//
//    private void getAccessGalleryPermission() {
//        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//
//        if (ContextCompat.checkSelfPermission(this, READ_STORAGE) == PackageManager.PERMISSION_DENIED) {
//            if (ContextCompat.checkSelfPermission(this, WRITE_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                ActivityCompat.requestPermissions(this, permissions, REQUEST_GALLERY);
//            }
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_GALLERY:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                    startActivity(new Intent(this, RootActivity.class));
//                }
//        }
//    }
//
//}
