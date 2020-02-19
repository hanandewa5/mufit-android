package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 5/18/2018.
 */

public class HistoryBookingModel {
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
    @SerializedName("booking_type")
    @Expose
    private String bookingType;
    @SerializedName("booking_specialty")
    @Expose
    private String bookingSpecialty;
    @SerializedName("date_booking")
    @Expose
    private String dateBooking;
    @SerializedName("created_booking_time")
    @Expose
    private long createdBookingTime;
    @SerializedName("booking_shift_list")
    @Expose
    private List<HistoryBookingShiftModel> bookingShiftList = null;
    @SerializedName("cover")
    @Expose
    private String cover;
    @SerializedName("payment_photo")
    @Expose
    private String paymentPhoto;
    @SerializedName("payment_date")
    @Expose
    private Long paymentDate;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("booking_date")
    @Expose
    private long bookingDate;

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

    public long getCreatedBookingTime() {
        return createdBookingTime;
    }

    public void setCreatedBookingTime(Integer createdBookingTime) {
        this.createdBookingTime = createdBookingTime;
    }

    public List<HistoryBookingShiftModel> getBookingShiftList() {
        return bookingShiftList;
    }

    public void setBookingShiftList(List<HistoryBookingShiftModel> bookingShiftList) {
        this.bookingShiftList = bookingShiftList;
    }

    public void setCreatedBookingTime(long createdBookingTime) {
        this.createdBookingTime = createdBookingTime;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCover() {
        return cover;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public String getPaymentPhoto() {
        return paymentPhoto;
    }

    public void setPaymentPhoto(String paymentPhoto) {
        this.paymentPhoto = paymentPhoto;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Long getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Long paymentDate) {
        this.paymentDate = paymentDate;
    }
}
