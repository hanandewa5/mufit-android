package com.nostratech.mufit.consumer.detailtrainer.helper;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.ArrayList;

public class OneDayDecorator implements DayViewDecorator {

    private ArrayList<CalendarDay> dates;

    public OneDayDecorator(ArrayList<CalendarDay> datesPars) {
        dates = datesPars;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates != null && dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.BLACK));
    }
}