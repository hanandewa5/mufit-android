package com.nostratech.mufit.consumer.utils;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.nostratech.mufit.consumer.R;

public class ProgressViewHolder extends RecyclerView.ViewHolder {

    private ProgressBar progressBar;

    public ProgressViewHolder(@NonNull View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}