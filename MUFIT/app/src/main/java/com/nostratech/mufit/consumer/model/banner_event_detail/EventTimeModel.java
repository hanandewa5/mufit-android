package com.nostratech.mufit.consumer.model.banner_event_detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventTimeModel {
    @SerializedName("hour")
    @Expose
    private Integer hour;
    @SerializedName("minute")
    @Expose
    private Integer minute;
    @SerializedName("second")
    @Expose
    private Integer second;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }
    
    public String getAsHHMM(String delimiter){
        return getHourString() + delimiter + getMinuteString();
    }
    
    public String getHourString(){

        if (hour == 0) return "00";

        if (hour < 10) return "0" + hour;

        return hour.toString();
    }

    public String getMinuteString(){

        if (minute == 0) return "00";

        if (minute < 10) return "0" + minute;

        return hour.toString();
    }
}
