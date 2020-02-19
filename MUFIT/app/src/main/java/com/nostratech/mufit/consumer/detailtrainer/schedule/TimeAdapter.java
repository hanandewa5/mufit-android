package com.nostratech.mufit.consumer.detailtrainer.schedule;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.detail_shift.Shift;
import com.nostratech.mufit.consumer.utils.sort.TimeAscending;

import java.util.Collections;
import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {

    private List<Shift> shiftList;

    private OnStateChangeListener listener;

    private int selectedItemPos = RecyclerView.NO_POSITION;

    public TimeAdapter(List<Shift> shiftList, OnStateChangeListener listener){
        this.shiftList = shiftList;
        this.listener = listener;
        Collections.sort(shiftList, new TimeAscending());
    }

    @NonNull
    @Override
    public TimeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_schedule_trainer,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAdapter.ViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        final Shift shift = shiftList.get(position);

        String schedule = shift.getPrettyShiftTime();

        holder.radioButtonScheduleTime.setText(schedule);
        holder.radioButtonScheduleTime.setChecked(selectedItemPos == holder.getAdapterPosition());

        //Checks if schedule is available
        if (shift.getStatus()) {
            //If shift is available, allow user interaction
            holder.radioButtonScheduleTime.setEnabled(true);
            holder.radioButtonScheduleTime.setAlpha(1.0f);
        } else {
            //Otherwise, disable user interaction and;
            //set opacity to 40%
            holder.radioButtonScheduleTime.setEnabled(false);
            holder.radioButtonScheduleTime.setAlpha(0.4f);
        }

        holder.radioButtonScheduleTime.setOnClickListener(v -> {
            selectedItemPos = position;
            listener.onStateChanged();
            notifyDataSetChanged();
        });
    }



    @Override
    public int getItemCount() {
        return shiftList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        //TextView txtTime; //todo: fristy - comment for now maybe use again on another day
        RadioButton radioButtonScheduleTime;
        RadioGroup radioGroupScheduleTime;

        ViewHolder(View itemView) {
            super(itemView);
            //txtTime = itemView.findViewById(R.id.text_time);//todo: fristy - comment for now maybe use again on another day
            radioGroupScheduleTime = itemView.findViewById(R.id.radioGroup_schedule_time);
            radioButtonScheduleTime = itemView.findViewById(R.id.radioButton_schedule_time);
        }
    }

    public String getIdSelectedSchedule()
    {
        if(selectedItemPos < 0) return null;
        return shiftList.get(selectedItemPos).getId();
    }

    public String getTextSelectedSchedule()
    {
        if(selectedItemPos < 0) return null;
        return shiftList.get(selectedItemPos).getPrettyShiftTime();
    }

    interface OnStateChangeListener{
        void onStateChanged();
    }

}