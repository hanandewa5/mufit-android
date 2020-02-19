package com.nostratech.mufit.consumer.home;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.category.CategoryResponseModel;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeSubCategoryAdapter extends RecyclerView.Adapter<HomeSubCategoryAdapter.CategoryViewHolder> {

    //TODO: make model for subcategory
    private List<CategoryResponseModel> data;
    private OnCategoryClickedListener listener;

    HomeSubCategoryAdapter(List<CategoryResponseModel> data) {
        this.data = data;
    }

    public void setListener(OnCategoryClickedListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_home_subcategories,
                parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int i) {
        final CategoryResponseModel categoryResponseModel = data.get(i);

        GlideApp.with(categoryViewHolder.itemView)
                .load(categoryResponseModel.getCategoryImageUrl())
                .placeholder(R.drawable.glide_image_placeholder)
                .centerCrop()
                .into(categoryViewHolder.image);

        categoryViewHolder.image.setOnClickListener(v -> listener.onCategoryCardClicked(categoryResponseModel));

        categoryViewHolder.title.setText(categoryResponseModel.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.subcategories_image)
        ImageView image;
        @BindView(R.id.subcategories_text)
        TextView title;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnCategoryClickedListener{
        void onCategoryCardClicked(CategoryResponseModel model);
    }
}
