package com.nostratech.mufit.consumer.history_booking.history_completed;

import com.nostratech.mufit.consumer.model.HistoryBookingModel;

import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 5/24/2018.
 */

public interface HistoryCompletedContract {
    interface View {
        void doShowHistoryBooking(List<HistoryBookingModel> historyBookingModels, boolean endOfPage);
        void showRecyclerViewLoading();
        void dismissRecyclerViewLoading();
    }

    interface Presenter {
        void getCompletedBooking( String status, int counterPage);
        int getLimit();
    }
}
