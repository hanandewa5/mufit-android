package com.nostratech.mufit.consumer.search_trainer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.home.HomeSpecialityListModel;
import com.nostratech.mufit.consumer.utils.FontUtils;

import java.util.ArrayList;
import java.util.List;

public class FilterSpecialityAdapter extends RecyclerView.Adapter<FilterSpecialityAdapter.ViewHolder> {

    private List<HomeSpecialityListModel> data;
    private int selectedItemIndex = RecyclerView.NO_POSITION;

    FilterSpecialityAdapter() {
        this.data = new ArrayList<>();
    }

    void insertData(List<HomeSpecialityListModel> newData) {
        data.addAll(newData);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FilterSpecialityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_trainer_speciality,
                parent, false);
        return new ViewHolder(view);
    }

    HomeSpecialityListModel getSelectedItem() {
        return selectedItemIndex != RecyclerView.NO_POSITION ? data.get(selectedItemIndex) : null;
    }

    void removeSelection() {
        selectedItemIndex = RecyclerView.NO_POSITION;
        notifyDataSetChanged();
    }

    /**
     * Call only after inserting data
     *
     * @param speciality - speciality to select
     */
    void selectSpeciality(String speciality) {
        for (int i = 0; i < data.size() - 1; i++) {
            HomeSpecialityListModel model = data.get(i);
            if (model.getName().equalsIgnoreCase(speciality)) {
                selectedItemIndex = i;
                notifyDataSetChanged();
                break;
            }
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final FilterSpecialityAdapter.ViewHolder holder, final int position) {

        final HomeSpecialityListModel model = data.get(position);

        holder.toggleButton.setTypeface(FontUtils.getTruenoRegular(holder.itemView.getContext()));
        holder.toggleButton.setText(model.getName());
        holder.toggleButton.setTextOn(model.getName());
        holder.toggleButton.setTextOff(model.getName());

        holder.toggleButton.setChecked(selectedItemIndex == position);
        holder.toggleButton.setOnClickListener(l -> {
            selectedItemIndex = position;
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ToggleButton toggleButton;

        ViewHolder(View itemView) {
            super(itemView);
            toggleButton = itemView.findViewById(R.id.toggleBtn_speciality);
        }
    }
}