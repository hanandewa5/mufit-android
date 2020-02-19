package id.mufit.singleweekcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomCalendar extends LinearLayout {

    TextView textMonthYear;
    ImageButton btnPrev;
    ImageButton btnNext;
    View viewBtnPrev;
    View viewBtnNext;
    ViewGroup layoutWeekdays;
    ViewPager pagerDates;

    TextView textSun;
    TextView textMon;
    TextView textTue;
    TextView textWed;
    TextView textThu;
    TextView textFri;
    TextView textSat;

    CalendarAdapter adapter;

    public CustomCalendar(Context context) {
        super(context);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_calendar, this);

        assignViews();

        CalendarAdapter adapter = new CalendarAdapter(getContext());
        setAdapter(adapter);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CustomCalendar, 0, 0);

        setHeaderTextAppearance(a.getResourceId(
                R.styleable.CustomCalendar_cal_headerTextAppearance,
                R.style.TextAppearance_MaterialCalendarWidget_Header
        ));
        setWeekDayTextAppearance(a.getResourceId(
                R.styleable.CustomCalendar_cal_weekDayTextAppearance,
                R.style.TextAppearance_MaterialCalendarWidget_WeekDay
        ));
        setDateTextAppearance(a.getResourceId(
                R.styleable.CustomCalendar_cal_dateTextAppearance,
                R.style.TextAppearance_MaterialCalendarWidget_Date
        ));

    }

    private void setHeaderTextAppearance(int resourceId){
        textMonthYear.setTextAppearance(getContext(), resourceId);
    }

    private void setWeekDayTextAppearance(int resourceId){
        for(int i =0; i < layoutWeekdays.getChildCount(); i++){
            ((TextView) layoutWeekdays.getChildAt(i)).setTextAppearance(getContext(), resourceId);
        }
    }

    private void setDateTextAppearance(int resourceId){
        adapter.setTextAppearanceResId(resourceId);
    }

    public void init(final CalendarAdapter.OnDateSelectedListener listener,
                     Date initialSelectedDate,
                     int months){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        final long today = calendar.getTimeInMillis();

        Handler handler = new Handler();

        //Run on a separate thread to prevent freezing UI
        final Runnable r = () -> {
            DateRange dateRange = new DateRange();
            List<Week> data = dateRange.calculateDates(today, months);
            adapter.setData(data);
            adapter.setOnDateSelectedListener(listener);
            Week firstWeek = data.get(0);
            updateMonthYearText(firstWeek);
            if(initialSelectedDate != null) selectDay(initialSelectedDate);
        };
        handler.post(r);
    }

    public CalendarDay getSelectedDate(){
        return adapter.getSelectedDay();
    }

    public void addTrainerBusyDays(List<CalendarDay> redDays){
        this.adapter.setRedIndicators(redDays);
        for(CalendarDay day : redDays){
            CalendarTextView tv = pagerDates.findViewWithTag(day.toString());
            if(tv != null) tv.drawIndicator(CalendarTextView.PAINT_RED);
        }
    }

    public void addTrainerFreeDays(List<CalendarDay> greenDays){
        this.adapter.addGreenIndicators(greenDays);
        for(CalendarDay day : greenDays){
            CalendarTextView tv = pagerDates.findViewWithTag(day.toString());
            if(tv != null) tv.drawIndicator(CalendarTextView.PAINT_GREEN);
        }
    }

    public void selectDay(Date date) {
        CalendarDay day = CalendarDay.from(date);
        CalendarTextView tv = pagerDates.findViewWithTag(day.toString());
        adapter.performDaySelection(tv, day);
        adapter.notifyDataSetChanged();
    }

    private void assignViews() {
        textMonthYear = findViewById(R.id.monthYear);
        viewBtnPrev = findViewById(R.id.view_btn_prev);
        viewBtnPrev.setOnClickListener(l -> goToPreviousWeek());
        btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(l -> goToPreviousWeek());
        viewBtnNext = findViewById(R.id.view_btn_next);
        viewBtnNext.setOnClickListener(l -> goToNextWeek());
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(l -> goToNextWeek());
        layoutWeekdays = findViewById(R.id.layout_weekDays);
        pagerDates = findViewById(R.id.pagerDates);

        textSun = findViewById(R.id.weekday_sun);
        textMon = findViewById(R.id.weekday_mon);
        textTue = findViewById(R.id.weekday_tue);
        textWed = findViewById(R.id.weekday_wed);
        textThu = findViewById(R.id.weekday_thu);
        textFri = findViewById(R.id.weekday_fri);
        textSat = findViewById(R.id.weekday_sat);

    }

    private void goToPreviousWeek(){
        int currentItem = pagerDates.getCurrentItem();
        if(currentItem > 0 ) pagerDates.setCurrentItem(currentItem - 1, true);
    }

    private void goToNextWeek(){
        int currentItem = pagerDates.getCurrentItem();
        int maxIndex = adapter.getCount();
        if(currentItem < maxIndex - 1) pagerDates.setCurrentItem(currentItem + 1, true);
    }

    public CustomCalendar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAdapter(final PagerAdapter adapter){
        this.adapter = (CalendarAdapter) adapter;

        this.pagerDates.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                updateMonthYearText(((CalendarAdapter) adapter).getItem(i));
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        this.pagerDates.setAdapter(adapter);
        this.pagerDates.setOffscreenPageLimit(adapter.getCount() - 1);
    }



    public void updateMonthYearText(Week week){
        int month = week.getMonth();
        int year = week.getYear();
        String monthText;
        switch (month){
            case Calendar.JANUARY:
                monthText = getResources().getString(R.string.calender_jan);
                break;
            case Calendar.FEBRUARY:
                monthText = getResources().getString(R.string.calender_feb);
                break;
            case Calendar.MARCH:
                monthText = getResources().getString(R.string.calender_mar);
                break;
            case Calendar.APRIL:
                monthText = getResources().getString(R.string.calender_apr);
                break;
            case Calendar.MAY:
                monthText = getResources().getString(R.string.calender_mei);
                break;
            case Calendar.JUNE:
                monthText = getResources().getString(R.string.calender_jun);
                break;
            case Calendar.JULY:
                monthText = getResources().getString(R.string.calender_jul);
                break;
            case Calendar.AUGUST:
                monthText = getResources().getString(R.string.calender_agu);
                break;
            case Calendar.SEPTEMBER:
                monthText = getResources().getString(R.string.calender_sep);
                break;
            case Calendar.OCTOBER:
                monthText = getResources().getString(R.string.calender_okt);
                break;
            case Calendar.NOVEMBER:
                monthText = getResources().getString(R.string.calender_nov);
                break;
            case Calendar.DECEMBER:
                monthText = getResources().getString(R.string.calender_des);
                break;
            default:
                monthText = "(Not Found)";
        }
        this.textMonthYear.setText(monthText + " " + year);
    }


}
