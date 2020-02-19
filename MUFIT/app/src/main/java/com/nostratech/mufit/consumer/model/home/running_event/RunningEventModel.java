package com.nostratech.mufit.consumer.model.home.running_event;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RunningEventModel {
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
    private List<RunningEventSpecialityModel> specialityList = null;
    @SerializedName("specialities")
    @Expose
    private String specialities;
    @SerializedName("trainer_list")
    @Expose
    private List<RunningEventTrainerModel> trainerList = null;
    @SerializedName("event_date")
    @Expose
    private long eventDate;
    @SerializedName("registration_start_date")
    @Expose
    private long registrationStartDate;
    @SerializedName("registration_end_date")
    @Expose
    private long registrationEndDate;
    @SerializedName("event_start_time")
    @Expose
    private RunningEventStartTimeModel eventStartTime;
    @SerializedName("event_end_time")
    @Expose
    private RunningEventEndTimeModel eventEndTime;
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
    @SerializedName("type")
    @Expose
    private String type;

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

    public List<RunningEventSpecialityModel> getSpecialityList() {
        return specialityList;
    }

    public void setSpecialityList(List<RunningEventSpecialityModel> specialityList) {
        this.specialityList = specialityList;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public List<RunningEventTrainerModel> getTrainerList() {
        return trainerList;
    }

    public void setTrainerList(List<RunningEventTrainerModel> trainerList) {
        this.trainerList = trainerList;
    }

    public long getEventDate() {
        return eventDate;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public long getRegistrationStartDate() {
        return registrationStartDate;
    }

    public void setRegistrationStartDate(long registrationStartDate) {
        this.registrationStartDate = registrationStartDate;
    }

    public long getRegistrationEndDate() {
        return registrationEndDate;
    }

    public void setRegistrationEndDate(long registrationEndDate) {
        this.registrationEndDate = registrationEndDate;
    }

    public RunningEventStartTimeModel getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(RunningEventStartTimeModel eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public RunningEventEndTimeModel getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(RunningEventEndTimeModel eventEndTime) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
