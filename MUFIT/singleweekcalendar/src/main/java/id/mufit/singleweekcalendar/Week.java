package id.mufit.singleweekcalendar;

import java.io.Serializable;

class Week implements Serializable {
    private CalendarDay sun;
    private CalendarDay mon;
    private CalendarDay tue;
    private CalendarDay wed;
    private CalendarDay thu;
    private CalendarDay fri;
    private CalendarDay sat;
    private int month = -1;
    private int year = -1;

    boolean isEmpty(){
        return sun == null &&
                mon == null &&
                tue == null &&
                wed == null &&
                thu == null &&
                fri == null &&
                sat == null;
    }

    public int getMonth(){
        return month;
    }

    private void setMonthYear(CalendarDay day){
        if(month == -1 ) {
            this.month = day.getMonth();
            this.year = day.getYear();
        }
    }

    public void setSun(CalendarDay sun) {
        this.sun = sun;
        setMonthYear(sun);
    }

    public void setMon(CalendarDay mon) {
        this.mon = mon;
        setMonthYear(mon);
    }

    public void setTue(CalendarDay tue) {
        this.tue = tue;
        setMonthYear(tue);
    }

    public void setWed(CalendarDay wed) {
        this.wed = wed;
        setMonthYear(wed);
    }

    public void setThu(CalendarDay thu) {
        this.thu = thu;
        setMonthYear(thu);
    }

    public void setFri(CalendarDay fri) {
        this.fri = fri;
        setMonthYear(fri);
    }

    public void setSat(CalendarDay sat) {
        this.sat = sat;
        setMonthYear(sat);
    }

    public CalendarDay getDay(int pos){
        if(pos == 0) return sun;
        else if(pos == 1) return mon;
        else if (pos ==2) return tue;
        else if(pos ==3) return wed;
        else if(pos ==4) return thu;
        else if(pos ==5) return fri;
        else if(pos ==6) return sat;

        return null;
    }

    public CalendarDay getSun() {
        return sun;
    }

    public CalendarDay getMon() {
        return mon;
    }

    public CalendarDay getTue() {
        return tue;
    }

    public CalendarDay getWed() {
        return wed;
    }

    public CalendarDay getThu() {
        return thu;
    }

    public CalendarDay getFri() {
        return fri;
    }

    public CalendarDay getSat() {
        return sat;
    }

    public int getYear() {
        return year;
    }
}