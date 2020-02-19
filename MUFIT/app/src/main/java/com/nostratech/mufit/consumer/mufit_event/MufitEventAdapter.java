package com.nostratech.mufit.consumer.mufit_event;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.EventModel;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class MufitEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private OnEventClickListener listener;
    private List<EventModel> eventList = new ArrayList<>();

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 0;

    public MufitEventAdapter(MufitEventAdapter.OnEventClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_mufit_event, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_progress_bar, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(holder.getItemViewType() == VIEW_TYPE_LOADING) return;

        final EventModel event = eventList.get(position);

        final ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.setIsRecyclable(false);

        //set image
        GlideApp.with(holder.itemView.getContext())
                .load(event.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(viewHolder.imageMufitEvent);

        //set judul
        viewHolder.textEventName.setText(event.getName());

        //set deskrisi
        viewHolder.textMufitEvent.setText(event.getDesc());

        viewHolder.imageMufitEvent.setOnClickListener(v->listener.onClick(event.getId()));
    }

    @Override
    public int getItemViewType(int position) {
        return eventList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }


    public void updateList(List<EventModel> eventModels) {
        this.eventList.addAll(eventModels);
        notifyDataSetChanged();
    }

    public void startLoading(){
        this.eventList.add(null);
        notifyItemInserted(this.eventList.size() - 1);
    }

    public void stopLoading(){
        this.eventList.remove(this.eventList.size() - 1);
        notifyItemRemoved(eventList.size());
    }

    public void clearList() {
        this.eventList = new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public RoundedImageView imageMufitEvent;
        public TextView textMufitEvent;
        public TextView textEventName;

        public ViewHolder(View itemView) {
            super(itemView);

            imageMufitEvent = itemView.findViewById(R.id.image_mufit_event);
            textMufitEvent = itemView.findViewById(R.id.text_mufit_event);
            textEventName = itemView.findViewById(R.id.text_event_name);

        }
    }
    public interface OnEventClickListener {
        void onClick(String eventId);
    }
    static class ProgressViewHolder  extends RecyclerView.ViewHolder{

        ProgressBar progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
