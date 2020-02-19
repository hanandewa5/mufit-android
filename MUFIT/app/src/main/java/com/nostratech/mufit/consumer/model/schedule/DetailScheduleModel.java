package com.nostratech.mufit.consumer.model.schedule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailScheduleModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("trainer_id")
    @Expose
    private String trainerId;
    @SerializedName("photo_trainer")
    @Expose
    private String photoTrainer;
    @SerializedName("trainer_name")
    @Expose
    private String trainerName;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("status_booking")
    @Expose
    private String statusBooking;
    @SerializedName("booking_specialty")
    @Expose
    private String bookingSpecialty;
    @SerializedName("date_booking")
    @Expose
    private String dateBooking;
    @SerializedName("created_booking_time")
    @Expose
    private Integer createdBookingTime;
    @SerializedName("booking_shift_list")
    @Expose
    private List<BookingShiftList> bookingShiftList = null;

    public DetailScheduleModel(String id, List<BookingShiftList> bookingShiftList) {
        this.id = id;
        this.bookingShiftList = bookingShiftList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getPhotoTrainer() {
        return photoTrainer;
    }

    public void setPhotoTrainer(String photoTrainer) {
        this.photoTrainer = photoTrainer;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getStatusBooking() {
        return statusBooking;
    }

    public void setStatusBooking(String statusBooking) {
        this.statusBooking = statusBooking;
    }

    public String getBookingSpecialty() {
        return bookingSpecialty;
    }

    public void setBookingSpecialty(String bookingSpecialty) {
        this.bookingSpecialty = bookingSpecialty;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public Integer getCreatedBookingTime() {
        return createdBookingTime;
    }

    public void setCreatedBookingTime(Integer createdBookingTime) {
        this.createdBookingTime = createdBookingTime;
    }

    public List<BookingShiftList> getBookingShiftList() {
        return bookingShiftList;
    }

    public void setBookingShiftList(List<BookingShiftList> bookingShiftList) {
        this.bookingShiftList = bookingShiftList;
    }
}
