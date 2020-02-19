package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmadan Ditiananda on 5/30/2018.
 */

public class RateReviewRequestModel {
    @SerializedName("booking_id")
    @Expose
    private String bookingId;
    @SerializedName("rating_trainer")
    @Expose
    private Integer ratingTrainer;
    @SerializedName("review")
    @Expose
    private String review;

    public RateReviewRequestModel(String bookingId, Integer ratingTrainer, String review) {
        this.bookingId = bookingId;
        this.ratingTrainer = ratingTrainer;
        this.review = review;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getRatingTrainer() {
        return ratingTrainer;
    }

    public void setRatingTrainer(Integer ratingTrainer) {
        this.ratingTrainer = ratingTrainer;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
