package com.nostratech.mufit.consumer.model.detail_shift;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shift {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("start_time")
    @Expose
    private StartTime startTime;
    @SerializedName("end_time")
    @Expose
    private EndTime endTime;
    @SerializedName("shift_range")
    @Expose
    private Integer shiftRange;
    @SerializedName("status")
    @Expose
    private Boolean status;

    private boolean isChecked;

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

    public StartTime getStartTime() {
        return startTime;
    }

    public void setStartTime(StartTime startTime) {
        this.startTime = startTime;
    }

    public EndTime getEndTime() {
        return endTime;
    }

    public void setEndTime(EndTime endTime) {
        this.endTime = endTime;
    }

    public Integer getShiftRange() {
        return shiftRange;
    }

    public void setShiftRange(Integer shiftRange) {
        this.shiftRange = shiftRange;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getPrettyShiftTime(){

        String startHr = startTime.getHour().toString();
        if (startHr.length() == 1) {
            startHr = "0" + startHr;
        }

        String startMin = startTime.getMinute().toString();
        if (startMin.length() == 1) {
            startMin = "0" + startMin;
        }
        String prettyStartTime = startHr + "." + startMin;

        String endHr = endTime.getHour().toString();
        if (endHr.length() == 1) {
            endHr = "0" + endHr;
        }
        String endMin = endTime.getMinute().toString();
        if (endMin.length() == 1) {
            endMin = "0" + endMin;
        }

        String prettyEndTime = endHr + "." + endMin;

        String prettyShift = prettyStartTime + "-" + prettyEndTime;

        return prettyShift;
    }
}