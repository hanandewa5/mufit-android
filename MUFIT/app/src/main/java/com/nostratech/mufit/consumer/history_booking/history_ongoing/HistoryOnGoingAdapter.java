package com.nostratech.mufit.consumer.history_booking.history_ongoing;

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

import com.nostratech.mufit.consumer.R;
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
 * Created by Ahmadan Ditiananda on 5/22/2018.
 */

public class HistoryOnGoingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String paid = "PAID";
    private static final String booked = "BOOKED";
    private static final String approved = "APPROVED";
    private static final String start = "START";
    private static final String receipt_uploaded = "RECEIPT_UPLOADED";

    private OnCardClickedListener listener;

    private List<HistoryBookingModel> historyBookingModels = new ArrayList<>();

    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_LOADING = 0;

    public HistoryOnGoingAdapter(OnCardClickedListener listener){
        this.listener = listener;
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
    public int getItemViewType(int position) {
        return historyBookingModels.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();

        if (holder.getItemViewType() == VIEW_TYPE_LOADING) return;

        final HistoryBookingModel historyBookingModel = historyBookingModels.get(position);

        final ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.setIsRecyclable(false);

        //Set payment date
        if (historyBookingModel.getPaymentDate() != null) {
            String datePayment = DateUtils.parseLong(DateUtils.Format.ddMMyyyy, historyBookingModel.getPaymentDate());
            viewHolder.mPaymentDate.setText(datePayment);
        }


        //Set name
        viewHolder.mTrainerName.setText(historyBookingModel.getTrainerName());

        //Set speciality name
        viewHolder.mSpeciality.setText(historyBookingModel.getBookingSpecialty() + " Class");

        //Set rating
        if (historyBookingModel.getRating() != null) {
            viewHolder.mTrainerRating.setText(historyBookingModel.getRating().toString());
        } else {
            viewHolder.mTrainerRating.setVisibility(View.GONE);
            viewHolder.mTrainerRatingIcon.setVisibility(View.GONE);
        }

        //Set time and date
        long dateReceipt = historyBookingModel.getBookingDate();
        DateFormat dateFormatReceipt = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String dateFormattedReceipt = dateFormatReceipt.format(dateReceipt);
        viewHolder.mTime.setText(dateFormattedReceipt + ", " + getFormattedTimeText(historyBookingModel));

        //Load profile pic
        GlideApp.with(holder.itemView.getContext())
                .load(historyBookingModel.getPhotoTrainer())
                .placeholder(R.color.grey)
                .into(viewHolder.mProfileImage);

        //set code promo
        viewHolder.mCodeBooking.setText(context.getResources().getString(R.string.booking_code) + " " + historyBookingModel.getCode());

        //Start new activity when user clicks on card
        viewHolder.mRoot.setOnClickListener(v -> listener.onCardClicked(historyBookingModel.getId()));

        //Get booking status code (BOOKED, START, etc)
        String statusBooking = historyBookingModel.getStatusBooking();

        //If it is an event
        if (historyBookingModel.getBookingType().equalsIgnoreCase("EVENT")) {
            viewHolder.mStatusBooking.setText("EVENT - " + statusBooking);
        } else {
            viewHolder.mStatusBooking.setText(statusBooking);
        }

        //Change colors of Booking Status text based on code
        switch (statusBooking) {
            //No longer used
            case receipt_uploaded:
                break;
            case start:
                viewHolder.mStatusBooking.setTextColor(0xFFF08210);
                break;
            case paid:
                viewHolder.mStatusBooking.setTextColor(0xFF9DDB38);
                break;
            case booked:
                viewHolder.mStatusBooking.setTextColor(context.getResources().getColor(R.color.trainer_confirmation_color));
                break;
            case approved:
                viewHolder.mStatusBooking.setTextColor(context.getResources().getColor(R.color.unpaid_color));
                break;
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
        Log.d("Adapter", "StartLoading");
        this.historyBookingModels.add(null);
        notifyItemInserted(this.historyBookingModels.size() - 1);
    }

    public void stopLoading() {
        Log.d("Adapter", "StpLoading");
        this.historyBookingModels.remove(this.historyBookingModels.size() - 1);
        notifyItemRemoved(historyBookingModels.size());
    }

    public void clearList() {
        this.historyBookingModels = new ArrayList<>();
        notifyDataSetChanged();
    }

    private String getFormattedTimeText(HistoryBookingModel historyBookingModel) {

        //TODO: ini cuman temporary fix (booking shift kosong)

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
    }


//    private HistoryOnGoingFragment historyOnGoingFragment;

//    public HistoryOnGoingFragment getHistoryOnGoingFragment() {
//        return historyOnGoingFragment;
//    }

    //    public HistoryOnGoingAdapter(HistoryOnGoingFragment historyOnGoingFragment) {
//        this.historyOnGoingFragment = historyOnGoingFragment;
//    }

    //        switch (historyBookingModel.getStatusBooking()) {
//            case receipt_uploaded:
//                ViewHolderReceipt viewHolderReceipt = (ViewHolderReceipt) holder;
//                viewHolderReceipt.setIsRecyclable(false);
//                viewHolderReceipt.textTitleReceipt.setText(historyBookingModel.getStatusBooking().toUpperCase().replace("_"," "));
//
//
//                String tempReceipt = historyBookingModel.getBookingType();
//
//                try {
//                    if ("EVENT".equals(tempReceipt)) {
//                        viewHolderReceipt.textEventReceipt.setVisibility(View.VISIBLE);
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//
//                viewHolderReceipt.textIdReceipt.setText( context.getResources().getString(R.string.booking_code)
//                        + ": " + historyBookingModel.getCode());
//
////                Glide.with(context).load(historyBookingModel.getPhotoTrainer())
////                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
////                                .placeholder(R.drawable.mufit_logo_white)
////                                .override(300,300))
////                        .into(viewHolderReceipt.imageTrainerReceipt);
//
//                viewHolderReceipt.textTrainerNameReceipt.setText(historyBookingModel.getTrainerName());
//
//                long dateReceipt = historyBookingModel.getBookingDate();
//                DateFormat dateFormatReceipt = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                String dateFormattedReceipt = dateFormatReceipt.format(dateReceipt);
//
//                viewHolderReceipt.textDateReceipt.setText(dateFormattedReceipt);
//                viewHolderReceipt.textSpecialityReceipt.setText(historyBookingModel.getBookingSpecialty());
//
//                final int textCountReceipt = historyBookingModel.getBookingShiftList().size();
//                final TextView[] textShiftArrayReceipt = new TextView[textCountReceipt];
//                final TextView textShiftReceipt = new TextView(context);
//                textShiftReceipt.setTextColor(historyOnGoingFragment.getResources().getColor(R.color.grey_2));
//
//                for (int i = 0; i < textCountReceipt; i++) {
//                    String startTimeReceipt = historyBookingModel.getBookingShiftList().get(i).getStartTime();
//                    String startTimeSplitReceipt[] = startTimeReceipt.split(":");
//                    String startTime1Receipt = startTimeSplitReceipt[0];
//                    String startTime2Receipt = startTimeSplitReceipt[1];
//                    startTimeFinal = startTime1Receipt + "." + startTime2Receipt;
//
//                    String endTimeReceipt = historyBookingModel.getBookingShiftList().get(i).getEndTime();
//                    String endTimeSplitReceipt[] = endTimeReceipt.split(":");
//                    String endTime1Receipt = endTimeSplitReceipt[0];
//                    String endTime2Booked = endTimeSplitReceipt[1];
//                    endTimeFinal = endTime1Receipt + "." + endTime2Booked;
//
//                    textShiftReceipt.append(startTimeFinal + " - " + endTimeFinal + "  ");
//                    textShiftArrayReceipt[i] = textShiftReceipt;
//                }
//
//                viewHolderReceipt.layoutDynamicReceipt.addView(textShiftReceipt);
//
//                viewHolderReceipt.cardTrainerReceipt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(context, HistoryDetailActivity.class);
//                        intent.putExtra("bookingId", historyBookingModel.getId());
//                        intent.putExtra("status", "receipt_uploaded");
//                        if (historyBookingModel.getRating() != null) {
//                            intent.putExtra("rating", historyBookingModel.getRating().toString());
//                        }
//                        if (historyBookingModel.getBackground() != null) {
//                            intent.putExtra("cover", historyBookingModel.getBackground());
//                        }
//                        if (historyBookingModel.getPaymentPhoto() != null) {
//                            intent.putExtra("payment_photo", historyBookingModel.getPaymentPhoto());
//                        }
//                        if (historyBookingModel.getPhotoTrainer() != null) {
//                            //Log.d("FRISTY LOG", "HISTORY ONGOING KEPANGGIL NI. NIH URL FOTONYA " + historyBookingModel.getPhotoTrainer());
//                            intent.putExtra("trainer_photo", historyBookingModel.getPhotoTrainer()); //TODO: fristy - get trainer photo url
//                        }
//                        intent.putExtra("version", historyBookingModel.getVersion());
//                        intent.putExtra("booking_type", historyBookingModel.getBookingType());
//                        view.getContext().startActivity(intent);
//                    }
//                });
//
//                break;
//            case paid:
//                ViewHolderConfirmed viewHolderConfirmed = (ViewHolderConfirmed) holder;
//                viewHolderConfirmed.setIsRecyclable(false);
//                viewHolderConfirmed.textTitleConfirmed.setText(historyBookingModel.getStatusBooking().toUpperCase().replace("_"," "));
//
//
//                String tempConfirmed = historyBookingModel.getBookingType();
//
//                try {
//                    if ("EVENT".equals(tempConfirmed)) {
//                        viewHolderConfirmed.textEventConfirmed.setVisibility(View.VISIBLE);
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//
//                viewHolderConfirmed.textIdConfirmed.setText(context.getResources().getString(R.string.booking_code)
//                        + ": " + historyBookingModel.getCode());
//
////                Glide.with(context).load(historyBookingModel.getPhotoTrainer())
////                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
////                                .placeholder(R.drawable.mufit_logo_white)
////                                .skipMemoryCache(true)
////                                .override(300,300))
////                        .into(viewHolderConfirmed.imageTrainerConfirmed);
//
//                viewHolderConfirmed.textTrainerNameConfirmed.setText
//                        (historyBookingModels.get(position).getTrainerName());
//
//                long dateConfirmed = historyBookingModel.getBookingDate();
//                DateFormat dateFormatConfirmed = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                String dateFormattedConfirmed = dateFormatConfirmed.format(dateConfirmed);
//
//                viewHolderConfirmed.textDateConfirmed.setText(dateFormattedConfirmed);
//                if (historyBookingModels.get(position).getRating() == null) {
//                    viewHolderConfirmed.textRatingHistoryConfirmed.setVisibility(View.GONE);
//                    viewHolderConfirmed.imageRatingStar.setVisibility(View.GONE);
//                } else {
//                    viewHolderConfirmed.textRatingHistoryConfirmed.setText
//                            (historyBookingModels.get(position).getRating().toString());
//                }
//
//                viewHolderConfirmed.textSpecialityConfirmed.setText
//                        (historyBookingModels.get(position).getBookingSpecialty());
//
//                final int textCountConfirmed = historyBookingModel.getBookingShiftList().size();
//                final TextView[] textShiftArray = new TextView[textCountConfirmed];
//                final TextView textShiftConfirmed = new TextView(context);
//                textShiftConfirmed.setTextColor(historyOnGoingFragment.getResources().getColor(R.color.grey_2));
//
//                for (int i = 0; i < textCountConfirmed; i++) {
//                    String startTimeConfirmed = historyBookingModel.getBookingShiftList().get(i).getStartTime();
//                    String startTimeSplitConfirmed[] = startTimeConfirmed.split(":");
//                    String startTime1Confirmed = startTimeSplitConfirmed[0];
//                    String startTime2Confirmed = startTimeSplitConfirmed[1];
//                    startTimeFinal = startTime1Confirmed + "." + startTime2Confirmed;
//
//                    String endTimeTrainerConfirmation = historyBookingModel.getBookingShiftList().get(i).getEndTime();
//                    String endTimeSplitTrainerConfirmation[] = endTimeTrainerConfirmation.split(":");
//                    String endTime1Booked = endTimeSplitTrainerConfirmation[0];
//                    String endTime2Booked = endTimeSplitTrainerConfirmation[1];
//                    endTimeFinal = endTime1Booked + "." + endTime2Booked;
//
//                    textShiftConfirmed.append(startTimeFinal + " - " + endTimeFinal + "  ");
//                    textShiftArray[i] = textShiftConfirmed;
//                }
//
//                viewHolderConfirmed.layoutDynamicConfirmed.addView(textShiftConfirmed);
//                viewHolderConfirmed.cardConfirmed.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(context, HistoryDetailActivity.class);
//                        intent.putExtra("bookingId", historyBookingModel.getId());
//                        intent.putExtra("status", "paid");
//                        if (historyBookingModel.getRating() != null) {
//                            intent.putExtra("rating", historyBookingModel.getRating().toString());
//                        }
//                        if (historyBookingModel.getBackground() != null) {
//                            intent.putExtra("cover", historyBookingModel.getBackground());
//                        }
//                        if (historyBookingModel.getPaymentPhoto() != null) {
//                            intent.putExtra("payment_photo", historyBookingModel.getPaymentPhoto());
//                        }
//                        if (historyBookingModel.getPhotoTrainer() != null) {
//                            //Log.d("FRISTY LOG", "HISTORY ONGOING KEPANGGIL NI. NIH URL FOTONYA " + historyBookingModel.getPhotoTrainer());
//                            intent.putExtra("trainer_photo", historyBookingModel.getPhotoTrainer()); //TODO: fristy - get trainer photo url
//                        }
//                        intent.putExtra("version", historyBookingModel.getVersion());
//                        view.getContext().startActivity(intent);
//                    }
//                });
//
//                break;
//            case booked:
//                ViewHolderTrainerConfirmation viewHolderTrainerConfirmation = (ViewHolderTrainerConfirmation) holder;
//                viewHolderTrainerConfirmation.setIsRecyclable(false);
//                viewHolderTrainerConfirmation.textTitleConfirmation.setText(historyBookingModel.getStatusBooking().toUpperCase().replace("_"," "));
//
//                String tempBooked = historyBookingModel.getBookingType();
//
//                try {
//                    if ("EVENT".equals(tempBooked)) {
//                        viewHolderTrainerConfirmation.textEventConfirmation.setVisibility(View.VISIBLE);
//                        viewHolderTrainerConfirmation.btnUploadTrainerConfirmation.setVisibility(View.VISIBLE);
//                        viewHolderTrainerConfirmation.btnUploadTrainerConfirmation.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                historyOnGoingFragment.uploadPayment(historyBookingModel.getId(),
//                                        historyBookingModel.getVersion());
//                            }
//                        });
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//
//                viewHolderTrainerConfirmation.textIdConfirmation.setText(context.getResources().getString(R.string.booking_code)
//                        + ": " + historyBookingModel.getCode());
//
////                Glide.with(context).load(historyBookingModel.getPhotoTrainer())
////                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
////                                .placeholder(R.drawable.mufit_logo_white)
////                                .skipMemoryCache(true)
////                                .override(300,300))
////                        .into(viewHolderTrainerConfirmation.imageTrainerConfirmation);
//
//                viewHolderTrainerConfirmation.textTrainerNameConfirmation.setText(historyBookingModel.getTrainerName());
//
//                long dateConfirmation = historyBookingModel.getBookingDate();
//                DateFormat dateFormatConfirmation = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                String dateFormattedConfirmation = dateFormatConfirmation.format(dateConfirmation);
//
//                viewHolderTrainerConfirmation.textDateConfirmation.setText(dateFormattedConfirmation);
//                viewHolderTrainerConfirmation.textSpecialityConfirmation.setText(historyBookingModel.getBookingSpecialty());
//
//                final int textCountTrainerConfirmation = historyBookingModel.getBookingShiftList().size();
//                final TextView[] textShiftArrayTrainerConfirmation = new TextView[textCountTrainerConfirmation];
//                final TextView textShiftTrainerConfirmation = new TextView(context);
//                textShiftTrainerConfirmation.setTextColor(historyOnGoingFragment.getResources().getColor(R.color.grey_2));
//
//                for (int i = 0; i < textCountTrainerConfirmation; i++) {
//                    String startTimeTrainerConfirmation = historyBookingModel.getBookingShiftList().get(i).getStartTime();
//                    String startTimeSplitTrainerConfirmation[] = startTimeTrainerConfirmation.split(":");
//                    String startTime1Booked = startTimeSplitTrainerConfirmation[0];
//                    String startTime2Booked = startTimeSplitTrainerConfirmation[1];
//                    startTimeFinal = startTime1Booked + "." + startTime2Booked;
//
//                    String endTimeTrainerConfirmation = historyBookingModel.getBookingShiftList().get(i).getEndTime();
//                    String endTimeSplitTrainerConfirmation[] = endTimeTrainerConfirmation.split(":");
//                    String endTime1Booked = endTimeSplitTrainerConfirmation[0];
//                    String endTime2Booked = endTimeSplitTrainerConfirmation[1];
//                    endTimeFinal = endTime1Booked + "." + endTime2Booked;
//
//                    textShiftTrainerConfirmation.append(startTimeFinal + " - " + endTimeFinal + "  ");
//                    textShiftArrayTrainerConfirmation[i] = textShiftTrainerConfirmation;
//                }
//
//                viewHolderTrainerConfirmation.layoutDynamicTrainerConfirmation.addView(textShiftTrainerConfirmation);
//                viewHolderTrainerConfirmation.cardTrainerConfirmation.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(context, HistoryDetailActivity.class);
//                        intent.putExtra("bookingId", historyBookingModel.getId());
//                        intent.putExtra("status", "booked");
//                        if (historyBookingModel.getRating() != null) {
//                            intent.putExtra("rating", historyBookingModel.getRating().toString());
//                        }
//                        if (historyBookingModel.getBackground() != null) {
//                            intent.putExtra("cover", historyBookingModel.getBackground());
//                        }
//                        if (historyBookingModel.getPaymentPhoto() != null) {
//                            intent.putExtra("payment_photo", historyBookingModel.getPaymentPhoto());
//                        }
//                        if (historyBookingModel.getPhotoTrainer() != null) {
//                            //Log.d("FRISTY LOG", "HISTORY ONGOING KEPANGGIL NI. NIH URL FOTONYA " + historyBookingModel.getPhotoTrainer());
//                            intent.putExtra("trainer_photo", historyBookingModel.getPhotoTrainer()); //TODO: fristy - get trainer photo url
//                        }
//                        intent.putExtra("version", historyBookingModel.getVersion());
//                        intent.putExtra("booking_type", historyBookingModel.getBookingType());
//                        view.getContext().startActivity(intent);
//                    }
//                });
//
//                break;
//            case approved:
//                ViewHolderUnpaid viewHolderUnpaid = (ViewHolderUnpaid) holder;
//                viewHolderUnpaid.setIsRecyclable(false);
//                viewHolderUnpaid.textTitleUnpaid.setText(historyBookingModel.getStatusBooking().toUpperCase().replace("_"," "));
//
//                String tempUnpaid = historyBookingModel.getBookingType();
//
//                try {
//                    if ("EVENT".equals(tempUnpaid)) {
//                        viewHolderUnpaid.textEventUnpaid.setVisibility(View.VISIBLE);
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//
//                final int textCountUnpaid = historyBookingModel.getBookingShiftList().size();
//                final TextView[] textShiftArrayUnpaid = new TextView[textCountUnpaid];
//                final TextView textShiftUnpaid = new TextView(context);
//                textShiftUnpaid.setTextColor(historyOnGoingFragment.getResources().getColor(R.color.grey_2));
//
//                for (int i = 0; i < textCountUnpaid; i++) {
//                    String startTimeUnpaid = historyBookingModel.getBookingShiftList().get(i).getStartTime();
//                    String startTimeSplitUnpaid[] = startTimeUnpaid.split(":");
//                    String startTime1Unpaid = startTimeSplitUnpaid[0];
//                    String startTime2Unpaid = startTimeSplitUnpaid[1];
//                    startTimeFinal = startTime1Unpaid + "." + startTime2Unpaid;
//
//                    String endTimeUnpaid = historyBookingModel.getBookingShiftList().get(i).getEndTime();
//                    String endTimeSplitUnpaid[] = endTimeUnpaid.split(":");
//                    String endTime1Unpaid = endTimeSplitUnpaid[0];
//                    String endTime2Unpaid = endTimeSplitUnpaid[1];
//                    endTimeFinal = endTime1Unpaid + "." + endTime2Unpaid;
//
//                    textShiftUnpaid.append(startTimeFinal + " - " + endTimeFinal + "  ");
//                    textShiftArrayUnpaid[i] = textShiftUnpaid;
//                }
//
//                viewHolderUnpaid.layoutDynamicUnpaid.addView(textShiftUnpaid);
//                viewHolderUnpaid.btnUpload.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        historyOnGoingFragment.uploadPayment(historyBookingModel.getId(), historyBookingModel.getVersion());
//                    }
//                });
//
//                long dateUnpaid= historyBookingModel.getBookingDate();
//                DateFormat dateFormatUnpaid = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                String dateFormattedUnpaid = dateFormatUnpaid.format(dateUnpaid);
//
//                viewHolderUnpaid.cardUnpaid.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(context, HistoryDetailActivity.class);
//                        intent.putExtra("bookingId", historyBookingModel.getId());
//                        intent.putExtra("status", "approved");
//                        if (historyBookingModel.getRating() != null) {
//                            intent.putExtra("rating", historyBookingModel.getRating().toString());
//                        }
//                        if (historyBookingModel.getPaymentPhoto() != null) {
//                            intent.putExtra("payment_photo", historyBookingModel.getPaymentPhoto());
//                        }
//                        if (historyBookingModel.getBackground() != null) {
//                            intent.putExtra("cover", historyBookingModel.getBackground());
//                        }
//                        if (historyBookingModel.getPhotoTrainer() != null) {
//                            //Log.d("FRISTY LOG", "HISTORY ONGOING KEPANGGIL NI. NIH URL FOTONYA " + historyBookingModel.getPhotoTrainer());
//                            intent.putExtra("trainer_photo", historyBookingModel.getPhotoTrainer()); //TODO: fristy - get trainer photo url
//                        }
//                        intent.putExtra("version", historyBookingModel.getVersion());
//                        intent.putExtra("booking_type", historyBookingModel.getBookingType());
//                        view.getContext().startActivity(intent);
//
//                    }
//                });
//
//                viewHolderUnpaid.textIdUnpaid.setText(context.getResources().getString(R.string.booking_code)
//                        + ": " + historyBookingModel.getCode());
//
////                Glide.with(context).load(historyBookingModel.getPhotoTrainer())
////                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
////                                .placeholder(R.drawable.mufit_logo_white)
////                                .skipMemoryCache(true)
////                                .override(300,300))
////                        .into(viewHolderUnpaid.imageTrainerUnpaid);
//
//                viewHolderUnpaid.textTrainerNameUnpaid.setText(historyBookingModel.getTrainerName());
//
//                viewHolderUnpaid.textDateUnpaid.setText(dateFormattedUnpaid);
//                viewHolderUnpaid.textSpecialityUnpaid.setText(historyBookingModel.getBookingSpecialty());
//
//                break;
//            case start:
//
//                Bundle bundle = new Bundle();
//                bundle.putString("id_booking_canceled", historyBookingModel.getId());
//                HistoryOnGoingFragment fragment = new HistoryOnGoingFragment();
//                fragment.setArguments(bundle);
//
//                ViewHolderOnSession viewHolderOnSession = (ViewHolderOnSession) holder;
//                viewHolderOnSession.setIsRecyclable(false);
//                viewHolderOnSession.textIdOnSession.setTypeface(getTfRegular());
//                viewHolderOnSession.textTitleOnSession.setTypeface(getTfMedium());
//                viewHolderOnSession.textTitleOnSession.setText(historyBookingModel.getStatusBooking().toUpperCase().replace("_"," "));
//                viewHolderOnSession.textDateOnSession.setTypeface(getTfRegular());
//                viewHolderOnSession.textTrainerNameOnSession.setTypeface(getTfRegular());
//                viewHolderOnSession.textSpecialityOnSession.setTypeface(getTfRegularItalic());
//                viewHolderOnSession.textEventOnSession.setTypeface(getTfMedium());
//
//                String tempOnSession = historyBookingModel.getBookingType();
//
//                try {
//                    if ("EVENT".equals(tempOnSession)) {
//                        viewHolderOnSession.textEventOnSession.setVisibility(View.VISIBLE);
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
//
//                long dateOnSession= historyBookingModel.getBookingDate();
//                DateFormat dateFormatOnSession = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
//                String dateFormattedOnSession = dateFormatOnSession.format(dateOnSession);
//
//                viewHolderOnSession.cardOnSession.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent = new Intent(context, HistoryDetailActivity.class);
//                        intent.putExtra("bookingId", historyBookingModel.getId());
//                        intent.putExtra("status", "start");
//                        if (historyBookingModel.getRating() != null) {
//                            intent.putExtra("rating", historyBookingModel.getRating().toString());
//                        }
//                        if (historyBookingModel.getBackground() != null) {
//                            intent.putExtra("cover", historyBookingModel.getBackground());
//                        }
//                        if (historyBookingModel.getPaymentPhoto() != null) {
//                            intent.putExtra("payment_photo", historyBookingModel.getPaymentPhoto());
//                        }
//                        if (historyBookingModel.getPhotoTrainer() != null) {
//                            //Log.d("FRISTY LOG", "HISTORY ONGOING KEPANGGIL NI. NIH URL FOTONYA " + historyBookingModel.getPhotoTrainer());
//                            intent.putExtra("trainer_photo", historyBookingModel.getPhotoTrainer()); //TODO: fristy - get trainer photo url
//                        }
//                        intent.putExtra("version", historyBookingModel.getVersion());
//                        view.getContext().startActivity(intent);
//                    }
//                });
//
//                viewHolderOnSession.textIdOnSession.setText(context.getResources().getString(R.string.booking_code)
//                        + ": " + historyBookingModel.getCode());
//
////                Glide.with(context).load(historyBookingModel.getPhotoTrainer())
////                        .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
////                                .placeholder(R.drawable.mufit_logo_white)
////                                .skipMemoryCache(true)
////                                .override(300,300))
////                        .into(viewHolderOnSession.imageTrainerOnSession);
//
//                viewHolderOnSession.textTrainerNameOnSession.setText(historyBookingModel.getTrainerName());
//
//                viewHolderOnSession.textDateOnSession.setText(dateFormattedOnSession);
//                viewHolderOnSession.textSpecialityOnSession.setText(historyBookingModel.getBookingSpecialty());
//
//                final int textCountOnSession = historyBookingModel.getBookingShiftList().size();
//                final TextView[] textShiftArrayOnSession = new TextView[textCountOnSession];
//                final TextView textShiftOnSession = new TextView(context);
//                textShiftOnSession.setTypeface(getTfRegular());
//                textShiftOnSession.setTextColor(historyOnGoingFragment.getResources().getColor(R.color.grey_2));
//
//                for (int i = 0; i < textCountOnSession; i++) {
//                    String startTimeUnpaid = historyBookingModel.getBookingShiftList().get(i).getStartTime();
//                    String startTimeSplitUnpaid[] = startTimeUnpaid.split(":");
//                    String startTime1Unpaid = startTimeSplitUnpaid[0];
//                    String startTime2Unpaid = startTimeSplitUnpaid[1];
//                    startTimeFinal = startTime1Unpaid + "." + startTime2Unpaid;
//
//                    String endTimeUnpaid = historyBookingModel.getBookingShiftList().get(i).getEndTime();
//                    String endTimeSplitUnpaid[] = endTimeUnpaid.split(":");
//                    String endTime1Unpaid = endTimeSplitUnpaid[0];
//                    String endTime2Unpaid = endTimeSplitUnpaid[1];
//                    endTimeFinal = endTime1Unpaid + "." + endTime2Unpaid;
//
//                    textShiftOnSession.append(startTimeFinal + " - " + endTimeFinal + "  ");
//                    textShiftArrayOnSession[i] = textShiftOnSession;
//                }
//
//                viewHolderOnSession.layoutDynamicOnSession.addView(textShiftOnSession);
//        }


//    public static class ViewHolder extends RecyclerView.ViewHolder {
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//        }
//    }

//    class ViewHolderConfirmed extends ViewHolder {
//
//        public TextView textTitleConfirmed, textDateConfirmed, textTrainerNameConfirmed, textRatingHistoryConfirmed
//                ,textSpecialityConfirmed, textEventConfirmed, textIdConfirmed;
//        public CircleImageView imageTrainerConfirmed;
//        public CardView cardConfirmed;
//        public LinearLayout layoutDynamicConfirmed;
//        public ImageView imageRatingStar;
//
//        public ViewHolderConfirmed(View itemView) {
//            super(itemView);
//            textIdConfirmed = itemView.findViewById(R.id.text_idConfirmed);
//            textEventConfirmed = itemView.findViewById(R.id.text_titleEventConfirmed);
//            cardConfirmed = itemView.findViewById(R.id.card_Confirmed);
//            textTitleConfirmed = itemView.findViewById(R.id.text_titleConfirmed);
//            textDateConfirmed = itemView.findViewById(R.id.text_dateConfirmed);
//            imageTrainerConfirmed = itemView.findViewById(R.id.image_trainerConfirmed);
//            textTrainerNameConfirmed = itemView.findViewById(R.id.text_trainerNameConfirmed);
//            textRatingHistoryConfirmed = itemView.findViewById(R.id.text_ratingHistoryConfirmed);
//            textSpecialityConfirmed = itemView.findViewById(R.id.text_specialityConfirmed);
//            layoutDynamicConfirmed = itemView.findViewById(R.id.layout_dynamicConfirmed);
//            imageRatingStar = itemView.findViewById(R.id.image_ratingConfirmed);
//        }
//    }
//
//    class ViewHolderTrainerConfirmation extends ViewHolder {
//
//        public TextView textTitleConfirmation, textDateConfirmation, textTrainerNameConfirmation,
//        textSpecialityConfirmation, textEventConfirmation,textIdConfirmation;
//        public CircleImageView imageTrainerConfirmation;
//        public CardView cardTrainerConfirmation;
//        public LinearLayout layoutDynamicTrainerConfirmation;
//        public Button btnUploadTrainerConfirmation;
//
//        public ViewHolderTrainerConfirmation(View itemView) {
//            super(itemView);
//            textIdConfirmation = itemView.findViewById(R.id.text_idConfirmation);
//            textEventConfirmation = itemView.findViewById(R.id.text_titleEventConfirmation);
//            textTitleConfirmation = itemView.findViewById(R.id.text_titleConfirmation);
//            textDateConfirmation = itemView.findViewById(R.id.text_dateConfirmation);
//            textTrainerNameConfirmation = itemView.findViewById(R.id.text_trainerNameConfirmation);
//            textSpecialityConfirmation = itemView.findViewById(R.id.text_specialityConfirmation);
//            layoutDynamicTrainerConfirmation = itemView.findViewById(R.id.layout_dynamicConfirmation);
//            imageTrainerConfirmation = itemView.findViewById(R.id.image_trainerConfirmation);
//            cardTrainerConfirmation = itemView.findViewById(R.id.cardTrainerConfirmation);
//            btnUploadTrainerConfirmation = itemView.findViewById(R.id.btn_uploadBookingForEvent);
//        }
//    }
//
//    class ViewHolderUnpaid extends ViewHolder {
//
//        public TextView textTitleUnpaid, textTrainerNameUnpaid, textDateUnpaid, textSpecialityUnpaid,
//                textEventUnpaid, textIdUnpaid;
//        public CircleImageView imageTrainerUnpaid;
//        public Button btnUpload;
//        public CardView cardUnpaid;
//        public LinearLayout layoutDynamicUnpaid;
//
//        public ViewHolderUnpaid(View itemView) {
//            super(itemView);
//            textIdUnpaid = itemView.findViewById(R.id.text_idUnpaid);
//            textEventUnpaid = itemView.findViewById(R.id.text_titleEventUnpaid);
//            textTitleUnpaid = itemView.findViewById(R.id.text_titleUnpaid);
//            textDateUnpaid = itemView.findViewById(R.id.text_dateUnpaid);
//            textTrainerNameUnpaid = itemView.findViewById(R.id.text_trainerNameUnpaid);
//            textSpecialityUnpaid = itemView.findViewById(R.id.text_specialityUnpaid);
//            layoutDynamicUnpaid = itemView.findViewById(R.id.layout_dynamicUnpaid);
//            imageTrainerUnpaid = itemView.findViewById(R.id.image_TrainerUnpaid);
//            btnUpload = itemView.findViewById(R.id.btn_upload);
//            cardUnpaid = itemView.findViewById(R.id.cardUnpaid);
//        }
//    }
//
//    class ViewHolderOnSession extends ViewHolder {
//
//        public TextView textTitleOnSession, textDateOnSession, textTrainerNameOnSession, textSpecialityOnSession,
//                textEventOnSession, textIdOnSession;
//        public CircleImageView imageTrainerOnSession;
//        public CardView cardOnSession;
//        public LinearLayout layoutDynamicOnSession;
//
//        public ViewHolderOnSession(View itemView) {
//            super(itemView);
//            textIdOnSession = itemView.findViewById(R.id.text_idOnSession);
//            textEventOnSession = itemView.findViewById(R.id.text_titleEventOnSession);
//            textTitleOnSession = itemView.findViewById(R.id.text_titleOnSession);
//            textDateOnSession = itemView.findViewById(R.id.text_dateOnSession);
//            textTrainerNameOnSession = itemView.findViewById(R.id.text_trainerNameOnSession);
//            textSpecialityOnSession = itemView.findViewById(R.id.text_specialityOnSession);
//            layoutDynamicOnSession = itemView.findViewById(R.id.layout_dynamicOnSession);
//            imageTrainerOnSession = itemView.findViewById(R.id.image_TrainerOnSession);
//            cardOnSession = itemView.findViewById(R.id.cardOnSession);
//        }
//    }
//
//    class ViewHolderReceipt extends ViewHolder {
//
//        public TextView textTitleReceipt, textDateReceipt, textTrainerNameReceipt,
//                textSpecialityReceipt, textEventReceipt,textIdReceipt;
//        public CircleImageView imageTrainerReceipt;
//        public CardView cardTrainerReceipt;
//        public LinearLayout layoutDynamicReceipt;
//
//        public ViewHolderReceipt(View itemView) {
//            super(itemView);
//            textIdReceipt = itemView.findViewById(R.id.text_idReceipt);
//            textEventReceipt = itemView.findViewById(R.id.text_titleEventReceipt);
//            textTitleReceipt = itemView.findViewById(R.id.text_titleReceipt);
//            textDateReceipt = itemView.findViewById(R.id.text_dateReceipt);
//            textTrainerNameReceipt = itemView.findViewById(R.id.text_trainerNameReceipt);
//            textSpecialityReceipt = itemView.findViewById(R.id.text_specialityReceipt);
//            layoutDynamicReceipt = itemView.findViewById(R.id.layout_dynamicReceipt);
//            imageTrainerReceipt = itemView.findViewById(R.id.image_trainerReceipt);
//            cardTrainerReceipt = itemView.findViewById(R.id.card_Receipt);
//        }
//    }


}