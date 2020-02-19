package com.nostratech.mufit.consumer.rate_review;

/**
 * Created by Ahmadan Ditiananda on 5/30/2018.
 */

public interface RateReviewContract {

    interface View {

        void showErrorEmptyRatingReview();

        void showErrorEmptyRating();

        void showErrorEmptyReview();

        void showTrainerDetail(String name,
                               String profilePic,
                               String coverPic);

        void showTrainerRating(String rating);

        void showReviewSubmissionSuccess();
    }

    interface Presenter {

        void getBookingDetail(String id);

        void submitReview(int rating, String review);
    }
}
