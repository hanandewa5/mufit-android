package com.nostratech.mufit.consumer.history_booking.history_ongoing;

import com.nostratech.mufit.consumer.model.HistoryBookingModel;

import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 5/22/2018.
 */

public interface HistoryOnGoingInterface {
    interface View {
        void doShowHistoryBooking(List<HistoryBookingModel> historyBookingModels, boolean endOfPage);
        void showRecyclerViewLoading();
        void dismissRecyclerViewLoading();
    }

    interface Presenter {
        void getOnGoingBooking(String status, int counterPage);
        int getLimit();
    }
}
