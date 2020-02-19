package com.nostratech.mufit.consumer.model.banner_event_detail.create_booking_event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventBookingRequestModel {

    public EventBookingRequestModel(String customerName, String customerPhone,
                                    String event, List<EventBookingSpecialityRequestModel> specialityList,
                                    String trainer) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.event = event;
        this.specialityList = specialityList;
        this.trainer = trainer;
    }

    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_phone")
    @Expose
    private String customerPhone;
    @SerializedName("event")
    @Expose
    private String event;
    @SerializedName("speciality_list")
    @Expose
    private List<EventBookingSpecialityRequestModel> specialityList;
    @SerializedName("trainer")
    @Expose
    private String trainer;

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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public List<EventBookingSpecialityRequestModel> getSpecialityList() {
        return specialityList;
    }

    public void setSpecialityList(List<EventBookingSpecialityRequestModel> specialityList) {
        this.specialityList = specialityList;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
}
