package com.nostratech.mufit.consumer.cancel_booking;

/**
 * Created by Ahmadan Ditiananda on 6/1/2018.
 */

public interface CancelBookingContract {

    interface View {
        void showValidationEmpty();
        void cancelBookingSuccess();
    }

    interface Presenter {
        void cancelBooking (String description, String id);
    }
}
