package com.nostratech.mufit.consumer.search_trainer;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.SearchTrainerModel;
import com.nostratech.mufit.consumer.utils.CurrencyFormatter;
import com.nostratech.mufit.consumer.utils.CustomTypefaceSpan;
import com.nostratech.mufit.consumer.utils.ProgressViewHolder;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.mufit.core.utils.TypefaceHelper;

/**
 * Created by Ahmadan Ditiananda on 5/18/2018.
 */

public class SearchTrainerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchTrainerModel> trainerModels = new ArrayList<>();

    private final TrainerHolderOnClickListener listener;
    private final CurrencyFormatter currencyFormatter;

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 0;

    SearchTrainerAdapter(TrainerHolderOnClickListener listener){
        this.listener = listener;
        this.currencyFormatter = new CurrencyFormatter();
    }

    @Override
    public int getItemViewType(int position) {
        return trainerModels.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    void appendList(List<SearchTrainerModel> listModels) {
        this.trainerModels.addAll(listModels);
        notifyDataSetChanged();
    }

    void startLoading(){
        this.trainerModels.add(null);
        notifyItemInserted(this.trainerModels.size() - 1);
    }

    void stopLoading(){
        this.trainerModels.remove(this.trainerModels.size() - 1);
        notifyItemRemoved(trainerModels.size());
    }

    public void clearList() {
        this.trainerModels = new ArrayList<>();
        notifyDataSetChanged();
    }

    public void updateList(List<SearchTrainerModel> listModels) {
        this.trainerModels = new ArrayList<>();
        this.trainerModels.addAll(listModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM ){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_trainer, parent, false);
            return new SearchTrainerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_progress_bar, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        viewHolder.setIsRecyclable(false);

        if(viewHolder.getItemViewType() == VIEW_TYPE_LOADING) return;

        final Context context = viewHolder.itemView.getContext();
        final SearchTrainerModel model = trainerModels.get(position);
        final SearchTrainerViewHolder holder = (SearchTrainerViewHolder) viewHolder;

        holder.textTrainerName.setText(model.getName());

        String specialityAvailable = context.getString(R.string.list_trainer_speciality_desc, model.getSpeciality());
        SpannableStringBuilder sb = new SpannableStringBuilder(specialityAvailable);
        TypefaceSpan truenoLightSpan = new CustomTypefaceSpan("", TypefaceHelper.get(TypefaceHelper.TRUENO_LIGHT, context));
        TypefaceSpan truenoRegularSpan = new CustomTypefaceSpan("", TypefaceHelper.get(TypefaceHelper.TRUENO_REGULAR, context));

        //Sets text before ":" as trueno light
        sb.setSpan(truenoLightSpan, 0, specialityAvailable.indexOf(":"), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //Sets text after ":" as trueno regular
        sb.setSpan(truenoRegularSpan, specialityAvailable.indexOf(":"), specialityAvailable.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        holder.textSpecialityAvailable.setText(sb);

        String price = String.format("Rp %s ++", currencyFormatter.getRupiahString(model.getPrice()));
        holder.textPriceValue.setText(price);
        
        holder.btnBookPackage.setEnabled(model.isHasPackage());

        if(model.getRating() != null) {
            holder.textTrainerRating.setText(trainerModels.get(position).getRating().toString());
        }
        else
            {
            holder.ratingStar.setVisibility(View.GONE);
            holder.textTrainerRating.setVisibility(View.GONE);
        }

        //Load trainer header image
        String trainerHeaderUrl = trainerModels.get(position).getCoverPicTrainer();
        if(trainerHeaderUrl != null && !trainerHeaderUrl.isEmpty()){
            GlideApp.with(context)
                    .load(trainerHeaderUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .placeholder(R.color.image_preview_bg_color)
                    .error(R.color.image_preview_bg_color)
                    .centerCrop()
//                .transform(new PositionedCropTransformation(1, 0))
                    .into(holder.headerTrainerImage);
        }

        //Load trainer profile image
        String imageTrainerUrl = trainerModels.get(position).getUrlPhotoTrainer();
        if(imageTrainerUrl != null && !imageTrainerUrl.isEmpty()){
            GlideApp.with(context)
                    .load(imageTrainerUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .placeholder(R.color.image_preview_bg_color)
                    .error(R.color.image_preview_bg_color)
                    .centerCrop()
//                .transform(new PositionedCropTransformation(1, 0))
                    .into(holder.imageTrainerProfile);
        }

        String trainerIdForIntent = trainerModels.get(position).getId();

        holder.btnBookSession.setOnClickListener(v -> listener.openDetailTrainer(trainerIdForIntent));
        holder.layoutTrainer.setOnClickListener(v -> listener.openDetailTrainer(trainerIdForIntent));

        holder.btnBookPackage.setOnClickListener(v -> listener.onPackageClicked(model));
        
    }

    @Override
    public int getItemCount() {
        return trainerModels.size();
    }

    static class SearchTrainerViewHolder extends RecyclerView.ViewHolder {
        TextView textTrainerName, textSpecialityAvailable, textPriceValue, textPriceLabel, textTrainerRating;
        Button btnBookSession;
        Button btnBookPackage;
        CircleImageView imageTrainerProfile;
        ImageView headerTrainerImage;
        ImageView ratingStar;
        RelativeLayout layoutTrainer;

        SearchTrainerViewHolder(View itemView) {
            super(itemView);
            layoutTrainer = itemView.findViewById(R.id.searchTrainerAdapter_root);
            ratingStar = itemView.findViewById(R.id.searchTrainerAdapter_imgRatingStar);
            textTrainerName = itemView.findViewById(R.id.searchTrainerAdapter_textTrainerName);
            textSpecialityAvailable = itemView.findViewById(R.id.searchTrainerAdapter_textSpecialityAvailable);
            btnBookSession = itemView.findViewById(R.id.searchTrainerAdapter_btnBookSession);
            btnBookPackage = itemView.findViewById(R.id.searchTrainerAdapter_btnBookPackage);
            textPriceValue = itemView.findViewById(R.id.searchTrainerAdapter_textPrice);
            textPriceLabel = itemView.findViewById(R.id.searchTrainerAdapter_labelPrice);
            textTrainerRating = itemView.findViewById(R.id.searchTrainerAdapter_textRating);
            headerTrainerImage = itemView.findViewById(R.id.searchTrainer_imgHeader);
            imageTrainerProfile = itemView.findViewById(R.id.searchTrainerAdapter_imgTrainerProfile);
        }
    }


    interface TrainerHolderOnClickListener {
        void openDetailTrainer(String trainerId);
        void onPackageClicked(SearchTrainerModel model);
    }
}
