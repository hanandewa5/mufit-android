//package com.nostratech.mufit.consumer.detailtrainer.helper;
//
//import android.graphics.Typeface;
//import android.view.View;
//import android.view.animation.RotateAnimation;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.nostratech.mufit.consumer.R;
//import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
//import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;
//
//import static android.view.animation.Animation.RELATIVE_TO_SELF;
//
//public class SpecialityViewHolder extends GroupViewHolder {
//
//    private TextView mSpeciality;
//    private TextView mPrice;
//    private TextView mDivider;
//    private ImageView mArrow;
//
//    SpecialityViewHolder(View itemView) {
//        super(itemView);
//        mSpeciality = itemView.findViewById(R.id.txt_speciality);
//        mPrice = itemView.findViewById(R.id.txt_price);
//        mDivider = itemView.findViewById(R.id.divider);
//        mArrow = itemView.findViewById(R.id.arrow);
//    }
//
//    public void setValueHeader(Typeface typeface, ExpandableGroup model) {
//        mSpeciality.setTypeface(typeface);
//        mPrice.setTypeface(typeface);
//        mDivider.setTypeface(typeface);
//        mSpeciality.setText(model.getTitle());
//        mPrice.setText(((SpecialityTrainerModel) model).getPrice());
//    }
//    @Override
//    public void expand() {
//        animateExpand();
//    }
//
//    @Override
//    public void collapse() {
//        animateCollapse();
//    }
//
//    private void animateExpand() {
//        RotateAnimation rotate =
//                new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
//        rotate.setDuration(300);
//        rotate.setFillAfter(true);
//        mArrow.startAnimation(rotate);
//    }
//
//    private void animateCollapse() {
//        RotateAnimation rotate =
//                new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
//        rotate.setDuration(300);
//        rotate.setFillAfter(true);
//        mArrow.startAnimation(rotate);
//    }
//}
