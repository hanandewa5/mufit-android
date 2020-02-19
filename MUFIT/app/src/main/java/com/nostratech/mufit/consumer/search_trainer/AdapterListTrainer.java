package com.nostratech.mufit.consumer.search_trainer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.detailtrainer.DetailTrainerActivity;
import com.nostratech.mufit.consumer.model.ListTrainerModel;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;
import com.nostratech.mufit.consumer.utils.glidehelper.PositionedCropTransformation;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListTrainer extends RecyclerView.Adapter<AdapterListTrainer.ViewHolder>{
    private SearchTrainerActivity searchTrainerActivity;
    List<ListTrainerModel> listTrainerModels;
    ArrayList<ListTrainerModel> listTemp;

    private Context context;
    private RequestOptions requestOptions = new RequestOptions();

    private Typeface tfLight;
    private Typeface tfLightItalic;
    private Typeface tfRegular;
    private Typeface tfRegularItalic;
    private Typeface tfMedium;
    private Typeface tfMediumItalic;
    private Typeface tfBold;
    private Typeface tfBoldItalic;


    public AdapterListTrainer(SearchTrainerActivity searchTrainerActivity,
                              List<ListTrainerModel> listTrainerModels){
        this.searchTrainerActivity = searchTrainerActivity;
        this.listTrainerModels = listTrainerModels;
        this.listTemp = new ArrayList<>();
        this.listTemp.addAll(listTrainerModels);
    }

    @NonNull
    @Override
    public AdapterListTrainer.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_trainer,
                parent, false);
        context = parent.getContext();
        initTypefaces(context);
        return new AdapterListTrainer.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListTrainer.ViewHolder holder, final int position) {
        holder.Name.setTypeface((getTfMedium()));
        holder.Specialities.setTypeface((getTfRegular()));
        holder.ButtonBooking.setTypeface((getTfMedium()));
        holder.Price.setTypeface((getTfRegular()));
        holder.PriceDesc.setTypeface((getTfRegular()));
        holder.Rating.setTypeface((getTfMedium()));

        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getInstance(localeID);

        holder.Name.setText(listTrainerModels.get(position).getName());
        holder.Specialities.setText(listTrainerModels.get(position).getSpeciality());
        holder.ratingStar.setVisibility(View.GONE);
        holder.Rating.setVisibility(View.GONE);

        GlideApp.with(context).load(listTrainerModels.get(position).getUrlPhotoTrainer())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE).skipMemoryCache(true)
                .transform(new PositionedCropTransformation(1, 0))
                .placeholder(R.drawable.mufit_logo_white)
                .into(holder.ImageTrainer);

        holder.ButtonBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTrainerActivity.class);
                intent.putExtra("id", listTrainerModels.get(position).getId());
                context.startActivity(intent);
            }
        });

        holder.layoutTrainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailTrainerActivity.class);
                intent.putExtra("id", listTrainerModels.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTrainerModels.size();
    }

    public void refresh(List<ListTrainerModel> trainerModels) {
        listTrainerModels = new ArrayList<>();
        listTrainerModels.addAll(trainerModels);
        notifyDataSetChanged();
    }

    public boolean filter(String charText) {
        boolean isEmpty = true;
        charText = charText.toLowerCase(Locale.getDefault());
        listTrainerModels.clear();
        if (charText.length() == 0) {
            listTrainerModels.addAll(listTemp);
        }
        else {
            for (ListTrainerModel ld : listTemp) {
                if (ld.getName().toLowerCase(Locale.getDefault()).contains(charText) ||
                        ld.getSpeciality().toLowerCase(Locale.getDefault()).contains(charText)) {
                    listTrainerModels.add(ld);
                }
            }
            if(listTrainerModels.isEmpty()){
                isEmpty = false;
            }
            else{
                isEmpty = true;
            }
        }
        notifyDataSetChanged();
        return isEmpty;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView Name, Specialities, Price, PriceDesc, Rating;
        Button ButtonBooking;
        CircleImageView ImageTrainer;
        ImageView headerTrainerImage;
        LinearLayout layoutTrainer;
        ImageView ratingStar;

        public ViewHolder(View itemView) {
            super(itemView);
            ratingStar = itemView.findViewById(R.id.searchTrainerAdapter_imgRatingStar);
            layoutTrainer = itemView.findViewById(R.id.searchTrainerAdapter_root);
            Name = itemView.findViewById(R.id.searchTrainerAdapter_textTrainerName);
            Specialities = itemView.findViewById(R.id.searchTrainerAdapter_textSpecialityAvailable);
            ButtonBooking = itemView.findViewById(R.id.searchTrainerAdapter_btnBookSession);
            Price = itemView.findViewById(R.id.searchTrainerAdapter_textPrice);
            PriceDesc = itemView.findViewById(R.id.searchTrainerAdapter_labelPrice);
            Rating = itemView.findViewById(R.id.searchTrainerAdapter_textRating);
//            imageTrainerProfile = itemView.findViewById(R.id.image_Trainer);
            headerTrainerImage = itemView.findViewById(R.id.searchTrainer_imgHeader);
        }
    }

    private void initTypefaces(Context context){
        tfLight = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_light.otf");
        tfLightItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_light_italic.otf");
        tfRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_regular.otf");
        tfRegularItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_regular_italic.otf");
        tfMedium = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_medium.otf");
        tfMediumItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_medium_italic.otf");
        tfBold = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_bold.otf");
        tfBoldItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_bold_italic.otf");
    }

    public Typeface getTfLight() {
        return tfLight;
    }

    public void setTfLight(Typeface tfLight) {
        this.tfLight = tfLight;
    }

    public Typeface getTfLightItalic() {
        return tfLightItalic;
    }

    public void setTfLightItalic(Typeface tfLightItalic) {
        this.tfLightItalic = tfLightItalic;
    }

    public Typeface getTfRegular() {
        return tfRegular;
    }

    public void setTfRegular(Typeface tfRegular) {
        this.tfRegular = tfRegular;
    }

    public Typeface getTfRegularItalic() {
        return tfRegularItalic;
    }

    public void setTfRegularItalic(Typeface tfRegularItalic) {
        this.tfRegularItalic = tfRegularItalic;
    }

    public Typeface getTfMedium() {
        return tfMedium;
    }

    public void setTfMedium(Typeface tfMedium) {
        this.tfMedium = tfMedium;
    }

    public Typeface getTfMediumItalic() {
        return tfMediumItalic;
    }

    public void setTfMediumItalic(Typeface tfMediumItalic) {
        this.tfMediumItalic = tfMediumItalic;
    }

    public Typeface getTfBold() {
        return tfBold;
    }

    public void setTfBold(Typeface tfBold) {
        this.tfBold = tfBold;
    }

    public Typeface getTfBoldItalic() {
        return tfBoldItalic;
    }

    public void setTfBoldItalic(Typeface tfBoldItalic) {
        this.tfBoldItalic = tfBoldItalic;
    }
}
