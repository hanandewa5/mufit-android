package com.nostratech.mufit.consumer.utils.date;

public class DateFormatter {

    /**
     * Beautify time String into time with "." delimiter
     * @param timeString - String to be formatted
     * @param delimiter - original delimiter to be replaced
     * @return
     */
    public String beautifyTime(String timeString, String delimiter){
        //Format training time into user-friendly format

        String[] timeStringSplit = timeString.split(delimiter);
        return timeStringSplit[0] + "." + timeStringSplit[1];
    }

}
