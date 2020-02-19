package com.nostratech.mufit.consumer.history_booking;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.utils.ViewPagerFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Not using MyBaseFragment as there is no corresponding Presenter
 */
public class FragmentRootHistory extends Fragment implements ViewPagerFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolbarHistoryBooking)
    Toolbar toolbarHistoryBooking;

    private HistoryPagerAdapter pagerAdapter;

    private boolean isInit = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_history, container, false);
        ButterKnife.bind(this, view);

        setUpHistoryTab();
        return view;
    }

    private void setUpHistoryTab() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.setTabRippleColor(null);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.on_going)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.completed)));
    }

    @Override
    public void onFragmentOpenedByUser() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {
                //When Fragment is attached to its Activity, initialize the ViewPager and its content
                if (FragmentRootHistory.this.isAdded()) {

                    //If this page has been initialized, skip this process
                    if (isInit) return;

                    pagerAdapter = new HistoryPagerAdapter(FragmentRootHistory.this.getChildFragmentManager(),
                            tabLayout.getTabCount());
                    viewPager.setAdapter(pagerAdapter);
                    viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                    isInit = true;
                }

                //If Fragment is not yet attached, retry in 200 ms
                else {
                    handler.postDelayed(this, 200);
                }
            }
        };

        handler.post(r);
    }

    @Override
    public void refreshFragment() {
        new Handler().postDelayed(() -> {
            ((HistoryOnGoingFragment) pagerAdapter.getItem(0)).refresh();
            ((HistoryCompletedFragment) pagerAdapter.getItem(1)).refresh();
        }, 100);

    }
}
