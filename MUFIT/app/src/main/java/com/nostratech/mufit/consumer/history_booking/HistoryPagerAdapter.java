package com.nostratech.mufit.consumer.history_booking;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * Created by Ahmadan Ditiananda on 5/16/2018.
 */

public class HistoryPagerAdapter extends FragmentStatePagerAdapter {

    int numOfTabs;
    private HistoryOnGoingFragment historyOnGoingFragment;
    private HistoryCompletedFragment historyCompletedFragment;

    public HistoryPagerAdapter(FragmentManager fragmentManager, int numberOfTabs){
        super(fragmentManager);
        this.numOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                if(historyOnGoingFragment == null) historyOnGoingFragment = new HistoryOnGoingFragment();
                return historyOnGoingFragment;
            case 1 :
                if(historyCompletedFragment == null) historyCompletedFragment = new HistoryCompletedFragment();
                return historyCompletedFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
