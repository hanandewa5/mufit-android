package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 5/28/2018.
 */

public class HistoryDetailModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("date_booking")
    @Expose
    private String dateBooking;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("trainer_id")
    @Expose
    private String trainerId;
    @SerializedName("trainer_name")
    @Expose
    private String trainerName;
    @SerializedName("trainer_phone")
    @Expose
    private String trainerPhone;
    @SerializedName("photo_trainer")
    @Expose
    private String photoTrainer;
    @SerializedName("status_booking")
    @Expose
    private String statusBooking;
    @SerializedName("booking_speciality_list")
    @Expose
    private List<BookingSpecialityModel> bookingSpecialityList = null;
    @SerializedName("booking_shift_list")
    @Expose
    private List<BookingShiftListModel> bookingShiftList = null;
    @SerializedName("bill")
    @Expose
    private Integer bill;
    @SerializedName("service_fee")
    @Expose
    private Integer serviceFee;
    @SerializedName("join_shift")
    @Expose
    private Boolean joinShift;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("grand_total")
    @Expose
    private Integer grandTotal;
    @SerializedName("voucher_code")
    @Expose
    private String voucherCode;
    @SerializedName("discount")
    @Expose
    private Integer discount;
    @SerializedName("booking_date")
    @Expose
    private long bookingDate;
    @SerializedName("cover_pic_trainer")
    @Expose
    private String coverPicTrainerUrl;
    @SerializedName("booking_type")
    @Expose
    private String bookingType;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("review")
    @Expose
    private String review;

    public String getReview() {
        return review;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(String dateBooking) {
        this.dateBooking = dateBooking;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerPhone() {
        return trainerPhone;
    }

    public void setTrainerPhone(String trainerPhone) {
        this.trainerPhone = trainerPhone;
    }

    public String getPhotoTrainer() {
        return photoTrainer;
    }

    public void setPhotoTrainer(String photoTrainer) {
        this.photoTrainer = photoTrainer;
    }

    public String getStatusBooking() {
        return statusBooking;
    }

    public void setStatusBooking(String statusBooking) {
        this.statusBooking = statusBooking;
    }

    public List<BookingSpecialityModel> getBookingSpecialityList() {
        return bookingSpecialityList;
    }

    public void setBookingSpecialityList(List<BookingSpecialityModel> bookingSpecialityList) {
        this.bookingSpecialityList = bookingSpecialityList;
    }

    public List<BookingShiftListModel> getBookingShiftList() {
        return bookingShiftList;
    }

    public void setBookingShiftList(List<BookingShiftListModel> bookingShiftList) {
        this.bookingShiftList = bookingShiftList;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public Boolean getJoinShift() {
        return joinShift;
    }

    public void setJoinShift(Boolean joinShift) {
        this.joinShift = joinShift;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Integer grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public long getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(long bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public String getCoverPicTrainerUrl() {
        return coverPicTrainerUrl;
    }

    public String getBookingType() {
        return bookingType;
    }

    public String getRating() {
        return rating;
    }

    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }
}
