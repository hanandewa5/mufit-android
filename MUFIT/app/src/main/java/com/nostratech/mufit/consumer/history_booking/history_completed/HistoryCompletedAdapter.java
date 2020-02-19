package com.nostratech.mufit.consumer.history_booking.history_completed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.constants.BookingStatusConstants;
import com.nostratech.mufit.consumer.model.HistoryBookingModel;
import com.nostratech.mufit.consumer.model.HistoryBookingShiftModel;
import com.nostratech.mufit.consumer.utils.date.DateUtils;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmadan Ditiananda on 5/24/2018.
 */

public class HistoryCompletedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HistoryBookingModel> historyBookingModels = new ArrayList<>();
    private RequestOptions requestOptions = new RequestOptions();

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 0;

    private final OnCardClickedListener listener;

    public HistoryCompletedAdapter(OnCardClickedListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return historyBookingModels.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_history_incoming, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_progress_bar, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        if (holder.getItemViewType() == VIEW_TYPE_LOADING) return;

        final HistoryBookingModel historyBookingModel = historyBookingModels.get(position);
        final ViewHolder viewHolder = (ViewHolder) holder;
        holder.setIsRecyclable(false);

        //Set payment date
        if (historyBookingModel.getPaymentDate() != null) {
            String datePayment = DateUtils.
                    parseLong(DateUtils.Format.ddMMyyyy, historyBookingModel.getPaymentDate());
            viewHolder.mPaymentDate.setText(datePayment);
        }

        //set trainer name
        viewHolder.mTrainerName.setText(historyBookingModel.getTrainerName());

        //set speciality
        viewHolder.mSpeciality.setText(historyBookingModel.getBookingSpecialty() + " Class");

        //Set time
        long dateReceipt = historyBookingModel.getBookingDate();
        DateFormat dateFormatReceipt = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateFormattedReceipt = dateFormatReceipt.format(dateReceipt);
        viewHolder.mTime.setText(dateFormattedReceipt + ", " + getTimeFormattedText(historyBookingModel));

        //set code promo
        viewHolder.mCodeBooking.setText(context.getResources().getString(R.string.booking_code) + " " + historyBookingModel.getCode());

        //Load image
        GlideApp.with(holder.itemView.getContext())
                .load(historyBookingModel.getPhotoTrainer())
                .placeholder(R.color.grey)
                .into(viewHolder.mProfileImage);

        //Open new activity (Rate & Review or HistoryDetail) when user clicks on card
        viewHolder.mRoot.setOnClickListener(v -> listener.onCardClicked(historyBookingModel.getId()));

        //Show trainer rating if available
        if(historyBookingModel.getRating() != null){
            ((ViewHolder) holder).mTrainerRating.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).mTrainerRatingIcon.setVisibility(View.VISIBLE);
            ((ViewHolder) holder).mTrainerRating.setText(String.valueOf(historyBookingModel.getRating()));
        } else {
            ((ViewHolder) holder).mTrainerRating.setVisibility(View.GONE);
            ((ViewHolder) holder).mTrainerRatingIcon.setVisibility(View.GONE);
        }



        String statusBooking = historyBookingModel.getStatusBooking();
        String statusText = statusBooking;

        //Change color of booking status text based on code
        switch (statusBooking) {
            case BookingStatusConstants.COMPLETED:
                viewHolder.mStatusBooking.setTextColor(context.getResources().getColor(R.color.completed_color));
                viewHolder.mRoot.setOnClickListener(v-> listener.onCardCompletedClicked(historyBookingModel.getId()));
                break;
            case BookingStatusConstants.RATED:
                viewHolder.mStatusBooking.setTextColor(context.getResources().getColor(R.color.trainer_confirmation_color));
                break;
            case BookingStatusConstants.CANCEL:
            case BookingStatusConstants.BOOKED_CANCEL:
                statusText = "CANCELLED";
                viewHolder.mStatusBooking.setTextColor(context.getResources().getColor(R.color.canceled_color));
                break;
        }

        //If it is an event
        if (historyBookingModel.getBookingType().equalsIgnoreCase("EVENT")) {
            viewHolder.mStatusBooking.setText("EVENT - " + statusText);
        } else {
            viewHolder.mStatusBooking.setText(statusText);
        }

    }

    @Override
    public int getItemCount() {
        return historyBookingModels.size();
    }

    public void updateList(List<HistoryBookingModel> listModels) {
        this.historyBookingModels.addAll(listModels);
        notifyDataSetChanged();
    }

    public void startLoading() {
        Log.d("CompletedAdapter", "StartLoading");
        this.historyBookingModels.add(null);
        notifyItemInserted(this.historyBookingModels.size() - 1);
    }

    public void stopLoading() {
        Log.d("CompletedAdapter", "StpLoading");
        this.historyBookingModels.remove(this.historyBookingModels.size() - 1);
        notifyItemRemoved(historyBookingModels.size());
    }

    public void clearList() {
        this.historyBookingModels = new ArrayList<>();
        notifyDataSetChanged();
    }

    private String getTimeFormattedText(HistoryBookingModel historyBookingModel) {

        //TODO: ini cuman temporary fix (booking shift kosong)
        //TODO: refactor jadi static method

        List<HistoryBookingShiftModel> shiftList = historyBookingModel.getBookingShiftList();

        if (!shiftList.isEmpty()) {
            String startTime = shiftList.get(0).getStartTime();
            String startTimeSplit[] = startTime.split(":");
            String startTimeHr = startTimeSplit[0];
            String startTimeMin = startTimeSplit[1];

            String startTimeFormatted = startTimeHr + "." + startTimeMin;

            String endTime = shiftList.get(0).getEndTime();
            String endTimeSplit[] = endTime.split(":");
            String endTimeHr = endTimeSplit[0];
            String endTimeMin = endTimeSplit[1];
            String endTimeFormatted = endTimeHr + "." + endTimeMin;

            return startTimeFormatted + " - " + endTimeFormatted;
        } else {
            return "(Shift Tidak Ditemukan)";
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card)
        CardView mRoot;

        @BindView(R.id.status_booking)
        TextView mStatusBooking;
        @BindView(R.id.payment_date)
        TextView mPaymentDate;
        @BindView(R.id.profile_image)
        ImageView mProfileImage;
        @BindView(R.id.trainer_name)
        TextView mTrainerName;
        @BindView(R.id.speciality)
        TextView mSpeciality;
        @BindView(R.id.code_booking)
        TextView mCodeBooking;
        @BindView(R.id.time)
        TextView mTime;

        @BindView(R.id.trainer_rating)
        TextView mTrainerRating;
        @BindView(R.id.trainer_rating_star)
        ImageView mTrainerRatingIcon;

        @BindView(R.id.button_cancel)
        Button mCancelButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ProgressViewHolder extends RecyclerView.ViewHolder {

        ProgressBar progressBar;

        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

    public interface OnCardClickedListener {
        void onCardClicked(String bookingId);
        void onCardCompletedClicked(String bookingId);
    }
}