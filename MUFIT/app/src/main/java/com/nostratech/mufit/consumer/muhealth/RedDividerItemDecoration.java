package com.nostratech.mufit.consumer.muhealth;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;

/**
 * Does not draw the last decoration
 */
//TODO: Remove if not used
public class RedDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable divider;

    public RedDividerItemDecoration(Context context) {
        divider = ContextCompat.getDrawable(context, R.drawable.divider_red);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();

        //Modify condition to childCount - 1 if you want to show the last divider
        for (int i = 0; i <= childCount - 2; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + divider.getIntrinsicHeight();

            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom);
            divider.draw(canvas);
        }
    }
}
