//package com.nostratech.mufit.consumer.detailtrainer.helper;
//
//import android.content.Context;
//import android.graphics.Typeface;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.nostratech.mufit.consumer.R;
//import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
//import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
//
//import java.util.List;
//
//public class SpecialityAdapter extends ExpandableRecyclerViewAdapter<SpecialityViewHolder, DurationViewHolder> {
//    private Context context;
//    private Typeface tfRegular;
//    public SpecialityAdapter(Context context, List<? extends ExpandableGroup> groups) {
//        super(groups);
//        this.context = context;
//        tfRegular = Typeface.createFromAsset(context.getAssets(),
//                "fonts/trueno_regular.otf");
//    }
//
//    @Override
//    public SpecialityViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_group_speciality, parent, false);
//        return new SpecialityViewHolder(view);
//    }
//
//    @Override
//    public DurationViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.list_detail_item_speciality, parent, false);
//        return new DurationViewHolder(view);
//    }
//
//    @Override
//    public void onBindChildViewHolder(DurationViewHolder holder, int flatPosition,
//                                      ExpandableGroup group, int childIndex) {
//
//        final DurationModel model = ((SpecialityTrainerModel) group).getItems().get(childIndex);
//        holder.setDuration(getTfRegular(), model.getDuration());
//    }
//
//    @Override
//    public void onBindGroupViewHolder(SpecialityViewHolder holder, int flatPosition,
//                                      ExpandableGroup group) {
//
//        holder.setValueHeader(getTfRegular(), group);
//    }
//
//
//    public Context getContext() {
//        return context;
//    }
//
//    public Typeface getTfRegular() {
//        return tfRegular;
//    }
//
//}
