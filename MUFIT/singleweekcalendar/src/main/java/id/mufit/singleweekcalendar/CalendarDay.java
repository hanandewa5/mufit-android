package id.mufit.singleweekcalendar;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class CalendarDay implements Serializable {

    private int day;
    private int month;
    private int year;
    private long dateInMillis;

    public CalendarDay(int day, int month, int year, long dateInMillis) {
        this.day = day;
        this.month = month;
        this.year = year;
        this.dateInMillis = dateInMillis;
    }

    public Date getDate(){
        return new Date(dateInMillis);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public static CalendarDay from(Date date){
        if(date == null) return null;

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        return new CalendarDay(day, month, year, date.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CalendarDay that = (CalendarDay) o;

        return day == that.day && month == that.month && year == that.year;
    }


    @Override
    public int hashCode() {
        return hashCode(year, month, day);
    }

    private static int hashCode(int year, int month, int day) {
        //Should produce hashes like "20150401"
        return (year * 10000) + (month * 100) + day;
    }

    @Override
    public String toString() {
        return "CalendarDay{" + year + "-" + month + "-" + day + "}";
    }
}
