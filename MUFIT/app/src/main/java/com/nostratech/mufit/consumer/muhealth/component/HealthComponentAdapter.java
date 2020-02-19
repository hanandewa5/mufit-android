package com.nostratech.mufit.consumer.muhealth.component;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HealthComponentAdapter extends RecyclerView.Adapter<HealthComponentAdapter.HealthComponentViewHolder> {

    private List<HealthComponentModel> data;
    private OnClickListener listener;
    private static DecimalFormat decimalFormat = new DecimalFormat("0.#");;

    public HealthComponentAdapter(OnClickListener listener){
        this.data = new ArrayList<>();
        this.listener = listener;
    }

    public void insertData(List<HealthComponentModel> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public HealthComponentModel getItem(int index){
        return data.get(index);
    }

    @NonNull
    @Override
    public HealthComponentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_muhealth_component, parent, false);
        return new HealthComponentViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HealthComponentViewHolder holder, int position) {
        final HealthComponentModel model = data.get(position);
        holder.bindViews(model);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class HealthComponentViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.muhealthContainer)
        ConstraintLayout btnContainer;
        @BindView(R.id.muhealthComponent_icon)
        ImageView icon;
        @BindView(R.id.muhealthComponent_textTitle)
        TextView textTitle;
        @BindView(R.id.muhealthComponent_textValue)
        TextView textValue;
        @BindView(R.id.muhealthComponent_iconChangeIndicator)
        ImageView iconChange;
        @BindView(R.id.muhealthComponent_textValueChange)
        TextView textValueChange;
        @BindView(R.id.muhealthComponent_btnShowDetail)
        ImageButton btnShowDetail;

        private OnClickListener listener;

        HealthComponentViewHolder(@NonNull View itemView, OnClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.listener = listener;
        }

        void bindViews(HealthComponentModel model){
            GlideApp.with(icon)
                    .load(model.getIconUrl())
                    .fitCenter()
                    .into(icon);

            textTitle.setText(model.getTitle());
            textValue.setText(decimalFormat.format(model.getValue()) + model.getUnit());
            switch(model.getChangeType()){
                case NEUTRAL:
                    iconChange.setImageResource(R.drawable.ic_neutral);
                    break;
                case INCREASE:
                    iconChange.setImageResource(R.drawable.ic_increase);
                    break;
                case DECREASE:
                    iconChange.setImageResource(R.drawable.ic_decrease);
                    break;
            }
            textValueChange.setText(decimalFormat.format(model.getChangeValue()) + model.getUnit());

            btnContainer.setOnClickListener(l -> listener.onShowDetailClick(model));
        }
    }

    public interface OnClickListener {
        void onShowDetailClick(HealthComponentModel model);
    }
}
