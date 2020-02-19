package id.mufit.singleweekcalendar;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalendarAdapter extends PagerAdapter {

    private static final String TAG = CalendarAdapter.class.getSimpleName();
    private final Context context;
    private List<Week> data = new ArrayList<>();

    private int textAppearanceResId = 0;

    private Map<CalendarDay, Boolean> mapOfIndicators = new HashMap<>();
    private OnDateSelectedListener listener;

    private CalendarDay selectedDay;
    private View selectedDayView;

    void setRedIndicators(List<CalendarDay> redIndicators) {
        for (CalendarDay day : redIndicators){
            mapOfIndicators.put(day, false);
        }
        notifyDataSetChanged();
    }

    void addGreenIndicators(List<CalendarDay> greenIndicators) {
        for (CalendarDay day : greenIndicators){
            mapOfIndicators.put(day, true);
        }
        notifyDataSetChanged();
    }

    void setTextAppearanceResId(int resId){
        this.textAppearanceResId = resId;
    }

    CalendarAdapter(Context context){
        this.context = context;
    }

    void setOnDateSelectedListener(OnDateSelectedListener listener){
        this.listener = listener;
    }

    void setData(List<Week> data){
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    Week getItem(int pos){
        return data.get(pos);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_week, collection, false);

        final Week week = data.get(position);

        for(int i= 0; i< layout.getChildCount(); i++){
            CalendarTextView tv = (CalendarTextView) layout.getChildAt(i);

            final CalendarDay currentDay = week.getDay(i);

            if(currentDay != null){
                tv.setTextAppearance(context, textAppearanceResId);
                tv.setTag(currentDay.toString());
                tv.setText(String.valueOf(currentDay.getDay()));
                tv.setOnClickListener( v-> performDaySelection(v, currentDay));
                Boolean bool = mapOfIndicators.get(currentDay);
                if(bool != null){
                    if(bool){
                        tv.drawIndicator(CalendarTextView.PAINT_GREEN);
                    } else {
                        tv.drawIndicator(CalendarTextView.PAINT_RED);
                    }
                }
            }
        }


        collection.addView(layout);
        return layout;
    }

    void performDaySelection(View v, CalendarDay currentDay){
        listener.onDateSelected(currentDay);

        v.setBackgroundResource(R.drawable.yellow_circle_indicator);

        Animation fadeOut = AnimationUtils.loadAnimation(v.getContext(), R.anim.fade_in);
        v.startAnimation(fadeOut);
        this.selectedDay = currentDay;
        if (selectedDayView != null && selectedDayView != v) {
            selectedDayView.setBackground(null);
        }
        this.selectedDayView = v;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    CalendarDay getSelectedDay() {
        return selectedDay;
    }


    public interface OnDateSelectedListener{
        void onDateSelected(CalendarDay day);
    }
}