package com.nostratech.mufit.consumer.utils.date;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

    public enum Format {
        ddMMyyyy,
        dd_MMMM_yyyy
    }

    final static Calendar cal = Calendar.getInstance();

    public static long getEpochFromDayMonthYear(int day, int month, int year){
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        return cal.getTimeInMillis();
    }

    public static String parseLong(Format format, long epoch){
        String dateFormat = null;
        switch(format){
            case ddMMyyyy:
                dateFormat = "dd/MM/yyyy";
                break;
            case dd_MMMM_yyyy:
                dateFormat = "dd MMMM yyyy";
                break;
        }

        DateFormat dateFormatPayment = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return dateFormatPayment.format(epoch);
    }

    public static boolean isInBetween(long target, long start, long end){
        return target > start && target < end;
    }

}
