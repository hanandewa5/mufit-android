package com.nostratech.mufit.consumer.utils.glidehelper;

import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class GlideRequestListener implements RequestListener<Drawable> {

    private View progressBar;

    public GlideRequestListener(View progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e,
                                Object model,
                                Target<Drawable> target,
                                boolean isFirstResource) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource,
                                   Object model,
                                   Target<Drawable> target,
                                   DataSource dataSource,
                                   boolean isFirstResource) {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
        return false;
    }
}