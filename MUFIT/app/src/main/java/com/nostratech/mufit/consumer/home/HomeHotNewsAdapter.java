package com.nostratech.mufit.consumer.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeHotNewsAdapter extends RecyclerView.Adapter<HomeHotNewsAdapter.NewsHolder> {

    private List<NewsModel> data;
    private OnNewsClickListener listener;

    HomeHotNewsAdapter(List<NewsModel> data, OnNewsClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_hot_news,
                parent, false);
        return new NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {

        final NewsModel model = data.get(i);

        String imageUrl = model.getImageUrl();

        if (imageUrl != null && !imageUrl.isEmpty()) {
            GlideApp.with(newsHolder.itemView)
                    .load(imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .into(newsHolder.image);
        }

        newsHolder.title.setText(model.getTitle());
        newsHolder.description.setText(model.getDescription());
        newsHolder.root.setOnClickListener(v ->listener.onNewsClick(model));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class NewsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.root_layout)
        ViewGroup root;

        NewsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnNewsClickListener {
        void onNewsClick(NewsModel model);
    }
}
