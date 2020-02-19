package com.nostratech.mufit.consumer.detailtrainer.helper;

import java.util.List;

public class SpecialityTrainerModel{
    private String price;
    private String name;
    private boolean isChecked;
    private String idSpeciality;
    private String equipment;
    private Integer max;

    public SpecialityTrainerModel(String name, String price, List<DurationModel> items, String idSpeciality, String equipment, Integer max) {
//        super(name, items);
        this.price = price;
        this.name = name;
        this.idSpeciality = idSpeciality;
        this.equipment = equipment;
        this.max = max;
    }

    public String getPrice() {
        return price;
    }
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecialityTrainerModel)) return false;

        SpecialityTrainerModel model = (SpecialityTrainerModel) o;

        return getPrice().equals(model.getPrice());

    }

    @Override
    public int hashCode() {
        return getPrice() != null ? getPrice().hashCode() : 0;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(String idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public String getEquipment() {
        return this.equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public Integer getMax() {
        return this.max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}


