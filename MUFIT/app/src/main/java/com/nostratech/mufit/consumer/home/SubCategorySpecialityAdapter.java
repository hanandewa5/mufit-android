package com.nostratech.mufit.consumer.home;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.category.CategorySpecialityModel;

import java.util.List;

//TODO: Cleanup
public class SubCategorySpecialityAdapter extends RecyclerView.Adapter<SubCategorySpecialityAdapter.ViewHolder> {

    private Context context;
    private List<CategorySpecialityModel> SubCategoriesListModels;
    private ToggleButton previousToogleButton;
    private String selectedSpeciality = null;

    public SubCategorySpecialityAdapter(List<CategorySpecialityModel> SubCategoriesListModels) {
        this.SubCategoriesListModels = SubCategoriesListModels;
    }

    @NonNull
    @Override
    public SubCategorySpecialityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_categories_list,
                parent, false);
        context = parent.getContext();
        return new SubCategorySpecialityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategorySpecialityAdapter.ViewHolder holder, final int position) {

        holder.toggleButtonCategories.setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/trueno_regular.otf"));
        holder.toggleButtonCategories.setText(SubCategoriesListModels.get(position).getName());
        holder.toggleButtonCategories.setTextOn(SubCategoriesListModels.get(position).getName());
        holder.toggleButtonCategories.setTextOff(SubCategoriesListModels.get(position).getName());

        holder.toggleButtonCategories.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    //Log.d("FRISTY LOG", "masuk oncheked listener CHEKED awal " + compoundButton.getText().toString());
//                    compoundButton.setBackground(context
//                            .getResources().getDrawable(R.drawable.button_sub_categories));
//                    compoundButton.setTextColor(context.getResources().getColor(R.color.white100));

                    if (previousToogleButton != null) {
                        if (!compoundButton.equals(previousToogleButton)) {
                            //Log.d("FRISTY LOG", "masuk oncheked listener beda CHEKED awal " + previousToogleButton.getText().toString());
                            previousToogleButton.setChecked(false);
//                            previousToogleButton.setBackground(context.getResources().getDrawable(R.drawable.button_background_transparent));
//                            previousToogleButton.setTextColor(context.getResources().getColor(R.color.black54));
                        } else {
                            compoundButton.setChecked(true);
//                            compoundButton.setBackground(context.getResources().getDrawable(R.drawable.button_sub_categories));
//                            compoundButton.setTextColor(context.getResources().getColor(R.color.white100));
                            previousToogleButton = (ToggleButton) compoundButton;
                            selectedSpeciality = compoundButton.getText().toString();
                            return;
                        }
                    }
                } else {
                    if (previousToogleButton != null) {
                        if (!compoundButton.equals(previousToogleButton)) {
                            //Log.d("FRISTY LOG", "masuk not cheked listener awal " + compoundButton.getText().toString());
                            //Log.d("FRISTY LOG", "masuk not cheked listener awal " + previousToogleButton.getText().toString());
//                            previousToogleButton.setBackground(context.getResources().getDrawable(R.drawable.button_background_transparent));
//                            previousToogleButton.setTextColor(context.getResources().getColor(R.color.black54));
                            previousToogleButton.setChecked(false);

                            compoundButton.setChecked(true);
//                            compoundButton.setBackground(context.getResources().getDrawable(R.drawable.button_sub_categories));
//                            compoundButton.setTextColor(context.getResources().getColor(R.color.white100));
                        } else {
                            //Log.d("FRISTY LOG", "masuk not cheked listener SAMA awal " + compoundButton.getText().toString());
                            //Log.d("FRISTY LOG", "masuk not cheked listener SAMA awal " + previousToogleButton.getText().toString());
                            compoundButton.setChecked(false);
//                            compoundButton.setBackground(context.getResources().getDrawable(R.drawable.button_background_transparent));
//                            compoundButton.setTextColor(context.getResources().getColor(R.color.black54));
                            previousToogleButton = (ToggleButton) compoundButton;
                            selectedSpeciality = null;
                            return;
                        }
                    }
                }
                previousToogleButton = (ToggleButton) compoundButton;
                compoundButton.setChecked(true);
                selectedSpeciality = compoundButton.getText().toString();
                //Log.d("FRISTY LOG", "after change previousToogleButton " + previousToogleButton.getText().toString());
                //Log.d("FRISTY LOG", "after change compoundButton " + compoundButton.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return SubCategoriesListModels.size();
    }

    public String getSelectedSpeciality() {
        return selectedSpeciality;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ToggleButton toggleButtonCategories;

        public ViewHolder(View itemView) {
            super(itemView);
            toggleButtonCategories = itemView.findViewById(R.id.text_categories_list);
        }
    }
}
