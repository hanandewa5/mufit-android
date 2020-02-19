package com.nostratech.mufit.consumer.news_promo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.NewsModel;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


//TODO: Refactor to allow insertion of data at any time and remove data from constructor
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsModel> data;
    private OnNewsClickListener listener;

    private boolean loading = false;
    private static final int ITEM_LOADING = -1;
    private static final int ITEM_NEWS = 1;

    NewsAdapter(List<NewsModel> data, OnNewsClickListener listener) {
        this.data = data;
        this.listener = listener;

        //Used for Load More text
        data.add(null);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? ITEM_LOADING : ITEM_NEWS;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_LOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_load_more, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news_promo, parent, false);
            return new NewsHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {

        if(holder.getItemViewType() == ITEM_LOADING) {

            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            //0 is index for Progress indicator
            //1 is index for Load More text
            loadingHolder.flipper.setDisplayedChild(loading ? 0 : 1);
            loadingHolder.textLoadMore.setOnClickListener(l -> {
                loading = true;
                notifyItemChanged(data.size() - 1);
                listener.onNewsLoadMore();
            });

        } else {

            final NewsModel model = data.get(i);

            final NewsHolder newsHolder = (NewsHolder) holder;

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

    }

    public void removeLoadMore(){
        int pos = data.size() - 1;
        data.remove(pos);
        notifyItemRemoved(pos);
    }

    public void insertNewData(List<NewsModel> newNews){
        this.loading = false;
        //Insert before Load More position
        int pos = data.size() - 1;
        data.addAll(pos, newNews);
        notifyItemRangeChanged(pos, newNews.size());
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

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.viewFlipper)
        ViewFlipper flipper;
        @BindView(R.id.text_loadMore)
        TextView textLoadMore;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnNewsClickListener {
        void onNewsClick(NewsModel model);
        void onNewsLoadMore();
    }

}
