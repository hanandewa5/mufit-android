package com.nostratech.mufit.consumer.utils.calendarview;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.w3c.dom.CDATASection;

import java.util.Calendar;
import java.util.List;

public class DisabledDaysDecorator implements DayViewDecorator {
    private List<CalendarDay> disabledDays;

    public DisabledDaysDecorator(List<CalendarDay> disabledDays){
        this.disabledDays = disabledDays;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return day != null && disabledDays.contains(day);
    }

    @Override
    public void decorate(DayViewFacade dayViewFacade) {
        dayViewFacade.setDaysDisabled(true);
    }
}
