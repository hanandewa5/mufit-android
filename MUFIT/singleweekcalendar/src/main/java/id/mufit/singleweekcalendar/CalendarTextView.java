package id.mufit.singleweekcalendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

public class CalendarTextView extends androidx.appcompat.widget.AppCompatTextView {

    static final int PAINT_RED = 0xFFD0021B;
    static final int PAINT_GREEN = 0xFF7ED321;
    static final int PAINT_YELLOW = 0xFFF8E71C;

    private Paint mPaint;

    private boolean selected = false;

    private int indicatorRadius = 0;
    //set as 5px initially

    private int yOffset = 0;

    public void setIndicatorRadius(int radius){
        this.indicatorRadius = radius;
    }

    public void setIndicatorVerticalOffset(int yOffset){
        this.yOffset = yOffset;
    }

    public void select(){
        this.selected = true;
        invalidate();
    }

    public void unselect(){
        this.selected = false;
        invalidate();
    }

    public void drawIndicator(int color){
        mPaint.setColor(color);
        indicatorRadius = (int) getResources().getDimension(R.dimen.trainer_detail_calendar_decoration_radius);
        invalidate();
    }

    public CalendarTextView(Context context) {
        super(context);
    }

    public CalendarTextView(Context context, AttributeSet attrs){
        super(context, attrs);
        mPaint = new Paint();
    }


    /**
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = this.getMeasuredWidth();
        yOffset = size / 4;
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getMeasuredWidth() / 2;

        if(indicatorRadius != 0){
            canvas.drawCircle(center, center + yOffset, indicatorRadius, mPaint);
        }

        if(selected){
            mPaint.setColor(Color.YELLOW);
            int radius = center - 5;
            canvas.drawCircle(center, center, radius, mPaint );
        }



    }
}
