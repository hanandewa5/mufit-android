package com.nostratech.mufit.consumer.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.detailtrainer.DetailTrainerActivity;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.model.home.HomeTrainerListModel;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.ArrayList;
import java.util.List;

import id.mufit.core.data.AppCache;

//TODO: Move logic to presenter
public class HomeTrainerListAdapter extends RecyclerView.Adapter<HomeTrainerListAdapter.ViewHolder> {

    private Context context;
    private ImageView iv;
    private RequestOptions requestOptions = new RequestOptions();
    private List<HomeTrainerListModel> homeTrainerListModels = new ArrayList<>();

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;
    private AppCache appCache;

    public HomeTrainerListAdapter(AppCache appCache) {
        this.appCache = appCache;
    }

    @Override
    public int getItemViewType(int position) {
        return (position == homeTrainerListModels.size() -1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @NonNull
    @Override
    public HomeTrainerListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        ViewHolder viewHolder = null;
        View view;
        context = parent.getContext();

        switch (viewType) {
            case ITEM :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_trainer_list,
                        parent,false);
                //TODO : fristy - parse image view for upd image trainer on home
                //iv = view.findViewById(R.id.image_homeTrainer);
                return new ViewHolder(view);
            case LOADING :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_footer, parent, false);
                return new LoadingVH(view);
                default:
                    return null;
        }

//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_trainer_list,
//                parent, false);
//        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeTrainerListAdapter.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case ITEM :

            holder.trainerName.setText(homeTrainerListModels.get(position).getName());

            GlideApp.with(context)
                    .load(homeTrainerListModels.get(position).getUrlPhotoTrainer())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.color.preload_image_gray)
                    .centerCrop()
//                    .transform(new PositionedCropTransformation(1, 0))
                    .into(holder.trainerPhoto);

            holder.layout.setOnClickListener(view -> {

                String trainerId = homeTrainerListModels.get(position).getId();

                if(appCache.isUserLoggedIn()){

                    //Start DetailTrainerActivity
                    Bundle data = new Bundle();
                    data.putString(Constants.IntentExtras.TRAINER_ID,trainerId);
                    Intent intent = new Intent(context, DetailTrainerActivity.class);
                    intent.putExtras(data);
                    context.startActivity(intent);
                } else {

                    //Open Login Activity and set appropriate flags
                    Intent i = new Intent(context, LoginActivity.class);
                    i.putExtra(LoginActivity.FLAG, LoginActivity.OPEN_DETAIL_TRAINER);
                    i.putExtra(Constants.IntentExtras.TRAINER_ID, trainerId);
                    context.startActivity(i);
                }

//                if (!appCache.checkLoginFromDetailTrainer(0, homeTrainerListModels.get(position).getId())) {
//                    String trainerId = homeTrainerListModels.get(position).getId();
//
//                    //Start DetailTrainerActivity
//                    Bundle data = new Bundle();
//                    data.putString(Constants.INTENT_EXTRA_TRAINER_ID,trainerId);
//                    Intent intent = new Intent(context, DetailTrainerActivity.class);
//                    intent.putExtras(data);
//                    context.startActivity(intent);
//
//                }

            });

            break;
            case LOADING :
                break;
        }
    }

    @Override
    public int getItemCount() {
        return homeTrainerListModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView trainerName;
        ViewGroup layout;
        ImageView trainerPhoto;

        private ViewHolder(View itemView) {
            super(itemView);

            layout = itemView.findViewById(R.id.layout_homeTrainer);
            trainerName = itemView.findViewById(R.id.text_trainerNameHome);
            trainerPhoto = itemView.findViewById(R.id.image_homeTrainer);
        }
    }

    class LoadingVH extends ViewHolder {

        ProgressBar progressBar;

        public LoadingVH(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar_Paging);
        }
    }

    //TODO : update adapter menggunakan list dari rest API. biasakan gunakan notifydatasetchanged
    public void updateList(List<HomeTrainerListModel> listModels) {
        this.homeTrainerListModels.addAll(listModels);
        notifyDataSetChanged();
    }

    //TODO : clear list ketika refresh
    public void clearList() {
        this.homeTrainerListModels = new ArrayList<>();
        notifyDataSetChanged();
    }

//    interface OnClickListener{
//        void onTrainerPicClick(String trainerId);
//    }
}
