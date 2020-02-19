package com.nostratech.mufit.consumer.legacy;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.SpecialityModel;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Typeface tfLight;
    private Typeface tfLightItalic;
    private Typeface tfRegular;
    private Typeface tfRegularItalic;
    private Typeface tfMedium;
    private Typeface tfMediumItalic;
    private Typeface tfBold;
    private Typeface tfBoldItalic;
    private Context _context;
    private List<SpecialityModel> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<SpecialityModel, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<SpecialityModel> listDataHeader,
                                 HashMap<SpecialityModel, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_detail_item_speciality, null);
        }

        TextView textDurationTitle = convertView
                .findViewById(R.id.duration_title);
        TextView txtListChild = convertView
                .findViewById(R.id.duration);

        textDurationTitle.setTypeface(getTfRegular());
        txtListChild.setText(childText);
        txtListChild.setTypeface(getTfRegular());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        initTypefaces();
        SpecialityModel temp = (SpecialityModel) getGroup(groupPosition);
        String headerTitle = temp.getName();
        String price = temp.getPrice();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_speciality, null);
        }

        initTypefaces();
        TextView textHeader = (TextView) convertView
                .findViewById(R.id.txt_price);
        TextView textDivider = convertView
                .findViewById(R.id.divider);
        TextView textPrice = convertView
                .findViewById(R.id.txt_speciality);
        textHeader.setTypeface(getTfRegular());
        textDivider.setTypeface(getTfRegular());
        textPrice.setTypeface(getTfRegular());
        textHeader.setText(headerTitle);
        textPrice.setText(price);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private void initTypefaces() {
        tfLight = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_light.otf");
        tfLightItalic = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_light_italic.otf");
        tfRegular = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_regular.otf");
        tfRegularItalic = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_regular_italic.otf");
        tfMedium = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_medium.otf");
        tfMediumItalic = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_medium_italic.otf");
        tfBold = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_bold.otf");
        tfBoldItalic = Typeface.createFromAsset(_context.getAssets(),
                "fonts/trueno_bold_italic.otf");
    }

    public Typeface getTfLight() {
        return tfLight;
    }

    public Typeface getTfLightItalic() {
        return tfLightItalic;
    }

    public Typeface getTfRegular() {
        return tfRegular;
    }

    public Typeface getTfRegularItalic() {
        return tfRegularItalic;
    }

    public Typeface getTfMedium() {
        return tfMedium;
    }

    public Typeface getTfMediumItalic() {
        return tfMediumItalic;
    }

    public Typeface getTfBold() {
        return tfBold;
    }

    public Typeface getTfBoldItalic() {
        return tfBoldItalic;
    }
}