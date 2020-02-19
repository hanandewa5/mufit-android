package com.nostratech.mufit.consumer.history_detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.model.BookingSpecialityModel;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import id.mufit.core.dialog.MufitDialogOneButtonWithText;
import id.mufit.core.dialog.MufitDialogTwoButtons;

public class HistoryDetailActivity extends MyToolbarBackActivity implements HistoryDetailContract.View {

    public static final String EXTRA_BOOKING_ID = "bookingId";

    @BindView(R.id.image_headerDetail)
    ImageView imageHeaderDetail;
    @BindView(R.id.image_trainerDetail)
    CircleImageView imageTrainerDetail;
    @BindView(R.id.text_trainerDetailName)
    TextView textTrainerDetailName;
    @BindView(R.id.text_ratingDetail)
    TextView textRatingDetail;
    @BindView(R.id.layout_ratereview)
    LinearLayout layoutRatereview;
//    @BindView(R.id.btn_UploadBuktiFromDetail)
//    Button btnUploadBuktiFromDetail;
    @BindView(R.id.layout_HistoryDetail)
    RelativeLayout layoutHistoryDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_btnCancel)
    ViewGroup layoutBtnCancel;
    @BindView(R.id.btn_CancelFromDetail)
    Button btnCancelBooking;
    @BindView(R.id.image_ratingStar)
    ImageView imageRatingStar;
    @BindView(R.id.layout_textGroup)
    ViewGroup layoutTextGroup;
    @BindView(R.id.layout_scrollHistoryDetail)
    ScrollView layoutScrollHistoryDetail;
    @BindView(R.id.text_address_content)
    TextView textAddress;
    @BindView(R.id.text_date)
    TextView textDate;
    @BindView(R.id.text_session_time)
    TextView textSessionTime;
    @BindView(R.id.text_BookingCode)
    TextView textBookingCode;
    @BindView(R.id.text_voucher_code)
    TextView textVoucherCode;
    @BindView(R.id.text_statusJudul)
    TextView textStatusJudul;
    @BindView(R.id.text_Status)
    TextView textStatus;
    @BindView(R.id.rv_HistoryDetail)
    RecyclerView rvHistoryDetail;

    @BindView(R.id.text_discount)
    TextView textDiscount;
    @BindView(R.id.text_priceDiscount)
    TextView textPriceDiscount;
    @BindView(R.id.text_priceServiceFee)
    TextView textPriceServiceFee;
    @BindView(R.id.text_total)
    TextView textTotal;
    @BindView(R.id.text_priceTotal)
    TextView textPriceTotal;
    @BindView(R.id.text_transferJudul)
    TextView textTransferJudul;
    @BindView(R.id.text_rekening)
    TextView textRekening;
    @BindView(R.id.layout_rekening)
    RelativeLayout layoutRekening;
    @BindView(R.id.text_namaPemilikRekening)
    TextView textNamaPemilikRekening;
    @BindView(R.id.layout_namaPemilikRekening)
    RelativeLayout layoutNamaPemilikRekening;
    @BindView(R.id.text_noInternet)
    TextView textNoInternet;
    @BindView(R.id.layout_noInternet)
    LinearLayout layoutNoInternet;
    @BindView(R.id.progressBar_loading)
    ProgressBar progressBarLoading;
    @BindView(R.id.swipeRefresh_HistoryDetail)
    SwipeRefreshLayout swipeRefreshHistoryDetail;
    @BindView(R.id.text_noInternet2)
    TextView textNoInternet2;

    private HistoryDetailContract.HistoryDetailPresenter historyDetailPresenter;
    private String bookingId;
//    final int REQUEST_GALLERY = 1;
//    private String paymentDescTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        Intent intent = getIntent();
        bookingId = intent.getStringExtra(EXTRA_BOOKING_ID);
        String rating = intent.getStringExtra("rating");

        historyDetailPresenter = new HistoryDetailPresenter(this, this, this);
        historyDetailPresenter.getHistoryDetailBooking(bookingId);

        swipeRefreshHistoryDetail.setColorSchemeResources(R.color.orange);
        swipeRefreshHistoryDetail.setOnRefreshListener(() -> {
            layoutScrollHistoryDetail.setVisibility(android.view.View.VISIBLE);
            layoutNoInternet.setVisibility(android.view.View.GONE);
            historyDetailPresenter.getHistoryDetailBooking(bookingId);
            swipeRefreshHistoryDetail.setRefreshing(false);
        });
    }


    private void showCancelBookingDialog(){
        final MufitDialogTwoButtons dialog = new MufitDialogTwoButtons(this,
                getString(R.string.history_detail_cancel_booking_title),
                R.layout.dialog_cancel_booking);

        dialog.getBtnPositive().setOnClickListener(v1 -> {

            android.view.View v = dialog.getBody();
            final EditText etCancelBooking = v.findViewById(R.id.cancel_booking_description);
            final TextView textErrorMessage = v.findViewById(R.id.cancel_booking_error);
            String description = etCancelBooking.getText().toString();
            if(description.length() > 20){
                textErrorMessage.setVisibility(android.view.View.INVISIBLE);
                dialog.dismiss();
                historyDetailPresenter.cancelBooking(description, bookingId);
            } else {
                textErrorMessage.setVisibility(android.view.View.VISIBLE);
                textErrorMessage.startAnimation(AnimationUtils.loadAnimation(HistoryDetailActivity.this,R.anim.shake));
                textErrorMessage.requestFocus();
            }
        });

        dialog.show();
    }

    @OnClick(R.id.btn_CancelFromDetail)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_CancelFromDetail:
                showCancelBookingDialog();
                break;
        }
    }

    @Override
    public void showLoading() {
        progressBarLoading.setVisibility(android.view.View.VISIBLE);
        layoutScrollHistoryDetail.setVisibility(android.view.View.GONE);
        hideCancelBookingButton();
    }

    @Override
    public void dismissLoading() {
        progressBarLoading.setVisibility(android.view.View.GONE);
        layoutScrollHistoryDetail.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showNoInternetError() {
        dismissLoading();
        layoutScrollHistoryDetail.setVisibility(android.view.View.GONE);
        btnCancelBooking.setVisibility(android.view.View.GONE);
        layoutNoInternet.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void showValidationEmpty() {
        dismissLoading();
        dismissKeyboard();
        showGenericError(getString(R.string.cancel_booking_reason_empty));

    }

    @Override
    public void cancelBookingSuccess() {
        dismissLoading();
        dismissKeyboard();
        MufitDialogOneButtonWithText dialog = new MufitDialogOneButtonWithText(this,
                getString(R.string.dialog_success),
                getString(R.string.cancel_booking_success));
        dialog.getButton().setOnClickListener(l -> {
            Intent i = new Intent();
            i.putExtra(RootActivity.EXTRA_REFRESH_FRAGMENT, RootActivity.REFRESH_HISTORY);
            setResult(RESULT_OK, i);
            finishActivity();
        });
        dialog.show();
    }

    @Override
    public void showTrainerInfo(String imageUrl, String coverUrl, String trainerName) {

        GlideApp.with(this)
                .load(imageUrl)
                .centerCrop()
                .placeholder(R.color.image_preview_bg_color)
                .error(R.color.image_preview_bg_color)
                .into(imageTrainerDetail);

        GlideApp.with(this)
                .load(coverUrl)
                .centerCrop()
                .placeholder(R.color.image_preview_bg_color)
                .error(R.color.image_preview_bg_color)
                .into(imageHeaderDetail);

        textTrainerDetailName.setText(trainerName);
    }

    @Override
    public void showTrainingDetail(String address, String trainingDate, String trainingTime) {
        textAddress.setText(address);
        textDate.setText(trainingDate);
        textSessionTime.setText(trainingTime);
    }

    @Override
    public void showBookingStatus(String statusText) {
        textStatus.setText(statusText);
    }

    @Override
    public void showPaymentDetail(String voucherCode, String discountValue, String serviceFee, String totalPrice) {
        textVoucherCode.setText(voucherCode);
        textPriceDiscount.setText(discountValue);
        textPriceServiceFee.setText(serviceFee);
        textPriceTotal.setText(totalPrice);
    }

    @Override
    public void showCancelBookingButton() {
        layoutBtnCancel.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideCancelBookingButton() {
        layoutBtnCancel.setVisibility(android.view.View.GONE);
    }

    @Override
    public void showRatingAndReview(String rating, String review) {
        textRatingDetail.setText(rating);
        //TODO: include customer review
    }

    @Override
    public void showPurchasedSpecialities(List<BookingSpecialityModel> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistoryDetail.setLayoutManager(layoutManager);
        rvHistoryDetail.setNestedScrollingEnabled(false);

        HistoryDetailAdapter adapter = new HistoryDetailAdapter(this, list);
        rvHistoryDetail.setAdapter(adapter);
    }
}



//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_GALLERY) {
//            if (resultCode == Activity.RESULT_OK) {
//                Uri selectedImage = data.getData();
//                try {
//                    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
//                    Bitmap bitmapImage = BitmapFactory.decodeStream(imageStream);
//                    showPopupPayment(bitmapImage, selectedImage);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    public void showPopupPayment(Bitmap bitmap, final Uri uri) {
//        ImageView imageReceipt;
//        final EditText etPaymentDesc;
//        Button btnUpload, btnCancel;
//
//        dialogPaymentUpload.setContentView(R.layout.legacy_layout_popup_payment);
//
//        imageReceipt = dialogPaymentUpload.findViewById(R.id.image_receipt);
//        etPaymentDesc = dialogPaymentUpload.findViewById(R.id.text_paymentDesc);
//        btnUpload = dialogPaymentUpload.findViewById(R.id.btn_uploadPayment);
//        btnCancel = dialogPaymentUpload.findViewById(R.id.btn_cancelPayment);
//
//        etPaymentDesc.setTypeface(getTfRegular());
//        btnUpload.setTypeface(getTfMedium());
//        btnCancel.setTypeface(getTfMedium());
//        imageReceipt.setImageBitmap(bitmap);
//
//        btnUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View View) {
//                String filePath = getPath(uri);
//                String file_extension = filePath.substring(filePath.lastIndexOf(".") + 1);
//
//                if (file_extension.equals("img") || file_extension.equals("jpg") || file_extension.equals("jpeg")
//                        || file_extension.equals("png")) {
//                    File file = new File(filePath);
//
//                    try {
//                        file = new Compressor(getContext()).compressToFile(file);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
//                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
//                    historyDetailPresenter.uploadImageFromDetail(apiService, body, "trainer");
//                    paymentDescTemp = etPaymentDesc.getText().toString();
//                } else {
//                    simpleDialog(getResources().getString(R.string.upload_failed),
//                            getResources().getString(R.string.format_upload),
//                            false, false, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            });
//                }
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View View) {
//                dialogPaymentUpload.dismiss();
//            }
//        });
//
//
//        Window window = dialogPaymentUpload.getWindow();
//        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        dialogPaymentUpload.show();
//    }

//    public void accessGallery() {
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        startActivityForResult(intent, REQUEST_GALLERY);
//    }
//
//    public String getPath(Uri uri) {
//        String projection[] = {MediaStore.MediaColumns.DATA};
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }

//    @Override
//    public void uploadImageSuccess(String url) {
//        historyDetailPresenter.uploadPaymentFromDetail(apiService,
//                getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN)
//                , idBooking, url, version, paymentDescTemp);
//    }

//    @Override
//    public void uploadPaymentSuccess() {
//        dismissLoading();
////        dialogPaymentUpload.dismiss();
////        simpleDialog(getResources().getString(R.string.success),
////                getResources().getString(R.string.upload_success), false, false,
////                new DialogInterface.OnClickListener() {
////                    @Override
////                    public void onClick(DialogInterface dialogInterface, int i) {
////                        dialogInterface.dismiss();
////                        startActivity(new Intent(getContext(), HistoryBookingActivity.class));
////                    }
////                });
//    }