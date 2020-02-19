package com.nostratech.mufit.consumer.muhealth.checklist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.detailtrainer.helper.LayoutUtils;

public class DailyQuestView extends FrameLayout {
        View background;
        ImageView icon;

        public DailyQuestView(@NonNull Context context) {
            this(context, null);
        }

        public DailyQuestView(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);

            FrameLayout.LayoutParams parentParams = new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            parentParams.gravity = Gravity.CENTER;
            setLayoutParams(parentParams);
            
            int size = LayoutUtils.dpToPx(60);
            int margin = LayoutUtils.dpToPx(10);
            
            background = new ImageView(getContext());

            FrameLayout.LayoutParams bgParams = new FrameLayout.LayoutParams(size, size);
            background.setLayoutParams(bgParams);
            addView(background, 0);

            FrameLayout.LayoutParams iconParams = new FrameLayout.LayoutParams(size - margin * 2, size  - margin * 2, Gravity.CENTER);
            iconParams.setMargins(margin, margin, margin, margin);
            iconParams.gravity = Gravity.CENTER;
            icon = new ImageView(getContext());
            icon.setLayoutParams(iconParams);

            addView(icon, 1, iconParams);
        }

        public void setIconImage(int resourceId){
            icon.setImageResource(resourceId);
        }

        public void setDoneStatus(boolean bool){
            background.setBackgroundResource(bool ? R.drawable.muhealth_checklist_bg : R.drawable.muhealth_checklist_bg_disabled);
        }
    }