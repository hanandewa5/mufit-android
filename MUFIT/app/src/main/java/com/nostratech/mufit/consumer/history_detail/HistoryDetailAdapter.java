package com.nostratech.mufit.consumer.history_detail;

import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.model.BookingSpecialityModel;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.ViewHolder> {

    HistoryDetailActivity historyDetailActivity;
    List<BookingSpecialityModel> bookingSpecialityModelList;
    ArrayList<BookingSpecialityModel> listTemp;
    Context context;

    private Typeface tfLight;
    private Typeface tfLightItalic;
    private Typeface tfRegular;
    private Typeface tfRegularItalic;
    private Typeface tfMedium;
    private Typeface tfMediumItalic;
    private Typeface tfBold;
    private Typeface tfBoldItalic;

    public HistoryDetailAdapter(HistoryDetailActivity historyDetailActivity,
                                List<BookingSpecialityModel> bookingSpecialityModelList) {
        this.historyDetailActivity = historyDetailActivity;
        this.bookingSpecialityModelList = bookingSpecialityModelList;
        this.listTemp = new ArrayList<>();
        this.listTemp.addAll(bookingSpecialityModelList);
    }

    @NonNull
    @Override
    public HistoryDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history_detail,
                parent, false);
        context = parent.getContext();
        initTypefaces(context);
        return new HistoryDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryDetailAdapter.ViewHolder holder, int position) {
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getInstance(localeID);

        holder.textSpeciality.setText(String.valueOf(bookingSpecialityModelList.get(position).getName()));
        holder.textPrice.setText(String.valueOf(formatRupiah.format(bookingSpecialityModelList.get(position).getPrice())));
    }

    @Override
    public int getItemCount() {
        return bookingSpecialityModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textSpeciality, textQuantity, textRupiah, textPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            textSpeciality = itemView.findViewById(R.id.text_speciality);
            textPrice = itemView.findViewById(R.id.text_price);
            textRupiah = itemView.findViewById(R.id.text_rupiah);
        }
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
