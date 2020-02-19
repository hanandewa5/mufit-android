package com.nostratech.mufit.consumer.schedule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.vipulasri.timelineview.TimelineView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.schedule.BookingShiftList;
import com.nostratech.mufit.consumer.model.schedule.DetailScheduleModel;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.SessionDetailsViewHolder> {

    private List<DetailScheduleModel> data;
    private OnSessionClickListener listener;

    ScheduleAdapter(List<DetailScheduleModel> data, OnSessionClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @NonNull
    @Override
    public ScheduleAdapter.SessionDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_schedule,
                parent, false);
        return new SessionDetailsViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.SessionDetailsViewHolder holder, final int position) {
        DetailScheduleModel model = data.get(position);

        String title = model.getBookingSpecialty() + " with " + model.getTrainerName();
        holder.textTitle.setText(title);

        BookingShiftList shiftList = model.getBookingShiftList().get(0);
        String sessionTime = getSessionTime(shiftList);
        holder.textTime.setText(sessionTime);


        //TODO: The current model does not provide an address. Can be enabled when it is supported
        holder.textAddress.setVisibility(View.GONE);

        holder.parent.setOnClickListener((v) -> listener.onClick(model));
    }

    /**
     * Start time and end time are in the form of "HH:mm:ss"
     *
     * @param model
     * @return
     */
    private String getSessionTime(BookingShiftList model) {
        String startTime = model.getStartTime();
        String endTime = model.getEndTime();

        //convert into "HH:mm"
        return startTime.substring(0, startTime.length() - 3)
                + " - "
                + endTime.substring(0, endTime.length() - 3);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class SessionDetailsViewHolder extends RecyclerView.ViewHolder {
        ViewGroup parent;
        TimelineView timelineView;
        TextView textTitle;
        TextView textTime;
        TextView textAddress;

        public SessionDetailsViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            parent = itemView.findViewById(R.id.layout);
            timelineView = itemView.findViewById(R.id.timeline);
            timelineView.initLine(viewType);
            textTitle = itemView.findViewById(R.id.session_title);
            textTime = itemView.findViewById(R.id.session_time);
            textAddress = itemView.findViewById(R.id.session_address);
        }
    }

    interface OnSessionClickListener {
        void onClick(DetailScheduleModel model);
    }
}
