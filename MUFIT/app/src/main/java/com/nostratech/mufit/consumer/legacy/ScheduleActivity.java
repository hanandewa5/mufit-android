//package com.nostratech.mufit.consumer.schedule;
//
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.base.BaseMenu;
//import com.nostratech.mufit.consumer.model.schedule.DetailScheduleModel;
//import com.nostratech.mufit.consumer.utils.EventDecorator;
//import com.nostratech.mufit.consumer.legacy.SessionManager;
//import com.prolificinteractive.materialcalendarview.CalendarDay;
//import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
//import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class ScheduleActivity extends BaseMenu implements ScheduleContract.View, OnDateSelectedListener {
//    @BindView(R.id.calendar_view)
//    MaterialCalendarView calendarView;
//    @BindView(R.id.text_schedule_title)
//    TextView textScheduleTitle;
//    @BindView(R.id.rv_schedule_detail)
//    RecyclerView rvScheduleDetail;
//    @BindView(R.id.text_no_schedule)
//    TextView textNoSchedule;
//    private SchedulePresenter schedulePresenter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        ButterKnife.bind(this);
//        initTypefaces();
//        rvScheduleDetail.setNestedScrollingEnabled(false);
//        calendarView.setSelectedDate(Calendar.getInstance());
//        calendarView.setOnDateChangedListener(this);
//        schedulePresenter = new SchedulePresenter(this, this, getContext());
//        schedulePresenter.getUserOverallSchedule(getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//    }
//
//    @Override
//    protected int getContentViewId() {
//        return R.layout.legacy_activity_schedule;
//    }
//
//    @Override
//    protected int getNavigationMenuItemId() {
//        return R.id.navigation_schedule;
//    }
//
//    @Override
//    public void initTypefaces() {
//
//    }
//
//    @Override
//    public void showLogDebug(String tag, String message) {
//        Log.d(tag, message);
//    }
//
//    @Override
//    public void showScheduleOnCalendar(ArrayList<CalendarDay> dates) {
//        dismissLoading();
//        calendarView.addDecorator(new EventDecorator(Color.RED, dates));
//        Long timeStamp = System.currentTimeMillis();
//        String bookDate = timeStamp.toString();
//        schedulePresenter.getScheduleForSelectedDate(getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN), bookDate);
//    }
//
//    @Override
//    public void showScheduleForSelectedDate(List<DetailScheduleModel> detailScheduleList) {
//        dismissLoading();
//        if(detailScheduleList == null || detailScheduleList.isEmpty()){
//            textNoSchedule.setVisibility(View.VISIBLE);
//            rvScheduleDetail.setVisibility(View.GONE);
//        } else {
//            ScheduleAdapter adapter = new ScheduleAdapter(detailScheduleList);
//
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ScheduleActivity.this);
//            rvScheduleDetail.setLayoutManager(layoutManager);
//            rvScheduleDetail.setAdapter(adapter);
//            textNoSchedule.setVisibility(View.GONE);
//            rvScheduleDetail.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
//        Long timestamp = date.getDate().getTime();
//        String bookDate = timestamp.toString();
//        schedulePresenter.getScheduleForSelectedDate(getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN), bookDate);
//    }
//}
