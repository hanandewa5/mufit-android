package com.nostratech.mufit.consumer.detailtrainer.helper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.DetailTrainerModel;
import com.nostratech.mufit.consumer.model.TrainerCertificateModel;

import java.util.ArrayList;
import java.util.List;

//TODO: Cleanup class
public class ProfileTrainerDetailAdaper extends RecyclerView.Adapter<ProfileTrainerDetailAdaper.ViewHolder> {

    List<DetailTrainerModel> listDetailTrainerModel = new ArrayList<>();
    List<TrainerCertificateModel> listTrainerCertificateModel = new ArrayList<>();
    private CheckBox selectedRadioButton;

    @NonNull
    @Override
    public ProfileTrainerDetailAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_trainer_detail,
                parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileTrainerDetailAdaper.ViewHolder holder, final int position) {
        //Log.d("FRISTY LOG", "POSISI KE " + position);
        Integer indexPosisi = position+1;
        String indexStr = indexPosisi.toString();

        //Append 0 in front of single digit indices
        if(indexPosisi < 10) indexStr = "0" + indexStr;

        holder.profileTrainerDetailNunmber.setText(indexStr);
        holder.profileTrainerDetailName.setText(listTrainerCertificateModel.get(position).getName());
        holder.profileTrainerDetailDetail.setText(listTrainerCertificateModel.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        //Log.d("FRISTY LOG", "jumlah list nya listTrainerCertificateModel " + listTrainerCertificateModel.size());
        return listTrainerCertificateModel.size();
    }

    public void refresh(List<DetailTrainerModel> listDetailTrainerModel) {
        this.listDetailTrainerModel = new ArrayList<>();
        this.listDetailTrainerModel.addAll(listDetailTrainerModel);
        notifyDataSetChanged();
    }

    public void addListTrainerCertificateModel(List<TrainerCertificateModel> listTrainerCertificateModel)
    {
        this.listTrainerCertificateModel = new ArrayList<>();
        this.listTrainerCertificateModel.addAll(listTrainerCertificateModel);
        notifyDataSetChanged();
    }

    public void clearList() {
        //this.myVoucherAdvancedModel = new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView profileTrainerDetailNunmber,profileTrainerDetailName,profileTrainerDetailDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            profileTrainerDetailNunmber = itemView.findViewById(R.id.text_profile_trainer_detail_nunmber);
            profileTrainerDetailName = itemView.findViewById(R.id.text_profile_trainer_detail_name);
            profileTrainerDetailDetail = itemView.findViewById(R.id.text_profile_trainer_detail_detail);
        }
    }
}
