package com.nostratech.mufit.consumer.news_promo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.card.MaterialCardView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.PublicVoucherModel;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicVoucherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PublicVoucherModel> data;
    private OnVoucherClickListener listener;

    private boolean loading = false;
    private static final int ITEM_LOADING = -1;
    private static final int ITEM_VOUCHER = 1;

    PublicVoucherAdapter(List<PublicVoucherModel> data, OnVoucherClickListener listener) {
        this.data = data;
        this.listener = listener;

        //Used for Load More text
        data.add(null);
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position) == null ? ITEM_LOADING : ITEM_VOUCHER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == ITEM_LOADING){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_load_more, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_public_voucher, parent, false);
            return new VoucherViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder.getItemViewType() == ITEM_LOADING){

            LoadingViewHolder loadingHolder = (LoadingViewHolder) holder;
            //0 is index for Progress indicator
            //1 is index for Load More text
            loadingHolder.flipper.setDisplayedChild(loading ? 0 : 1);
            loadingHolder.textLoadMore.setOnClickListener(l -> {
                loading = true;
                notifyItemChanged(data.size() - 1);
                listener.onLoadMoreClick();
            });

        } else {
            final PublicVoucherModel model = data.get(position);

            VoucherViewHolder voucherHolder = (VoucherViewHolder) holder;

            GlideApp.with(voucherHolder.itemView.getContext())
                    .load(model.getUrlBanner())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .placeholder(R.color.preload_image_gray)
                    .error(R.color.preload_image_gray)
                    .into(voucherHolder.image);

            voucherHolder.voucherCode.setText(model.getCode());

            String voucherType = model.getType();
            switch(voucherType.toLowerCase()){
                case "nominal":
                    voucherHolder.discountValue.setText("Rp. " + model.getValue());
                    break;
                case "percent":
                    voucherHolder.discountValue.setText(model.getValue() + "%");
            }


            voucherHolder.card.setOnClickListener(v->
                    listener.onVoucherClick(model.getId(), model.getUrlBanner()));
        }

    }

    public void removeLoadMore(){
        int pos = data.size() - 1;
        data.remove(pos);
        notifyItemRemoved(pos);
    }

    public void insertNewData(List<PublicVoucherModel> newVouchers){
        this.loading = false;
        //Insert before Load More position
        int pos = data.size() - 1;
        data.addAll(pos, newVouchers);
        notifyItemRangeChanged(pos, newVouchers.size());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    static class VoucherViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card)
        MaterialCardView card;
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.value_voucherCode)
        TextView voucherCode;
        @BindView(R.id.value_discountValue)
        TextView discountValue;

        VoucherViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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


    interface OnVoucherClickListener{
        void onVoucherClick(String voucherId, String imageUrl);
        void onLoadMoreClick();
    }
}
