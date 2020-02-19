package com.nostratech.mufit.consumer.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.search_trainer.ListTrainerConstants;
import com.nostratech.mufit.consumer.search_trainer.SearchTrainerActivity;
import com.nostratech.mufit.consumer.model.category.CategoryResponseModel;
import com.nostratech.mufit.consumer.service.ApiService;
import com.nostratech.mufit.consumer.utils.date.DateUtils;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;
import com.tsongkha.spinnerdatepicker.DatePickerDialog;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.utils.TypefaceHelper;

//TODO: Move Logic into Presenter
public class SubCategoriesActivity extends MyToolbarBackActivity implements SubCategoriesContract.View,
        DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_CATEGORY_MODEL = "categoryModel";

    @BindView(R.id.headerPhotos)
    ImageView headerPhotos;
    @BindView(R.id.text_categoryName)
    TextView textCategoryName;
    @BindView(R.id.text_categoryDescription)
    TextView textCategoryDescription;

    @BindView(R.id.button_search_trainer)
    Button buttonSearchTrainer;
    @BindView(R.id.layout_search_date)
    ConstraintLayout layoutSeachDate;
    @BindView(R.id.rv_Categories)
    RecyclerView rvCategorySpeciality;
    @BindView(R.id.etCategoriesDateTime)
    EditText etCategoriesDateTime;
    @BindView(R.id.btnManCategories)
    ToggleButton btnManCategories;
    @BindView(R.id.btnWomanCategories)
    ToggleButton btnWomanCategories;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    LinearLayoutManager linearLayoutManager;
    SubCategorySpecialityAdapter homeCategoriesListAdapter;
//    SubCategoriesContract.Presenter subCategoriesPesenter;
    ApiService apiService;

    private int day = 0;
    private int month = 0;
    private int year = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        ButterKnife.bind(this);
        initToolbar(toolbar);

//        subCategoriesPesenter = new SubCategoriesPresenter(this, this, getContext());

//        subCategoriesPesenter.getListSpeciality(apiService, getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));

        Typeface truenoRegular = TypefaceHelper.get(TypefaceHelper.TRUENO_REGULAR, this);
        btnManCategories.setTypeface(truenoRegular);
        btnWomanCategories.setTypeface(truenoRegular);

        initInternalCalendar();
        configureRvSpeciality();
        retrieveIntentExtras();

    }

    private void initInternalCalendar() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void configureRvSpeciality() {
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvCategorySpeciality.setLayoutManager(linearLayoutManager);
        rvCategorySpeciality.setNestedScrollingEnabled(false);
    }

    private void retrieveIntentExtras() {
        CategoryResponseModel model = (CategoryResponseModel) getIntent().getSerializableExtra(EXTRA_CATEGORY_MODEL);
        GlideApp.with(this)
                .load(model.getCategoryImageUrl())
                .centerCrop()
                .into(headerPhotos);

        textCategoryName.setText(model.getCategoryName());
        textCategoryDescription.setText(model.getDescription());

        homeCategoriesListAdapter = new SubCategorySpecialityAdapter(model.getListSpeciality());
        rvCategorySpeciality.setAdapter(homeCategoriesListAdapter);
    }

//    @OnClick()
//    public void onViewClicked(View View) {
//        switch (View.getId()) {
//        }
//    }

    @Override
    public void showLoading() {
        showProgressDialog(this);
    }

    @Override
    public void dismissLoading() {
        dismissProgressDialog();
    }

    @Override
    public void showNoInternetError() {
        dismissLoading();
        showGenericError(getString(R.string.no_internet));
        //TODO: NO internet error
    }

    @OnClick({R.id.etCategoriesDateTime, R.id.button_search_trainer})
    public void onClick(android.view.View view) {
        switch (view.getId()) {
            case R.id.etCategoriesDateTime:
                showPopupDate();
                break;
            case R.id.button_search_trainer:
                String genderFilter;
                if (btnManCategories.isChecked() && btnWomanCategories.isChecked()) {
                    btnManCategories.setChecked(false);
                    btnWomanCategories.setChecked(false);
                    genderFilter = null;
                } else if (btnManCategories.isChecked()) {
                    genderFilter = "M";
                } else if (btnWomanCategories.isChecked()) {
                    genderFilter = "F";
                } else {
                    genderFilter = null;
                }

                Bundle data = new Bundle();
                data.putString(ListTrainerConstants.EXTRA_SPECIALITY_FILTER, homeCategoriesListAdapter.getSelectedSpeciality());
                data.putString(ListTrainerConstants.EXTRA_GENDER_FILTER, genderFilter);

                long date = isDateSet()
                        ? DateUtils.getEpochFromDayMonthYear(day, month, year)
                        : Calendar.getInstance().getTimeInMillis();

                data.putLong(ListTrainerConstants.EXTRA_SELECTED_DATE, date);

                Intent intent = new Intent(this, SearchTrainerActivity.class);
                intent.putExtras(data);
                startActivityForResult(intent, 1, data);
                break;
        }
    }

    private void showPopupDate() {

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        new SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback(this)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(year, month, day)
                .maxDate(currentYear + 1, currentMonth, currentDay)
                .minDate(currentYear, currentMonth, currentDay)
                .build()
                .show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Receive Date chosen on the next page
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_DATE);
                day = data.getIntExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_DAY, 0);
                month = data.getIntExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_MONTH, 0);
                year = data.getIntExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_YEAR, 0);

                // set text View with string
                etCategoriesDateTime.setText(result);
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            //Write your code if there's no result
        }
    }

    private void setDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);

        cal.getTimeInMillis();

        Date date = new Date(cal.getTimeInMillis());

        DateFormat dateFormat2 = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
        String dateFormatted2 = dateFormat2.format(date);
        etCategoriesDateTime.setText(dateFormatted2);
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;
        setDate();
    }

    private boolean isDateSet() {
        return day != 0 && month != 0 && year != 0;
    }

}
