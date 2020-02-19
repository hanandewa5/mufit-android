package com.nostratech.mufit.consumer.history_detail;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextClock;
import android.widget.TextView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.HistoryDetailModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ahmadan Ditiananda on 5/26/2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listData;
    private HashMap<String, List<String>> listDataChild;

    private Typeface tfLight;
    private Typeface tfLightItalic;
    private Typeface tfRegular;
    private Typeface tfRegularItalic;
    private Typeface tfMedium;
    private Typeface tfMediumItalic;
    private Typeface tfBold;
    private Typeface tfBoldItalic;

    public ExpandableListAdapter(Context context, List<String> listData, HashMap<String, List<String>> listDataChild) {
        this.context = context;
        this.listData = listData;
        this.listDataChild = listDataChild;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public int getGroupCount() {
        return this.listData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.listDataChild.get(this.listData.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.listData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.listDataChild.get(this.listData.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        initTypefaces(context);

        String headerTitle = (String) getGroup(i);
//        String headerRpText = (String) getGroup(i);
//        String headerPrice = (String) getGroup(i);

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_history_detail, null);
        }

        TextView textViewHeader = (TextView) view.findViewById(R.id.text_speciality);
        textViewHeader.setTypeface(getTfMedium());

//        TextView textViewRp = (TextView) view.findViewById(R.id.text_specialittyHistoryDetailRp);
//        textViewRp.setTypeface(getTfRegular());
//
//        TextView textViewPrice = (TextView) view.findViewById(R.id.text_specialityHistoryDetailPrice);
//        textViewPrice.setTypeface(getTfRegular());

        textViewHeader.setText(headerTitle);
//        textViewHeader.setText(headerRpText);
//        textViewHeader.setText(headerPrice);

        return view;
    }

    @Override
    public View getChildView(int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        initTypefaces(context);

        final String childText = (String) getChild(i,i1);

        if(view ==  null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.adapter_history_detail_expand, null);
        }

        TextView textViewChild = (TextView) view.findViewById(R.id.text_specialityHistoryDetailExpand);
        textViewChild.setTypeface(getTfRegular());
        textViewChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    private void initTypefaces(Context context){
        tfLight = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_light.otf");
        tfLightItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_light_italic.otf");
        tfRegular = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_regular.otf");
        tfRegularItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_regular_italic.otf");
        tfMedium = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_medium.otf");
        tfMediumItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_medium_italic.otf");
        tfBold = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_bold.otf");
        tfBoldItalic = Typeface.createFromAsset(context.getAssets(),
                "fonts/trueno_bold_italic.otf");
    }

    public Typeface getTfLight() {
        return tfLight;
    }

    public void setTfLight(Typeface tfLight) {
        this.tfLight = tfLight;
    }

    public Typeface getTfLightItalic() {
        return tfLightItalic;
    }

    public void setTfLightItalic(Typeface tfLightItalic) {
        this.tfLightItalic = tfLightItalic;
    }

    public Typeface getTfRegular() {
        return tfRegular;
    }

    public void setTfRegular(Typeface tfRegular) {
        this.tfRegular = tfRegular;
    }

    public Typeface getTfRegularItalic() {
        return tfRegularItalic;
    }

    public void setTfRegularItalic(Typeface tfRegularItalic) {
        this.tfRegularItalic = tfRegularItalic;
    }

    public Typeface getTfMedium() {
        return tfMedium;
    }

    public void setTfMedium(Typeface tfMedium) {
        this.tfMedium = tfMedium;
    }

    public Typeface getTfMediumItalic() {
        return tfMediumItalic;
    }

    public void setTfMediumItalic(Typeface tfMediumItalic) {
        this.tfMediumItalic = tfMediumItalic;
    }

    public Typeface getTfBold() {
        return tfBold;
    }

    public void setTfBold(Typeface tfBold) {
        this.tfBold = tfBold;
    }

    public Typeface getTfBoldItalic() {
        return tfBoldItalic;
    }

    public void setTfBoldItalic(Typeface tfBoldItalic) {
        this.tfBoldItalic = tfBoldItalic;
    }
}
