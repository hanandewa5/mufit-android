package com.nostratech.mufit.consumer.model.banner_event_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BannerEventDetailModel {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
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
    @SerializedName("specialities")
    @Expose
    private String specialities;
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
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("available")
    @Expose
    private Boolean available;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
