package com.nostratech.mufit.consumer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyVoucherModel implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("version")
    @Expose
    private Integer version;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName( "current_quantity")
    @Expose
    private Integer currentQuantity;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("start_date")
    @Expose
    private long startDate;
    @SerializedName("end_date")
    @Expose
    private long endDate;
    @SerializedName("date_created")
    @Expose
    private long dateCreated;
    @SerializedName("usage_type")
    @Expose
    private String usageType;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("trainer_name")
    @Expose
    private String trainerName;
    @SerializedName("speciality_id")
    @Expose
    private String specialityId;
    @SerializedName("trainer_speciality_id")
    @Expose
    private String trainerSpecialityId;
    @SerializedName("trainer_id")
    @Expose
    private String trainerId;
    @SerializedName("speciality_name")
    @Expose
    private String specialityName;

    private boolean isChecked;

    protected MyVoucherModel(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            version = null;
        } else {
            version = in.readInt();
        }
        code = in.readString();
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readInt();
        }
        currentQuantity = in.readInt();
        type = in.readString();
        if (in.readByte() == 0) {
            value = null;
        } else {
            value = in.readInt();
        }
        startDate = in.readLong();
        endDate = in.readLong();
        dateCreated = in.readLong();
        usageType = in.readString();
        packageName = in.readString();
        trainerName = in.readString();
        specialityId = in.readString();
        trainerSpecialityId = in.readString();
        trainerId = in.readString();
        isChecked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        if (version == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(version);
        }
        dest.writeString(code);
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(quantity);
        }
        dest.writeInt(currentQuantity);
        dest.writeString(type);
        if (value == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(value);
        }
        dest.writeLong(startDate);
        dest.writeLong(endDate);
        dest.writeLong(dateCreated);
        dest.writeString(usageType);
        dest.writeString(packageName);
        dest.writeString(trainerName);
        dest.writeString(specialityId);
        dest.writeString(trainerSpecialityId);
        dest.writeString(trainerId);
        dest.writeByte((byte) (isChecked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MyVoucherModel> CREATOR = new Creator<MyVoucherModel>() {
        @Override
        public MyVoucherModel createFromParcel(Parcel in) {
            return new MyVoucherModel(in);
        }

        @Override
        public MyVoucherModel[] newArray(int size) {
            return new MyVoucherModel[size];
        }
    };

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(String specialityId) {
        this.specialityId = specialityId;
    }

    public String getTrainerSpecialityId() {
        return trainerSpecialityId;
    }

    public void setTrainerSpecialityId(String trainerSpecialityId) {
        this.trainerSpecialityId = trainerSpecialityId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
