package com.nostratech.mufit.consumer.model.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreateBookingRequestModel {
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("booking_shift_list")
    @Expose
    private List<BookingShiftList> bookingShiftList = null;
    @SerializedName("booking_speciality_list")
    @Expose
    private List<BookingSpecialityList> bookingSpecialityList = null;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("date_booking")
    @Expose
    private Long dateBooking;
    @SerializedName("join_shift")
    @Expose
    private Boolean joinShift;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("trainer")
    @Expose
    private String trainer;
    @SerializedName("voucher_code")
    @Expose
    private String voucherCode;
    @SerializedName("notes")
    private String notes;

    public CreateBookingRequestModel() {
    }

    public CreateBookingRequestModel(String address, List<BookingShiftList> bookingShiftList, List<BookingSpecialityList> bookingSpecialityList, String customerName, String customerPhone, Long dateBooking, Boolean joinShift, Double latitude, Double longitude, String trainer, String voucherCode, String notes) {
        this.address = address;
        this.bookingShiftList = bookingShiftList;
        this.bookingSpecialityList = bookingSpecialityList;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.dateBooking = dateBooking;
        this.joinShift = joinShift;
        this.latitude = latitude;
        this.longitude = longitude;
        this.trainer = trainer;
        this.voucherCode = voucherCode;
        this.notes = notes;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BookingShiftList> getBookingShiftList() {
        return bookingShiftList;
    }

    public void setBookingShiftList(List<BookingShiftList> bookingShiftList) {
        this.bookingShiftList = bookingShiftList;
    }

    public List<BookingSpecialityList> getBookingSpecialityList() {
        return bookingSpecialityList;
    }

    public void setBookingSpecialityList(List<BookingSpecialityList> bookingSpecialityList) {
        this.bookingSpecialityList = bookingSpecialityList;
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

    public Long getDateBooking() {
        return dateBooking;
    }

    public void setDateBooking(Long dateBooking) {
        this.dateBooking = dateBooking;
    }

    public Boolean getJoinShift() {
        return joinShift;
    }

    public void setJoinShift(Boolean joinShift) {
        this.joinShift = joinShift;
    }


    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
