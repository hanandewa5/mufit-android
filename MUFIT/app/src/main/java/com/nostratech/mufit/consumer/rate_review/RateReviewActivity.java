package com.nostratech.mufit.consumer.rate_review;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.image_properties.PositionedCropTransfomation;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;

public class RateReviewActivity extends MyToolbarBackActivity implements RateReviewContract.View {

    public static final String EXTRA_BOOKING_ID = "bookingId";

    @BindView(R.id.image_headerRateReview)
    ImageView imageHeaderRateReview;
    @BindView(R.id.image_RateReview)
    CircleImageView imageRateReview;
    @BindView(R.id.text_TrainerNameRateReview)
    TextView textTrainerNameRateReview;
    @BindView(R.id.text_ratingDetail)
    TextView textRatingDetail;
    @BindView(R.id.text_review)
    EditText textReview;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.image_ratingRateReview)
    ImageView imageRatingRateReview;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RateReviewPresenter rateReviewPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_review);
        ButterKnife.bind(this);

        initToolbar(toolbar);
        rateReviewPresenter = new RateReviewPresenter(this, this, this);

        Intent intent = getIntent();
        String bookingId = intent.getStringExtra(EXTRA_BOOKING_ID);

        //Retrieve session info
        rateReviewPresenter.getBookingDetail(bookingId);
    }


    @Override
    public void showNoInternetError() {
        showGenericError(getString(R.string.no_internet));
    }

    @Override
    public void showLoading() {
        showProgressDialog(this);
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }


    @OnClick(R.id.btn_submitRateReview)
    public void onViewClicked() {
        dismissKeyboard();

        int rating = (int) ratingBar.getRating();
        String review = textReview.getText().toString();

        rateReviewPresenter.submitReview(rating, review);
    }


    @Override
    public void showErrorEmptyRatingReview() {
        showGenericError(getString(R.string.rate_review_validation_empty_rating_review));
    }

    @Override
    public void showErrorEmptyRating() {
        showGenericError(getString(R.string.rate_review_validation_empty_rating));
    }

    @Override
    public void showErrorEmptyReview() {
        showGenericError(getString(R.string.rate_review_validation_empty_review));
    }

    @Override
    public void showReviewSubmissionSuccess() {
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(this,
                getResources().getString(R.string.dialog_success),
                getResources().getString(R.string.rate_review_success));
        dialog.getButton().setOnClickListener(l ->{
            Intent i = new Intent();
            i.putExtra(RootActivity.EXTRA_REFRESH_FRAGMENT, RootActivity.REFRESH_HISTORY);
            setResult(RESULT_OK, i);
            finishActivity();
        });
        dialog.show();
    }

    @Override
    public void showTrainerDetail(String name, String profilePic, String coverPic) {
        textTrainerNameRateReview.setText(name);

        RequestOptions requestOptions = new RequestOptions();
        GlideApp.with(this)
                .load(profilePic)
                .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(true))
                .placeholder(R.drawable.mufit_logo_white)
                .into(imageRateReview);

        //Set trainer cover image
        GlideApp.with(this)
                .load(coverPic)
                .placeholder(R.drawable.mufit_logo_white)
                .apply(requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(true)
                        .transform(new PositionedCropTransfomation(1, 0)))
                .into(imageHeaderRateReview);
    }

    @Override
    public void showTrainerRating(String rating) {
        textRatingDetail.setText(rating);
        textRatingDetail.setVisibility(View.VISIBLE);
        imageRatingRateReview.setVisibility(View.VISIBLE);
    }
}
