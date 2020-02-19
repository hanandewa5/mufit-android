package com.nostratech.mufit.consumer.schedule;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseFragment;
import com.nostratech.mufit.consumer.history_detail.HistoryDetailActivity;
import com.nostratech.mufit.consumer.model.schedule.DetailScheduleModel;
import com.nostratech.mufit.consumer.utils.EventDecorator;
import com.nostratech.mufit.consumer.utils.ViewPagerFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RootScheduleFragment extends MyBaseFragment implements ScheduleContract.View,
        OnDateSelectedListener,
        ViewPagerFragment, ScheduleAdapter.OnSessionClickListener {

    @BindView(R.id.calendar_view)
    MaterialCalendarView calendarView;
    @BindView(R.id.text_schedule_title)
    TextView textScheduleTitle;
    @BindView(R.id.rv_schedule_detail)
    RecyclerView rvScheduleDetail;
    @BindView(R.id.text_no_schedule)
    TextView textNoSchedule;
    private SchedulePresenter schedulePresenter;

    @BindView(R.id.rootSchedule_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rootSchedule_content)
    ScrollView content;

    private boolean isInit = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_schedule, container, false);
        ButterKnife.bind(this, view);

        configureRvScheduleDetail();

        calendarView.setOnDateChangedListener(this);
        schedulePresenter = new SchedulePresenter(getActivity(), RootScheduleFragment.this, RootScheduleFragment.this);
        return view;
    }

    private void configureRvScheduleDetail(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvScheduleDetail.setLayoutManager(layoutManager);
        rvScheduleDetail.setNestedScrollingEnabled(false);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
    }

    @Override
    public void dismissLoading() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNoInternetError() {
        //TODO: Implement No Internet Error message
    }

    @Override
    public void showScheduleOnCalendar(List<CalendarDay> dates) {
        calendarView.addDecorator(new EventDecorator(Color.RED, dates));
        schedulePresenter.getScheduleForSelectedDate(System.currentTimeMillis());
    }

    @Override
    public void showScheduleForSelectedDate(List<DetailScheduleModel> detailScheduleList) {
        textNoSchedule.setVisibility(View.GONE);
        rvScheduleDetail.setVisibility(View.VISIBLE);

        ScheduleAdapter adapter = new ScheduleAdapter(detailScheduleList, this);
        rvScheduleDetail.setAdapter(adapter);
    }

    @Override
    public void showNoScheduleForSelectedDate() {
        textNoSchedule.setVisibility(View.VISIBLE);
        rvScheduleDetail.setVisibility(View.GONE);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        schedulePresenter.getScheduleForSelectedDate(date.getDate().getTime());
    }

    @Override
    public void onFragmentOpenedByUser() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {

                //When Fragment is attached to its Activity, start to query Backend
                if (isAdded()) {
                    if (isInit) return;
                    schedulePresenter.getUserOverallSchedule();
                    calendarView.setSelectedDate(Calendar.getInstance());
                    isInit = true;
                }

                //If Fragment is not yet attached, retry in 200 ms
                else {
                    handler.postDelayed(this, 200);
                }
            }
        };

        handler.post(r);
    }

    @Override
    public void refreshFragment() {
        //TODO: implement refresh fragment jika dibutuhkan
    }

    @Override
    public void onClick(DetailScheduleModel model) {
        Intent i = new Intent(getActivity(), HistoryDetailActivity.class);
        i.putExtra(HistoryDetailActivity.EXTRA_BOOKING_ID, model.getId());
        showActivity(i);
    }
}
