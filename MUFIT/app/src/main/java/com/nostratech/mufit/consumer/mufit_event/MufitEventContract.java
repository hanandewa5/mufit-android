package com.nostratech.mufit.consumer.mufit_event;

import com.nostratech.mufit.consumer.model.EventModel;

import java.util.List;

public interface MufitEventContract {

    interface View {
        void showEvent(List<EventModel> myEventList, boolean endOfPage);
        void showRecyclerViewLoading();
        void dismissRecyclerViewLoading();
    }

    interface Presenter {
        void getMufitEvent();
        int getLimit();
    }
}
