package id.mufit.singleweekcalendar;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

public class WeekFragment extends Fragment implements View.OnClickListener {
    TextView sun;
    TextView mon;
    TextView tue;
    TextView wed;
    TextView thu;
    TextView fri;
    TextView sat;

    private Week week;
    private OnDateSelectedListener listener;

    public int getMonth(){
        return week.getMonth();
    }

    public Week getWeek(){
        return this.week;
    }

    static final String KEY_WEEK = "getWeek";

    public static WeekFragment newInstance(Week week, OnDateSelectedListener listener) {

        Bundle args = new Bundle();
        args.putSerializable(KEY_WEEK, week);

        WeekFragment fragment = new WeekFragment();
        fragment.setArguments(args);
        fragment.listener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        Bundle args = getArguments();

        initViews(view);

        week = (Week) args.getSerializable(KEY_WEEK);

        Objects.requireNonNull(args, "Week object is null; Please check that the fragment arguments are set properly");

        CalendarDay sun = week.getSun();
        CalendarDay mon = week.getMon();
        CalendarDay tue = week.getTue();
        CalendarDay wed = week.getWed();
        CalendarDay thu = week.getThu();
        CalendarDay fri = week.getFri();
        CalendarDay sat = week.getSat();

        if (sun != null) this.sun.setText(String.valueOf(sun.getDay()));
        if (mon != null) this.mon.setText(String.valueOf(mon.getDay()));
        if (tue != null) this.tue.setText(String.valueOf(tue.getDay()));
        if (wed != null) this.wed.setText(String.valueOf(wed.getDay()));
        if (thu != null) this.thu.setText(String.valueOf(thu.getDay()));
        if (fri != null) this.fri.setText(String.valueOf(fri.getDay()));
        if (sat != null) this.sat.setText(String.valueOf(sat.getDay()));

        return view;
    }

    private void initViews(View v) {
        sun = v.findViewById(R.id.sun);
        mon = v.findViewById(R.id.mon);
        tue = v.findViewById(R.id.tue);
        wed = v.findViewById(R.id.wed);
        thu = v.findViewById(R.id.thu);
        fri = v.findViewById(R.id.fri);
        sat = v.findViewById(R.id.sat);

        sun.setOnClickListener(this);
        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thu.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        CalendarDay clickedDay = null;

        int vId = v.getId();
        //Get the selected day's CalendarDay object

        Log.d("WeekFragment", "OncLick"  + vId);
        if(vId == R.id.sun){
            clickedDay = week.getSun();
        } else if (vId == R.id.mon){
            clickedDay = week.getMon();
        } else if (vId == R.id.tue){
            clickedDay = week.getTue();
        } else if (vId == R.id.wed){
            clickedDay = week.getWed();
        } else if (vId == R.id.thu) {
            clickedDay = week.getThu();
        } else if (vId == R.id.fri) {
            clickedDay = week.getFri();
        } else if (vId == R.id.sat){
            clickedDay = week.getSat();
        }

        //Prevent selection on empty date
        if(clickedDay == null) return;

        //Pass
        listener.onDateSelected(clickedDay, v);
        v.setBackgroundResource(R.drawable.yellow_circle_indicator);
    }

    public interface OnDateSelectedListener {
        void onDateSelected(CalendarDay day, View v);
    }
}
