package com.nostratech.mufit.consumer.utils.sort;

import com.nostratech.mufit.consumer.model.detail_shift.Shift;

import java.util.Comparator;

public class TimeAscending implements Comparator<Shift> {
    @Override
    public int compare(Shift o1, Shift o2) {
        Integer startTimeHr = o1.getStartTime().getHour();
        Integer endTimeHr = o2.getEndTime().getHour();

        return startTimeHr.compareTo(endTimeHr);
    }
}
