package com.nostratech.mufit.consumer.search_trainer;

import java.util.Objects;

class SearchQuery{
    private String name;
    private String gender;
    private String speciality;
    private String day;
    private String bookDate;

    public SearchQuery(String name, String gender, String speciality, String day, String bookDate) {
        this.name = name;
        this.gender = gender;
        this.speciality = speciality;
        this.day = day;
        this.bookDate = bookDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchQuery that = (SearchQuery) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(speciality, that.speciality) &&
                Objects.equals(day, that.day) &&
                Objects.equals(bookDate, that.bookDate);
    }

    @Override
    public String toString() {
        return "SearchQuery{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", speciality='" + speciality + '\'' +
                ", day='" + day + '\'' +
                ", bookDate='" + bookDate + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, speciality, day, bookDate);
    }
}