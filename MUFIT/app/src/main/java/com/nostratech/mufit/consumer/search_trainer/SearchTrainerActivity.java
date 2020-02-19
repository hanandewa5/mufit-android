package com.nostratech.mufit.consumer.search_trainer;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.booking_package.BookingPackageActivity;
import com.nostratech.mufit.consumer.detailtrainer.DetailTrainerActivity;
import com.nostratech.mufit.consumer.home.SubCategoriesActivity;
import com.nostratech.mufit.consumer.login.LoginActivity;
import com.nostratech.mufit.consumer.model.SearchTrainerModel;
import com.nostratech.mufit.consumer.model.home.HomeSpecialityListModel;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.FontUtils;
import com.nostratech.mufit.consumer.utils.GridSpacingItemDecoration;
import com.nostratech.mufit.consumer.utils.InfiniteScrollListener;
import com.nostratech.mufit.consumer.utils.ShowcaseDismissedListener;
import com.nostratech.mufit.consumer.utils.TutorialBuilder;
import com.nostratech.mufit.consumer.utils.TutorialManager;
import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.utils.TypefaceHelper;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class SearchTrainerActivity extends MyToolbarBackActivity implements
        SearchTrainerContract.View,
        SearchTrainerAdapter.TrainerHolderOnClickListener,
        com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener,
        InfiniteScrollListener.OnLoadMoreListener {

    @BindView(R.id.toolbarListTrainer)
    Toolbar toolbarListTrainer;
    @BindView(R.id.rvListTrainer)
    RecyclerView rvListTrainer;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.etKeywords)
    EditText etTrainerName;
    @BindView(R.id.etDateTime)
    EditText etDateTime;
    @BindView(R.id.btnMan)
    ToggleButton btnMan;
    @BindView(R.id.btnWoman)
    ToggleButton btnWoman;
    @BindView(R.id.text_filter)
    TextView textFilter;
    @BindView(R.id.text_Gender)
    TextView textGender;
    @BindView(R.id.text_Categories)
    TextView textCategories;
    @BindView(R.id.layoutToolbar)
    LinearLayout layoutToolbar;

    @BindView(R.id.view)
    android.view.View view;

    @BindView(R.id.layout_filter_trainer)
    ConstraintLayout layoutFilterTrainer;
    @BindView(R.id.layout_filter_date)
    ConstraintLayout layoutFilterDate;
    @BindView(R.id.rv_speciality)
    RecyclerView rvSpeciality;
    @BindView(R.id.swipeRefresh_ListTrainer)
    SwipeRefreshLayout swipeRefreshListTrainer;
    @BindView(R.id.text_noInternet)
    TextView textNoInternet;
    @BindView(R.id.layout_noInternet)
    LinearLayout layoutNoInternet;
    @BindView(R.id.progressBar_loading)
    ProgressBar progressBarLoading;
    @BindView(R.id.layout_empty)
    LinearLayout layoutEmpty;
    //TODO : fristy - add my voucher button
    @BindView(R.id.button_my_voucher)
    Button buttonMyVoucher;
    @BindView(R.id.button_cancel_voucher_use)
    Button buttonCancelVoucherUse;
    @BindView(R.id.layout_myVoucher)
    RelativeLayout layoutMyVoucher;
    @BindView(R.id.text_myVoucherName)
    TextView textMyVoucherPackageName;
    @BindView(R.id.layout_ListTrainer)
    RelativeLayout layoutListTrainer;

    @BindView(R.id.layout_gender)
    LinearLayout layoutGender;
    @BindView(R.id.btnApply)
    Button btnApply;

    @BindView(R.id.layout_current_filter)
    ViewGroup layoutFilter;

    @BindView(R.id.layout_filter_gender)
    ViewGroup layoutFilterGender;
    @BindView(R.id.text_filter_gender)
    TextView textFilterGender;
    @BindView(R.id.button_cancel_filter_gender)
    ImageButton buttonCancelFilterGender;

    @BindView(R.id.layout_filter_speciality)
    ViewGroup layoutFilterSpeciality;
    @BindView(R.id.text_filter_speciality)
    TextView textFilterSpeciality;
    @BindView(R.id.button_cancel_filter_speciality)
    ImageButton buttonCancelFilterSpeciality;

    @BindView(R.id.button_cancel_filter_name)
    ImageButton buttonCancelFilterName;
    @BindView(R.id.button_cancel_filter_date)
    ImageButton buttonCancelFilterDate;

    private InfiniteScrollListener infiniteScrollListener;
    private SearchTrainerAdapter listTrainerAdapter;
    private SearchTrainerContract.Presenter presenter;

    FilterSpecialityAdapter filterSpecialityAdapter;

    private TutorialManager tutorialManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trainer);
        ButterKnife.bind(this);

        tutorialManager = new TutorialManager(this);
        presenter = new SearchTrainerPresenter(this, this, this);

        //set toolbar
        initToolbar(toolbarListTrainer);
        hideTitle();

        configureRvFilterSpeciality();
        configureRvTrainerList();
        configureSwipeRefreshLayout();
        applyManWomanFilterButtonTypeface();
        configureEditTextFilterNameAndDate();

        initDrawer();

        initializeData();

        checkVoucher();
    }

    private void configureEditTextFilterNameAndDate() {
        etTrainerName.setOnEditorActionListener((v, actionId, event) -> {
            v.clearFocus();
            dismissKeyboard();
            return true;
        });

        etTrainerName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    buttonCancelFilterName.setVisibility(View.VISIBLE);
                } else {
                    buttonCancelFilterName.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //afterTextChanged is called when user presses the actionIME button on their keyboard
                presenter.onFilterNameChanged(s.toString());
            }
        });
        etDateTime.addTextChangedListener(new DateTimeWatcher(buttonCancelFilterDate));

        checkVoucher();

        setupStealFocusTouchListeners(layoutToolbar, etTrainerName);
    }

    private void initDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull android.view.View view, float v) {

            }

            @Override
            public void onDrawerOpened(@NonNull android.view.View view) {

            }

            @Override
            public void onDrawerClosed(@NonNull android.view.View view) {

            }

            @Override
            public void onDrawerStateChanged(int i) {

            }
        });

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private void configureSwipeRefreshLayout() {
        swipeRefreshListTrainer.setColorSchemeResources(R.color.orange);
        swipeRefreshListTrainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshResults();
                swipeRefreshListTrainer.setRefreshing(false);
            }
        });
    }

    private void initializeData() {
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        presenter.initializeData(data);
    }

    private void configureRvFilterSpeciality() {

        int numberOfColumns = 2;
        rvSpeciality.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        rvSpeciality.setNestedScrollingEnabled(false);

        //Add spacing between items in grid layout
        RecyclerView.ItemDecoration itemSpacing = new GridSpacingItemDecoration(numberOfColumns,
                (int) getResources().getDimension(R.dimen._10dp), false);
        rvSpeciality.addItemDecoration(itemSpacing);
        filterSpecialityAdapter = new FilterSpecialityAdapter();
    }

    private void configureRvTrainerList() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchTrainerActivity.this);
        infiniteScrollListener = new InfiniteScrollListener(layoutManager, this);
        rvListTrainer.setLayoutManager(layoutManager);
        listTrainerAdapter = new SearchTrainerAdapter(this);
        rvListTrainer.setAdapter(listTrainerAdapter);
        rvListTrainer.addOnScrollListener(infiniteScrollListener);
    }

    //Typeface cannot be set from XML for unknown reasons
    private void applyManWomanFilterButtonTypeface() {
        Typeface tfRegular = TypefaceHelper.get(TypefaceHelper.TRUENO_REGULAR, this);
        btnMan.setTypeface(tfRegular);
        btnWoman.setTypeface(tfRegular);
    }

    private void checkVoucher() {
        Bundle data = getIntent().getExtras();
        //Get voucher code passed from MyVoucherActivity
        if (data != null) {

        }
    }

    @Override
    public void showNoInternetError() {
        dismissLoading();
        rvListTrainer.setVisibility(android.view.View.GONE);
        layoutNoInternet.setVisibility(android.view.View.VISIBLE);
    }

    /**
     * runOnUiThread is required when called from inside onFilterNameChanged
     * Previously onFilterNameChanged is located in Activity, and therefore was running on the UI Thread
     */
    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            progressBarLoading.setVisibility(android.view.View.VISIBLE);
            rvListTrainer.setVisibility(android.view.View.GONE);
            layoutNoInternet.setVisibility(android.view.View.GONE);
        });
    }

    @Override
    public void dismissLoading() {
        runOnUiThread(() -> {
            progressBarLoading.setVisibility(android.view.View.GONE);
            rvListTrainer.setVisibility(android.view.View.VISIBLE);
            layoutNoInternet.setVisibility(android.view.View.GONE);
        });
    }

    @Override
    public void showListTrainer(List<SearchTrainerModel> searchTrainerModels, boolean endOfPage) {
        dismissLoading();

        if (endOfPage) infiniteScrollListener.addEndOfRequests();

        if (searchTrainerModels.size() > 0) {
            listTrainerAdapter.appendList(searchTrainerModels);
            layoutEmpty.setVisibility(android.view.View.GONE);
        } else {
            listTrainerAdapter.clearList();
        }

        if (listTrainerAdapter.getItemCount() == 0) {
            layoutEmpty.setVisibility(android.view.View.VISIBLE);
        }
    }

    @Override
    public void clearSearchResults() {
        runOnUiThread(() -> {
            listTrainerAdapter.clearList();
            infiniteScrollListener.reset();
        });
    }

    @Override
    public void showFilterSpecialityList(final List<HomeSpecialityListModel> homeSpecialityListModels) {
        dismissLoading();
        filterSpecialityAdapter.insertData(homeSpecialityListModels);
        rvSpeciality.setAdapter(filterSpecialityAdapter);

    }

    @OnClick({R.id.etDateTime, R.id.button_my_voucher, R.id.btnApply,
            R.id.button_cancel_voucher_use, R.id.button_cancel_filter_speciality,
            R.id.button_cancel_filter_gender, R.id.button_cancel_filter_date, R.id.button_cancel_filter_name})
    public void onViewClicked(android.view.View view) {
        dismissKeyboard();
        switch (view.getId()) {
            case R.id.etDateTime:
                showPopupDate();
                break;
            case R.id.button_my_voucher:
                //Buka halaman voucher (?)
                break;
            case R.id.button_cancel_voucher_use:
                presenter.onVoucherRemoved();
                break;
            case R.id.btnApply:
                applyFilters();
                break;
            case R.id.button_cancel_filter_date:
                presenter.onFilterDateReset();
                break;
            case R.id.button_cancel_filter_name:
                etTrainerName.setText("");
                break;
            case R.id.button_cancel_filter_gender:
                presenter.onFilterGenderReset();
                break;
            case R.id.button_cancel_filter_speciality:
                presenter.onFilterSpecialityReset();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.END);
    }

    private void applyFilters() {
        HomeSpecialityListModel model = filterSpecialityAdapter.getSelectedItem();
        String speciality = model == null
                ? null
                : model.getName();
        presenter.onFilterGenderSpecialityChanged(btnMan.isChecked(), btnWoman.isChecked(), speciality);
    }

    //Popup date time filter
    private void showPopupDate() {

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        int[] dateArray = presenter.getLastSelectedDate();
        new SpinnerDatePickerDialogBuilder()
                .context(this)
                .callback(this)
                .showTitle(true)
                .showDaySpinner(true)
                .defaultDate(dateArray[0], dateArray[1], dateArray[2])
                .maxDate(currentYear + 1, currentMonth, currentDay)
                .minDate(currentYear, currentMonth, currentDay)
                .build()
                .show();
    }


    @Override
    public void onBackPressed() {
        //check activity
        if (getCallingActivity() != null) {
            String callingClass = getCallingActivity().getClassName();
            if (callingClass.equals(SubCategoriesActivity.class.getName())) {
                String dateTime = etDateTime.getText().toString();
                int[] dateArray = presenter.getLastSelectedDate();
                Intent i = new Intent();
                i.putExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_DATE, dateTime);
                i.putExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_DAY, dateArray[0]);
                i.putExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_MONTH, dateArray[1]);
                i.putExtra(ListTrainerConstants.EXTRA_RETURN_SELECTED_YEAR, dateArray[2]);
                setResult(RESULT_OK, i);
            }
        }

        finishActivity();
    }

    //Called by adapter to open DetailTrainerActivity
    @Override
    public void openDetailTrainer(String trainerId) {
        presenter.requestNavigationToDetailTrainer(trainerId);
    }

    @Override
    public void onPackageClicked(SearchTrainerModel model) {
        presenter.requestNavigationToPackage(model.getId(), model.getName());
    }

    @Override
    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        presenter.onFilterDateChanged(year, monthOfYear, dayOfMonth);
    }

    /**
     * If voucher is cancelled result will be RESULT_VOUCHER_CANCELLED,
     * close activity immediately so user will be moved back to MyVoucherActivity
     * <p>
     * Else if voucher is not cancelled, do nothing
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Constants.RESULT_VOUCHER_CANCELLED) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_list_trainer, menu);

        //Unable to get view reference to filter icon in OnCreate
        //Therefore, the tutorial is initialized here instead of OnCreate, to get view reference to filter icon
        new Handler().post(() -> {
            final android.view.View menuItemView = findViewById(R.id.toolbarListTrainerItem);

            if (!tutorialManager.isTutorialSearchTrainerFinished()) {
                showTutorialAboutUsingFilters(menuItemView);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Open drawer when filter icon is tapped
        if (item.getItemId() == R.id.toolbarListTrainerItem) {
            dismissKeyboard();
            drawerLayout.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void dismissInfiniteScrollLoading() {
        listTrainerAdapter.stopLoading();
        infiniteScrollListener.setLoaded();
    }

    @Override
    public void showDateText(String date) {
        etDateTime.setText(date);
    }

    @Override
    public void showActiveVoucher(String voucherCode) {
        layoutMyVoucher.setVisibility(View.VISIBLE);
        textMyVoucherPackageName.setText(voucherCode);
    }

    @Override
    public void removeActiveVoucher() {
        layoutMyVoucher.setVisibility(View.GONE);
    }

    @Override
    public void toggleButtonWoman(boolean checked) {
        btnWoman.setChecked(checked);
    }

    @Override
    public void toggleButtonMan(boolean checked) {
        btnMan.setChecked(checked);
    }

    @Override
    public void hideFilters() {
        layoutFilter.setVisibility(View.GONE);
    }

    @Override
    public void showFilters() {
        layoutFilter.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPredeterminedFilter(String speciality) {
        filterSpecialityAdapter.selectSpeciality(speciality);
    }

    @Override
    public void updateFilterSpeciality(String speciality) {
        layoutFilterSpeciality.setVisibility(android.view.View.VISIBLE);
        textFilterSpeciality.setText(speciality);
    }

    @Override
    public void removeFilterSpeciality() {
        layoutFilterSpeciality.setVisibility(View.GONE);
        filterSpecialityAdapter.removeSelection();
    }

    @Override
    public void updateFilterGender(String gender) {
        layoutFilterGender.setVisibility(android.view.View.VISIBLE);
        switch (gender) {
            case "M":
                textFilterGender.setText(getString(R.string.man));
                break;
            case "F":
                textFilterGender.setText(getString(R.string.woman));
                break;
        }
    }

    @Override
    public void removeFilterGender() {
        layoutFilterGender.setVisibility(View.GONE);
        btnMan.setChecked(false);
        btnWoman.setChecked(false);
    }

    @Override
    public void removeFilterDate() {
        etDateTime.setText("");
    }

    @Override
    public void navigateToDetailTrainer(String trainerId, String voucherCode, String voucherDiscountType, int discountValue) {
        Intent intent = new Intent(this, DetailTrainerActivity.class);

        //Add trainerId for DetailTrainer to load data
        intent.putExtra(Constants.IntentExtras.TRAINER_ID, trainerId);

        //Voucher related variables
        intent.putExtra(Constants.IntentExtras.VOUCHER_TYPE, getIntent().getSerializableExtra(Constants.IntentExtras.VOUCHER_TYPE));
        intent.putExtra(Constants.IntentExtras.VOUCHER_CODE, voucherCode);
        intent.putExtra(Constants.Voucher.INTENT_EXTRA_VOUCHER_DISCOUNT_TYPE, voucherDiscountType);
        intent.putExtra(Constants.IntentExtras.VOUCHER_DISCOUNT_VALUE, discountValue);

        //start activity for result to catch if Voucher is cancelled in DetailTrainerActivity
        startActivityForResult(intent, 1);
    }

    @Override
    public void navigateToPackage(String trainerId, String trainerName) {
        Intent intent = new Intent(this, BookingPackageActivity.class);
        intent.putExtra(BookingPackageActivity.EXTRA_TRAINER_ID, trainerId);
        intent.putExtra(Constants.IntentExtras.TRAINER_NAME, trainerName);
        showActivity(intent);
    }

    @Override
    public void navigateToLogin(int flag) {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra(LoginActivity.FLAG, LoginActivity.OPEN_LIST_TRAINER);
        showActivity(i);
    }

    @Override
    public void showInfiniteScrollLoading() {
        rvListTrainer.post(() -> listTrainerAdapter.startLoading());
    }

    @Override
    public void onLoadMore() {
        presenter.runSearch();
    }


    public void showTutorialAboutUsingFilters(View filterIcon) {

        TutorialBuilder tutorialBuilder = new TutorialBuilder(this, FontUtils.getTruenoMedium(this));

        //Filter trainer based on name
        tutorialBuilder.addSequenceItemRectangleShape(layoutFilterTrainer,
                getString(R.string.next),
                getString(R.string.filter_trainer_showcase),
                null,
                false);

        //Filter trainer based on training date
        tutorialBuilder.addSequenceItemRectangleShape(layoutFilterDate,
                getString(R.string.next),
                getString(R.string.filter_date_showcase),
                null,
                false);

        //Open advanced filter menu
        tutorialBuilder.addSequenceItem(filterIcon,
                getString(R.string.next),
                getString(R.string.toolbar_filter_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        showTutorialTrainerRecyclerView();
                    }
                });

        tutorialBuilder.start();
    }

    //Made into a separate method to ensure RecyclerView is already laid out at this moment of time
    //Hence, the viewholder can be found
    public void showTutorialTrainerRecyclerView() {

        Typeface tfMedium = TypefaceHelper.get(TypefaceHelper.TRUENO_MEDIUM, this);
        TutorialBuilder tutorialRvTrainerBuilder = new TutorialBuilder(this, tfMedium);

        RecyclerView.ViewHolder holder = rvListTrainer.findViewHolderForAdapterPosition(0);

        //No need to show tutorial if no trainer found
        if (holder == null) {
            tutorialManager.finishTutorialSearchTrainer();
            return;
        }

        tutorialRvTrainerBuilder.addSequenceItemRectangleShape(holder.itemView.findViewById(R.id.searchTrainerAdapter_root),
                getString(R.string.next),
                getString(R.string.detai_trainer_showcase),
                null,
                true);

        tutorialRvTrainerBuilder.addSequenceItem(holder.itemView.findViewById(R.id.searchTrainerAdapter_btnBookSession),
                getString(R.string.next),
                getString(R.string.sesion_trainer_showcase),
                null);

        tutorialRvTrainerBuilder.addSequenceItem(holder.itemView.findViewById(R.id.searchTrainerAdapter_btnBookPackage),
                getString(R.string.next),
                getString(R.string.package_trainer_showcase),
                new ShowcaseDismissedListener() {
                    @Override
                    public void onShowcaseDismissed(MaterialShowcaseView showcaseView) {
                        tutorialManager.finishTutorialSearchTrainer();
                    }
                });


        tutorialRvTrainerBuilder.start();
    }

    //Dismiss keyboard when user presses anywhere outside keyboards
    private void setupStealFocusTouchListeners(android.view.View root, android.view.View... views) {
        root.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(android.view.View v, boolean hasFocus) {
                if (hasFocus) {
                    dismissKeyboard();
                }
            }
        });
        for (android.view.View v : views) {
            v.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    dismissKeyboard();
                    root.requestFocus();
                }
            });
        }
    }
}

