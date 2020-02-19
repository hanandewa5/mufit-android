package com.nostratech.mufit.consumer.detailtrainer.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.detail_shift.TrainerSpeciality;
import com.nostratech.mufit.consumer.utils.CurrencyFormatter;

import java.util.List;

class ScheduleSpecialityAdapter extends RecyclerView.Adapter<ScheduleSpecialityAdapter.SpecialityViewHolder> {

    private List<TrainerSpeciality> specialityTrainerList;

    private int selectedItemPos = RecyclerView.NO_POSITION;

    private boolean hideCheckboxes;

    private OnStateChangeListener listener;

    private CurrencyFormatter currencyFormatter;

    ScheduleSpecialityAdapter(List<TrainerSpeciality> specialityTrainerList,
                              OnStateChangeListener listener,
                              boolean hideCheckboxes){
        this.specialityTrainerList = specialityTrainerList;
        this.listener = listener;
        this.hideCheckboxes = hideCheckboxes;
        this.currencyFormatter = new CurrencyFormatter();
    }

    @NonNull
    @Override
    public SpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_speciality_trainer,
                parent, false);
        return new SpecialityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityViewHolder holder, final int position) {
        Context context = holder.itemView.getContext();
        final TrainerSpeciality model = specialityTrainerList.get(position);

        if(hideCheckboxes) holder.checkboxSpeciality.setVisibility(View.GONE);

        String specialityName = model.getName();
        String specialityEquipment = model.getEquipment();
        Integer specialityCapacity = model.getMaxPeople();

        //It should not be null. It it is, then something went wrong in the backend / DB
        if(specialityName == null) specialityName = "(Tidak ditemukan)";
        holder.textName.setText(specialityName);

        if(specialityEquipment == null) specialityEquipment = context.getString(R.string.detail_trainer_speciality_no_equipment);
        holder.textEquipment.setText(context.getString(R.string.detail_trainer_speciality_equipment, specialityEquipment));

        if(specialityCapacity == null )
            holder.textCapacity.setText(context.getString(R.string.detail_trainer_speciality_max_people, context.getString(R.string.detail_trainer_speciality_no_equipment)));
        else
            holder.textCapacity.setText(context.getString(R.string.detail_trainer_speciality_max_people, specialityCapacity.toString()));

        String price = "Rp " + currencyFormatter.getRupiahString(model.getPrice());
        holder.textPrice.setText(price);

        //TODO: improve this logic
        holder.checkboxSpeciality.setChecked(position == selectedItemPos);
        holder.checkboxSpeciality.setOnClickListener(v -> {

            if (holder.checkboxSpeciality.isChecked()){
                selectedItemPos = position;
            } else {
                selectedItemPos = RecyclerView.NO_POSITION;
            }
            listener.onPackageSelected(getSelectedTrainerSpecialityId());
            listener.onStateChanged();
            notifyDataSetChanged();

        });
    }

    @Override
    public int getItemCount() {
        return specialityTrainerList.size();
    }

    class SpecialityViewHolder extends RecyclerView.ViewHolder {
        TextView textName, textEquipment, textCapacity, textPrice;
        CheckBox checkboxSpeciality;
        ConstraintLayout constraintLayout;

        public SpecialityViewHolder(View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.layout_speciality_trainer);
            checkboxSpeciality = itemView.findViewById(R.id.checkbox_speciality_trainer);
            textName = itemView.findViewById(R.id.text_speciality_name);
            textEquipment = itemView.findViewById(R.id.text_speciality_equipment);
            textCapacity = itemView.findViewById(R.id.text_speciality_capacity);
            textPrice = itemView.findViewById(R.id.text_speciality_price);
        }
    }

    String getPriceSelectedSpeciality() {
        if(selectedItemPos < 0) return null;
        return String.valueOf(specialityTrainerList.get(selectedItemPos).getPrice());
    }

    String getSelectedTrainerSpecialityId() {
        if(selectedItemPos < 0) return null;
        return specialityTrainerList.get(selectedItemPos).getId();
    }

    String getClassNameSelectedSpeciality() {
        if(selectedItemPos < 0) return null;
        return specialityTrainerList.get(selectedItemPos).getName();
    }

    interface OnStateChangeListener{
        void onStateChanged();
        void onPackageSelected(String packageId);
    }
}
