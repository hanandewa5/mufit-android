package com.nostratech.mufit.consumer.my_voucher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.MyVoucherModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyVoucherAdapter extends RecyclerView.Adapter<MyVoucherAdapter.MyVoucherViewHolder> {

    private List<MyVoucherModel> myVoucherModels = new ArrayList<>();

    private int selectedItemIndex = RecyclerView.NO_POSITION;

    @NonNull
    @Override
    public MyVoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_voucher_owned,
                parent, false);
        return new MyVoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyVoucherViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        MyVoucherModel model = myVoucherModels.get(position);

        holder.voucherDetailTitle.setText(model.getSpecialityName() +" by "+ model.getTrainerName());
        holder.voucherDetailName.setText(model.getPackageName());

        String currentQuantity = String.valueOf(model.getCurrentQuantity());
        String quantity = String.valueOf(model.getQuantity());

        //TODO: Check API if it is possible to receive null in current_quantity and quantity
        if (currentQuantity == null && quantity == null){
            holder.voucherDetailUseFor.setText(context.getResources().getString(R.string.single_voucher));
        } else {
            String msg = context.getString(R.string.available, model.getCurrentQuantity(), model.getQuantity());
            holder.voucherDetailUseFor.setText(msg);
        }

        long date = model.getEndDate();

        DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        String dateFormatted = dateFormat.format(date);
        holder.voucherDetailValidDate.setText(context.getString(R.string.until, dateFormatted));

        RadioButton radioButton = holder.radioButtonVoucherSelect;
        radioButton.setChecked(selectedItemIndex == holder.getAdapterPosition());

        radioButton.setOnClickListener(v -> {

            selectedItemIndex = holder.getAdapterPosition();
            notifyDataSetChanged();
        });
    }

    public MyVoucherModel getItem(int position){
        return myVoucherModels.get(position);
    }

    public int getSelectedItemIndex(){
        return selectedItemIndex;
    }

    public void clearList(){
        myVoucherModels.clear();
        notifyDataSetChanged();
    }

    public void insertItem(List<MyVoucherModel> models){
        this.myVoucherModels.addAll(models);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return myVoucherModels.size();
    }

    static class MyVoucherViewHolder extends RecyclerView.ViewHolder {
        TextView voucherDetailName, voucherDetailTitle, voucherDetailUseFor, voucherDetailValidDate;
        RadioButton radioButtonVoucherSelect;
        ViewGroup layoutVoucherDetailList;

        MyVoucherViewHolder(View itemView) {
            super(itemView);
            layoutVoucherDetailList = itemView.findViewById(R.id.layout_voucher_detail);
            voucherDetailName = itemView.findViewById(R.id.text_voucher_detail_name);
            voucherDetailTitle = itemView.findViewById(R.id.text_voucher_detail_title);
            voucherDetailUseFor = itemView.findViewById(R.id.text_voucher_detail_use_for);
            voucherDetailValidDate = itemView.findViewById(R.id.text_voucher_detail_valid_date);
            radioButtonVoucherSelect = itemView.findViewById(R.id.radioButton_voucher_select);
        }
    }
}
