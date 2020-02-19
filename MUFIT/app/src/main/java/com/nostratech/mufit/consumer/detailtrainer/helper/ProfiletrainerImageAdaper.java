package com.nostratech.mufit.consumer.detailtrainer.helper;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.detailtrainer.ImageFullscreenActivity;
import com.nostratech.mufit.consumer.model.TrainerImageModel;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;
import com.nostratech.mufit.consumer.utils.glidehelper.PositionedCropTransformation;

import java.util.ArrayList;
import java.util.List;

//TODO: Cleanup class
public class ProfiletrainerImageAdaper extends RecyclerView.Adapter<ProfiletrainerImageAdaper.ViewHolder> {

    List<TrainerImageModel> listTrainerImageModel = new ArrayList<>();
    private CheckBox selectedRadioButton;
    private Context context;

    @NonNull
    @Override
    public ProfiletrainerImageAdaper.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_profile_trainer_image,
                parent, false);
        context = parent.getContext();
        //initTypefaces(context);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfiletrainerImageAdaper.ViewHolder holder, final int position) {
        //Log.d("FRISTY LOG", "POSISI KE " + position);
        //TODO : fristy - download image detail trainer
        GlideApp.with(context).load(listTrainerImageModel.get(position).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new PositionedCropTransformation(1, 0))
                .into(holder.profileDetailImage);

        holder.profileDetailImage.setOnClickListener(v -> {

            final String image = listTrainerImageModel.get(position).getUrl();
            Intent i = new Intent(context.getApplicationContext(), ImageFullscreenActivity.class);
            i.putExtra("image", image);
            context.startActivity(i);

            //Create dialog
//            BaseMufitDialog dialog = new MufitDialogOneButton(
//                    holder.itemView.getContext(),
//                    "Preview",
//                    R.layout.dialog_image_preview,
//                    null
//            );
//
//            //Get reference to ImageView in the dialog above
//            ImageView imagePreview = dialog.getContent().findViewById(R.id.image_preview);
//
//            //Load image
//            GlideApp.with(holder.itemView.getContext())
//                    .load(listTrainerImageModel.get(position).getUrl())
//                    .fitCenter()
//                    .into(imagePreview);
        });
    }

    @Override
    public int getItemCount() {
        //Log.d("FRISTY LOG", "jumlah list nya " + myVoucherModels.size());
        return listTrainerImageModel.size();
    }

    public void refresh(List<TrainerImageModel> listTrainerImageModel) {
        this.listTrainerImageModel = new ArrayList<>();
        this.listTrainerImageModel.addAll(listTrainerImageModel);
        notifyDataSetChanged();
    }

    public void clearList() {
        //this.myVoucherAdvancedModel = new ArrayList<>();
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profileDetailImage;

        public ViewHolder(View itemView) {
            super(itemView);
            profileDetailImage = itemView.findViewById(R.id.image_profile_trainer_detail);
        }
    }
}
