package com.nostratech.mufit.consumer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nostratech.mufit.consumer.model.banner_event_detail.EventTimeModel;
import com.nostratech.mufit.consumer.model.banner_event_detail.SpecialityEventModel;
import com.nostratech.mufit.consumer.model.banner_event_detail.TrainerListEventModel;

import java.io.Serializable;
import java.util.List;

public class EventModel implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("version")
    @Expose
    private Integer version;

    private String code;
    @SerializedName("quota")
    @Expose
    private Integer quota;
    @SerializedName("current_quota")
    @Expose
    private Integer currentQuota;
    @SerializedName("speciality_list")
    @Expose
    private List<SpecialityEventModel> specialityList = null;
    @SerializedName("trainer_list")
    @Expose
    private List<TrainerListEventModel> trainerList = null;
    @SerializedName("event_date")
    @Expose
    private Long eventDate;
    @SerializedName("registration_start_date")
    @Expose
    private Long registrationStartDate;
    @SerializedName("registration_end_date")
    @Expose
    private Long registrationEndDate;
    @SerializedName("event_start_time")
    @Expose
    private EventTimeModel eventStartTime;
    @SerializedName("event_end_time")
    @Expose
    private EventTimeModel eventEndTime;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("price")
    @Expose
    private Integer price;

    @SerializedName("specialities")
    private String specialities;

    @SerializedName("name")
    private String name;

    @SerializedName("url")
    private String url;

    @SerializedName("description")
    private String desc;

    @SerializedName("available")
    @Expose
    private Boolean available;

    public EventModel(String id,String specialities,String name, String url, String desc){
        this.id = id;
        this.specialities = specialities;
        this.name = name;
        this.url = url;
        this.desc = desc;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getCurrentQuota() {
        return currentQuota;
    }

    public void setCurrentQuota(Integer currentQuota) {
        this.currentQuota = currentQuota;
    }

    public List<SpecialityEventModel> getSpecialityList() {
        return specialityList;
    }

    public void setSpecialityList(List<SpecialityEventModel> specialityList) {
        this.specialityList = specialityList;
    }
    public List<TrainerListEventModel> getTrainerList() {
        return trainerList;
    }

    public void setTrainerList(List<TrainerListEventModel> trainerList) {
        this.trainerList = trainerList;
    }

    public Long getEventDate() {
        return eventDate;
    }

    public void setEventDate(Long eventDate) {
        this.eventDate = eventDate;
    }

    public Long getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(Long registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public Long getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(Long registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public EventTimeModel getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(EventTimeModel eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public EventTimeModel getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(EventTimeModel eventEndTime) {
        this.eventEndTime = eventEndTime;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    public String getSpecialities() {
        return specialities;
    }

    public String getName(){
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getDesc() {
        return desc;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

}
