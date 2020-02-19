//package com.nostratech.mufit.consumer.list_trainer;
//
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.IntentFilter;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.content.LocalBroadcastManager;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.view.Gravity;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.ToggleButton;
//
//import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.base.BaseActivity;
//import com.nostratech.mufit.consumer.base.BaseViewInterface;
//import com.nostratech.mufit.consumer.detailtrainer.DetailTrainerActivity;
//import com.nostratech.mufit.consumer.home.SubCategoriesActivity;
//import com.nostratech.mufit.consumer.login.LoginActivity;
//import com.nostratech.mufit.consumer.model.ListPackageModelOld;
//import com.nostratech.mufit.consumer.model.ListTrainerAdvancedModel;
//import com.nostratech.mufit.consumer.model.home.HomeSpecialityListModel;
//import com.nostratech.mufit.consumer.service.ApiClient;
//import com.nostratech.mufit.consumer.service.ApiService;
//import com.nostratech.mufit.consumer.utils.Constants;
//import com.nostratech.mufit.consumer.utils.GridSpacingItemDecoration;
//import com.nostratech.mufit.consumer.legacy.SessionManager;
//import com.tsongkha.spinnerdatepicker.SpinnerDatePickerDialogBuilder;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//
//public class SearchTrainerActivity extends BaseActivity implements BaseViewInterface,
//        SearchTrainerContract.View,
//        SearchTrainerAdapter.TrainerHolderOnClickListener, com.tsongkha.spinnerdatepicker.DatePickerDialog.OnDateSetListener {
//
//    @BindView(R.id.toolbarListTrainer)
//    Toolbar toolbarListTrainer;
//    @BindView(R.id.rvListTrainer)
//    RecyclerView rvListTrainer;
//    //    @BindView(R.id.bottomsheet)
////    BottomSheetLayout bottomsheet;
//    @BindView(R.id.drawerLayout)
//    DrawerLayout drawerLayout;
//    //    @BindView(R.id.navigation_View)
////    NavigationView navigationView;
//    @BindView(R.id.btnApply)
//    Button btnApply;
//    @BindView(R.id.etTrainerName)
//    EditText etTrainerName;
//    @BindView(R.id.etDateTime)
//    EditText etDateTime;
//    @BindView(R.id.btnMan)
//    ToggleButton btnMan;
//    @BindView(R.id.btnWoman)
//    ToggleButton btnWoman;
//    @BindView(R.id.text_filter)
//    TextView textFilter;
//    @BindView(R.id.text_Gender)
//    TextView textGender;
//    @BindView(R.id.text_Categories)
//    TextView textCategories;
//    //    @BindView(R.id.text_Empty)
////    TextView textEmpty;
////    @BindView(R.id.logo_mufit)
////    ImageView logoMufit;
//    @BindView(R.id.layoutToolbar)
//    LinearLayout layoutToolbar;
//    @BindView(R.id.navigation)
//    BottomNavigationViewEx navigation;
//    @BindView(R.id.rv_speciality)
//    RecyclerView rvSpeciality;
//    @BindView(R.id.swipeRefresh_ListTrainer)
//    SwipeRefreshLayout swipeRefreshListTrainer;
//    @BindView(R.id.text_noInternet)
//    TextView textNoInternet;
//    @BindView(R.id.layout_noInternet)
//    LinearLayout layoutNoInternet;
//    @BindView(R.id.progressBar_loading)
//    ProgressBar progressBarLoading;
//    @BindView(R.id.layout_empty)
//    LinearLayout layoutEmpty;
//    //TODO : fristy - add my voucher button
//    @BindView(R.id.button_my_voucher)
//    Button buttonMyVoucher;
//    @BindView(R.id.button_cancel_voucher_use)
//    Button buttonCancelVoucherUse;
//    @BindView(R.id.layout_myVoucher)
//    RelativeLayout layoutMyVoucher;
//    @BindView(R.id.text_myVoucherName)
//    TextView textMyVoucherPackageName;
//    @BindView(R.id.layout_ListTrainer)
//    RelativeLayout layoutListTrainer;
//
//    @BindView(R.id.layout_filter_gender)
//    ViewGroup layoutFilterGender;
//    @BindView(R.id.text_filter_gender)
//    TextView textFilterGender;
//    @BindView(R.id.button_cancel_filter_gender)
//    ImageButton buttonCancelFilterGender;
//
//    @BindView(R.id.layout_filter_speciality)
//    ViewGroup layoutFilterSpeciality;
//    @BindView(R.id.text_filter_speciality)
//    TextView textFilterSpeciality;
//    @BindView(R.id.button_cancel_filter_speciality)
//    ImageButton buttonCancelFilterSpeciality;
//
//    @BindView(R.id.layout_filter_title)
//    RelativeLayout layoutFilterTitle;
//
//    @BindView(R.id.button_cancel_filter_name)
//    ImageButton buttonCancelFilterName;
//    @BindView(R.id.button_cancel_filter_date)
//    ImageButton buttonCancelFilterDate;
//
//
//    //@BindView(R.id.text_Time)
//    //TextView textTime;
//    //@BindView(R.id.btn_Date)
//    //ToggleButton btnDate;
//
//
//    private SearchTrainerAdapter recyclerViewAdvancedAdapter;
//    private SearchTrainerContract.SearchTrainerPresenter listTrainerPresenter;
//    private ApiService apiService;
//    //    private Dialog dialogDatePicker, dialogTimePicker;
////    private DatePicker datePicker;
////    private TimePicker timePickerStart, timePickerEnd;
////    int yearSelected, monthSelected, daySelected;
////    private long date;
//    private String specialityTemp, tempGender = "";
//    private long tempCurrentDate;
//
//    TextView textTrainingSchedule;
//    Button btnApplyDate, btnApplyDateTime;
//    //    String hourSelectedStart, minuteSelectedStart, hourSelectedEnd, minuteSelectedEnd, day;
//    String dayOff;
//    StringBuilder sb, sbStart, sbEnd, sbDate;
//    FilterSpecialityAdapter filterSpecialityAdapter;
//    String specialityFromHome, search;
//
//    LinearLayoutManager layoutManager;
//    private int counterPage = 0;
//    private List<ListTrainerAdvancedModel> templistTrainerAdvancedModelsSearch, templistTrainerAdvancedModelsFiltered;
//    private boolean useFilter = false;
//
//    private String voucherCode;
//    private int discountValue;
//
//    private int day = 1;
//    private int month = 1;
//    private int year = 1970;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_trainer);
//        ButterKnife.bind(this);
//
//        Calendar calendar = Calendar.getInstance();
//        year = calendar.get(Calendar.YEAR);
//        month = calendar.get(Calendar.MONTH);
//        day = calendar.get(Calendar.DAY_OF_MONTH);
//
//        applyManWomanFilterButtonTypeface();
//        //set toolbar
//
//        configureToolbar(true, toolbarListTrainer);
////        toolbarListTrainer.setNavigationOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                onBackPressed();
////            }
////        });
//
////        if (getSupportActionBar() != null) {
////            getSupportActionBar().setDisplayShowTitleEnabled(false);
////            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        }
//
//        //set RecyclerView & Typeface for dialog
//        configureRvFilterSpeciality();
//
//
//        apiService = ApiClient.createService(ApiService.class);
//
//        listTrainerPresenter = new SearchTrainerPresenter(this, this, getContext(),
//                AndroidSchedulers.mainThread());
//
//        //get data
//
//        Intent intent = getIntent();
//        specialityFromHome = null;
//        search = intent.getStringExtra("keywords");
//
//        //set recyclerview for pagination
//
//        configureRvAdapter();
//
//        if (search != null) {
//            etTrainerName.setText(search);
//        }
//
//        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver,
//                new IntentFilter("pass_data"));
//
//        apiService = ApiClient.createService(ApiService.class);
//
//        listTrainerPresenter = new SearchTrainerPresenter(this, this, getContext(),
//                AndroidSchedulers.mainThread());
//
//        if (specialityFromHome == null) {
//            listTrainerPresenter.getAllTrainers(apiService,
//                    getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//        } else {
//            listTrainerPresenter.getListTrainerFiltered(apiService, getSessionManager().getUserDetails()
//                    .get(SessionManager.KEY_ACCESS_TOKEN), null, specialityFromHome, null, counterPage);
//        }
//
//        listTrainerPresenter.getAllSpeciality(apiService, getSessionManager().getUserDetails()
//                .get(SessionManager.KEY_ACCESS_TOKEN));
//
//        //TODO: fristy - get all package
//        listTrainerPresenter.getAllPackage(apiService,
//                getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//
//
//        //swipe to refresh data
//
//        swipeRefreshListTrainer.setColorSchemeResources(R.color.orange);
//        swipeRefreshListTrainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshListTrainer.setRefreshing(false);
//                if (etTrainerName.getText().toString().equals("")) {
//                    counterPage = 0;
//                    recyclerViewAdvancedAdapter.clearList();
//                    //endlessScrollListener.resetState();
//                    if (specialityFromHome == null) {
//                        listTrainerPresenter.getAllTrainers(apiService,
//                                getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//                    } else {
//                        listTrainerPresenter.getListTrainerFiltered(apiService, getSessionManager().getUserDetails()
//                                .get(SessionManager.KEY_ACCESS_TOKEN), null, specialityFromHome, null, counterPage);
//                    }
//                }
//            }
//        });
//
//        //set navigation drawer
//
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
////        dialogDatePicker = new Dialog(this);
////        dialogTimePicker = new Dialog(this);
//
//        //set Toggle Button
//
//        btnWoman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    btnWoman.setBackground(getResources().getDrawable(R.drawable.button_background_transparent_pressed));
//                    btnWoman.setTextColor(getResources().getColor(R.color.white100));
//                } else {
//                    btnWoman.setBackground(getResources().getDrawable(R.drawable.button_background_transparent));
//                    btnWoman.setTextColor(getResources().getColor(R.color.black54));
//                }
//            }
//        });
//
//        btnMan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b) {
//                    btnMan.setBackground(getResources().getDrawable(R.drawable.button_background_transparent_pressed));
//                    btnMan.setTextColor(getResources().getColor(R.color.white100));
//                } else {
//                    btnMan.setBackground(getResources().getDrawable(R.drawable.button_background_transparent));
//                    btnMan.setTextColor(getResources().getColor(R.color.black54));
//                }
//            }
//        });
//
//        //TODO: filter by current date
//        //btnDate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//        //    @Override
//        //    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//        //        if (b) {
//        //            btnDate.setBackground(getResources().getDrawable(R.drawable.button_background_transparent_pressed));
//        //            btnDate.setTextColor(getResources().getColor(R.color.white100));
//        //        } else {
//        //            btnDate.setBackground(getResources().getDrawable(R.drawable.button_background_transparent));
//        //            btnDate.setTextColor(getResources().getColor(R.color.black54));
//        //        }
//        //    }
//        //});
//
//        if (specialityFromHome != null) {
//            layoutFilterSpeciality.setVisibility(View.VISIBLE);
////            textFilterSpeciality.setVisibility(View.VISIBLE);
////            buttonCancelFilterSpeciality.setVisibility(View.VISIBLE);
//            textFilterSpeciality.setText(specialityFromHome);
//        }
//
//        buttonCancelFilterName.setVisibility(View.GONE);
//        buttonCancelFilterDate.setVisibility(View.GONE);
//
//        etTrainerName.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if (count > 0) {
//                    buttonCancelFilterName.setVisibility(View.VISIBLE);
//                } else {
//                    buttonCancelFilterName.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        etDateTime.addTextChangedListener(new TextWatcher() {
//
//            public void afterTextChanged(Editable s) {
//            }
//
//            public void beforeTextChanged(CharSequence s, int start,
//                                          int count, int after) {
//            }
//
//            public void onTextChanged(CharSequence s, int start,
//                                      int before, int count) {
//                if (count > 0) {
//                    buttonCancelFilterDate.setVisibility(View.VISIBLE);
//                } else {
//                    buttonCancelFilterDate.setVisibility(View.GONE);
//                }
//            }
//        });
//
//        checkVoucher();
//        showFilterFromSubCategories();
//    }
//
//    private void configureRvFilterSpeciality() {
//
//        int numberOfColumns = 2;
//        rvSpeciality.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
//        rvSpeciality.setNestedScrollingEnabled(false);
//
//        //Add spacing between items in grid layout
//        RecyclerView.ItemDecoration itemSpacing = new GridSpacingItemDecoration(numberOfColumns,
//                (int) getResources().getDimension(R.dimen._10dp), false);
//        rvSpeciality.addItemDecoration(itemSpacing);
//
//    }
//
//
//    //Typeface cannot be set from XML for unknown reasons
//    private void applyManWomanFilterButtonTypeface() {
//        btnMan.setTypeface(getTfRegular());
//        btnWoman.setTypeface(getTfRegular());
//    }
//
//    private void checkVoucher() {
//        Bundle data = getIntent().getExtras();
//        //Get voucher code passed from MyVoucherActivity
//        if (data != null) {
//            voucherCode = data.getString(Constants.INTENT_EXTRA_VOUCHER_CODE, null);
//            discountValue = data.getInt(Constants.INTENT_EXTRA_VOUCHER_DISCOUNT_VALUE, 0);
//
//            //If no voucher code was passed it will be null
//            //else, show the code
//            if (voucherCode != null) {
//                layoutMyVoucher.setVisibility(View.VISIBLE);
//                textMyVoucherPackageName.setText(voucherCode);
//            }
//        }
//    }
//
//    public BroadcastReceiver messageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            specialityTemp = intent.getStringExtra("speciality_list_trainer");
//        }
//    };
//
//    private void configureRvAdapter() {
//        layoutManager = new LinearLayoutManager(SearchTrainerActivity.this);
//        rvListTrainer.setLayoutManager(layoutManager);
//        if (specialityFromHome != null) {
//            //Log.d("FRISTY LOG", "DspecialityFromHome " + specialityFromHome);
//            if (specialityFromHome.equals(""))
//                recyclerViewAdvancedAdapter = new SearchTrainerAdapter(SearchTrainerActivity.this, getSessionManager(), this);
//            else
//                recyclerViewAdvancedAdapter = new SearchTrainerAdapter(SearchTrainerActivity.this, getSessionManager(), specialityFromHome, this);
//        } else if (specialityTemp != null) {
//            //Log.d("FRISTY LOG", "specialityTemp " + specialityTemp);
//            if (specialityTemp.equals(""))
//                recyclerViewAdvancedAdapter = new SearchTrainerAdapter(SearchTrainerActivity.this, getSessionManager(), this);
//            else
//                recyclerViewAdvancedAdapter = new SearchTrainerAdapter(SearchTrainerActivity.this, getSessionManager(), specialityTemp, this);
//        } else
//            recyclerViewAdvancedAdapter = new SearchTrainerAdapter(SearchTrainerActivity.this, getSessionManager(), this);
//        rvListTrainer.setAdapter(recyclerViewAdvancedAdapter);
//        /*endlessScrollListener = new EndlessScrollListener(layoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                if(etTrainerName.getText().toString().equals("")) //todo: fristy jika lagi search gak boleh load more
//                    //listTrainerPresenter.getListTrainerFiltered(apiService, getSessionManager().getUserDetails()
//                    //        .get(SessionManager.KEY_ACCESS_TOKEN), null, specialityFromHome, null, counterPage);
//                    listTrainerPresenter.getAllTrainers(apiService,
//                            getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//            }
//        };
//        rvListTrainer.addOnScrollListener(endlessScrollListener);*/
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater menuInflater = getMenuInflater();
//        menuInflater.inflate(R.menu.toolbar_list_trainer, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.toolbarListTrainerItem) {
//            hideKeyboard();
//            drawerLayout.openDrawer(Gravity.END);
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void noInternetError() {
//        dismissLoading();
//        rvListTrainer.setVisibility(View.GONE);
//        simpleDialog("", getResources().getString(R.string.no_internet), false, false,
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                        layoutNoInternet.setVisibility(View.VISIBLE);
//                    }
//                });
//    }
//
//    @Override
//    public void showError(String errorMessage) {
//        dismissLoading();
//        rvListTrainer.setVisibility(View.GONE);
//        simpleDialog("", errorMessage, false, false, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//                layoutNoInternet.setVisibility(View.VISIBLE);
//            }
//        });
//    }
//
//    @Override
//    public void showLoading() {
//        progressBarLoading.setVisibility(View.VISIBLE);
//        rvListTrainer.setVisibility(View.GONE);
//        layoutNoInternet.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void dismissLoading() {
//        progressBarLoading.setVisibility(View.GONE);
//        rvListTrainer.setVisibility(View.VISIBLE);
//        layoutNoInternet.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void hideKeyboard() {
//        hideKeyboard(getCurrentFocus());
//    }
//
//    @Override
//    public void initTypefaces() {
//    }
//
//    @Override
//    public void showLogDebug(String tag, String message) {
//    }
////
////    @Override
////    public void doShowListTrainer(List<ListTrainerAdvancedModel> listTrainerAdvancedModels) {
////        dismissLoading();
////        if (listTrainerAdvancedModels.size() > 0) {
////            layoutEmpty.setVisibility(View.GONE);
////            recyclerViewAdvancedAdapter.refresh(listTrainerAdvancedModels);
////        } else {
////            rvListTrainer.setVisibility(View.GONE);
////            layoutEmpty.setVisibility(View.VISIBLE);
////        }
////    }
//
//    @Override
//    public void showListTrainer(List<ListTrainerAdvancedModel> listTrainerAdvancedModels) {
//        dismissLoading();
//        if (listTrainerAdvancedModels.size() > 0) {
//            if (recyclerViewAdvancedAdapter.getItemCount() < ((counterPage + 1) * listTrainerPresenter.getLimit())) {
//                layoutEmpty.setVisibility(View.GONE);
//                recyclerViewAdvancedAdapter.updateList(listTrainerAdvancedModels);
//            }
//        }
//        if (recyclerViewAdvancedAdapter.getItemCount() == 0) {
//            layoutEmpty.setVisibility(View.VISIBLE);
//        }
//    }
//
//    @Override
//    public void doShowListTrainerBySpeciality(List<ListTrainerAdvancedModel> listTrainerAdvancedModels) {
//
//    }
//
//    @Override
//    public void doShowListTrainerByGender(List<ListTrainerAdvancedModel> listTrainerAdvancedModels) {
//
//    }
//
//    @Override
//    public void showFilterSpecialityList(final List<HomeSpecialityListModel> homeSpecialityListModels) {
//        dismissLoading();
//        filterSpecialityAdapter = new FilterSpecialityAdapter(homeSpecialityListModels);
//        rvSpeciality.setAdapter(filterSpecialityAdapter);
//    }
//
//
//    //search
//
//    //todo: Fristy - change how search trainer logic
//    @Override
//    public void search(String value) {
//        listTrainerPresenter.searchTrainer(apiService, getSessionManager().getUserDetails()
//                .get(SessionManager.KEY_ACCESS_TOKEN), value);
//        hideKeyboard();
//    }
//
//    //TODO: fristy - get all package
//    @Override
//    public void doShowListAllPackage(List<ListPackageModelOld> listPackageModelOld) {
//        //Log.d("FRISTY LOG", "ISI DARI PACKAGE MODEL LIST " + listPackageModelOld);
//        dismissLoading();
//
//        if (listPackageModelOld.size() > 0) {
//            recyclerViewAdvancedAdapter.updateListpackage(listPackageModelOld);
//        }
//    }
//
//    @Override
//    public void doShowTrainerBySearch(List<ListTrainerAdvancedModel> listTrainerAdvancedModels) {
//        dismissLoading();
//        templistTrainerAdvancedModelsSearch = new ArrayList<>();
//        /*for(int i=0; i< listTrainerAdvancedModels.size();i++)
//        {
//            Log.d("FRISTY LOG 2", "listTrainerAdvancedModels name " + listTrainerAdvancedModels.get(i).getName() + "\n");
//        }*/
//        if (listTrainerAdvancedModels.size() > 0) {
//            layoutEmpty.setVisibility(View.GONE);
////            textEmpty.setVisibility(View.GONE);
//            if (useFilter == false) {
//                recyclerViewAdvancedAdapter.refresh(listTrainerAdvancedModels);
//                templistTrainerAdvancedModelsSearch.addAll(listTrainerAdvancedModels);
//            } else {
//                //Log.d("FRISTY LOG 2", "MASUK ET KEYBWORD ADA ISI keyword " +etTrainerName.getText());
//                //Log.d("FRISTY LOG 2", "templistTrainerAdvancedModelsSearch.size() " +templistTrainerAdvancedModelsSearch.size());
//                if (etTrainerName.getText().toString().equals("")) {
//                    if (recyclerViewAdvancedAdapter != null) {
//                        configureRvAdapter();
//                        //TODO: fristy - get all package
//                        listTrainerPresenter.getAllPackage(apiService,
//                                getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//                        listTrainerPresenter.getListTrainerBySpecialityGender(apiService, getSessionManager().getUserDetails()
//                                .get(SessionManager.KEY_ACCESS_TOKEN), tempGender, specialityTemp, tempCurrentDate);
//
//                    }
//                } else if (templistTrainerAdvancedModelsFiltered.size() > 0) {
//                    for (int i = 0; i < listTrainerAdvancedModels.size(); i++) {
//                        for (int j = 0; j < templistTrainerAdvancedModelsFiltered.size(); j++) {
//                            //Log.d("FRISTY LOG 2", "listTrainerAdvancedModels.get(i).getId() " +listTrainerAdvancedModels.get(i).getId());
//                            //Log.d("FRISTY LOG 2", "ttemplistTrainerAdvancedModelsSearch.get(j).getId() " +templistTrainerAdvancedModelsSearch.get(j).getId());
//                            if (listTrainerAdvancedModels.get(i).getId().equals(templistTrainerAdvancedModelsFiltered.get(j).getId())) {
//                                templistTrainerAdvancedModelsSearch.add(listTrainerAdvancedModels.get(i));
//                            }
//                        }
//                    }
//                    recyclerViewAdvancedAdapter.refresh(templistTrainerAdvancedModelsSearch);
//                } else {
//                    rvListTrainer.setVisibility(View.GONE);
//                    layoutEmpty.setVisibility(View.VISIBLE);
////                    textEmpty.setVisibility(View.VISIBLE);
//                }
//            }
//        } else {
//            rvListTrainer.setVisibility(View.GONE);
//            layoutEmpty.setVisibility(View.VISIBLE);
////            textEmpty.setVisibility(View.VISIBLE);
//        }
//    }
//
//    //refresh all data
//
//    @Override
//    public void showVouchers(List<ListTrainerAdvancedModel> listTrainerAdvancedModels) {
//        dismissLoading();
//        if (listTrainerAdvancedModels.size() > 0) {
//            layoutEmpty.setVisibility(View.GONE);
//            recyclerViewAdvancedAdapter.refresh(listTrainerAdvancedModels);
//        } else {
//            rvListTrainer.setVisibility(View.GONE);
//            layoutEmpty.setVisibility(View.VISIBLE);
//        }
//    }
//
//    //search by speciality and gender
//
//    @Override
//    public void doShowListTrainerBySpecialityGender(List<ListTrainerAdvancedModel> listTrainerAdvancedModels) {
//        dismissLoading();
//        specialityFromHome = null;
//        templistTrainerAdvancedModelsFiltered = new ArrayList<>();
//
//        if (listTrainerAdvancedModels.size() > 0) {
//            layoutEmpty.setVisibility(View.GONE);
////            textEmpty.setVisibility(View.GONE);
//            //recyclerViewAdvancedAdapter.refresh(listTrainerAdvancedModels); //TODO: fristy change the way show search and filter trainer
//            if (etTrainerName.getText().toString().equals("")) {
//                recyclerViewAdvancedAdapter.refresh(listTrainerAdvancedModels);
//                templistTrainerAdvancedModelsFiltered.addAll(listTrainerAdvancedModels);
//            } else {
//                if (templistTrainerAdvancedModelsSearch.size() > 0) {
//                    for (int i = 0; i < listTrainerAdvancedModels.size(); i++) {
//                        for (int j = 0; j < templistTrainerAdvancedModelsSearch.size(); j++) {
//
//                            if (listTrainerAdvancedModels.get(i).getId().equals(templistTrainerAdvancedModelsSearch.get(j).getId())) {
//                                templistTrainerAdvancedModelsFiltered.add(listTrainerAdvancedModels.get(i));
//                            }
//                        }
//                    }
//                    recyclerViewAdvancedAdapter.refresh(templistTrainerAdvancedModelsFiltered);
//                } else {
//                    rvListTrainer.setVisibility(View.GONE);
//                    layoutEmpty.setVisibility(View.VISIBLE);
////                    textEmpty.setVisibility(View.VISIBLE);
//                }
//            }
//        } else {
//            rvListTrainer.setVisibility(View.GONE);
//        }
//    }
//
//    @OnClick({R.id.etDateTime, R.id.button_my_voucher, R.id.btnApply, R.id.button_cancel_voucher_use, R.id.button_cancel_filter_speciality, R.id.button_cancel_filter_gender, R.id.button_cancel_filter_date, R.id.button_cancel_filter_name})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.etDateTime:
//                hideKeyboard();
//                showPopupDate();
//                break;
//            case R.id.button_my_voucher:
//                //TODO : fristy - buka halaman voucher
//                //Intent i=new Intent(this, MyVoucherActivity.class);
//                //i.putExtra("previousActivity","SearchTrainerActivity");
//                //startActivity(i);
//                break;
//            case R.id.button_cancel_voucher_use:
//                layoutMyVoucher.setVisibility(View.GONE);
//                voucherCode = null;
//                break;
//            case R.id.btnApply:
//                tempGender = "";
//                tempCurrentDate = 0;
//                List<String> tempSpeciality = new ArrayList<>();
//
//                if (btnMan.isChecked() && btnWoman.isChecked()) {
//
//                    layoutFilterGender.setVisibility(View.GONE);
//                    btnMan.setChecked(false);
//                    btnWoman.setChecked(false);
//                    tempGender = "";
//                } else if (btnMan.isChecked()) {
//
//                    layoutFilterGender.setVisibility(View.VISIBLE);
//                    textFilterGender.setText(getContext().getResources().getString(R.string.man));
//                    tempGender = "M";
//                } else if (btnWoman.isChecked()) {
//
//                    layoutFilterGender.setVisibility(View.VISIBLE);
//                    textFilterGender.setText(getContext().getResources().getString(R.string.woman));
//                    tempGender = "F";
//                } else {
//                    layoutFilterGender.setVisibility(View.GONE);
//                    tempGender = "";
//                }
//
//                if (specialityTemp == null || specialityTemp.equals("")) {
//                    layoutFilterSpeciality.setVisibility(View.GONE);
////                    textFilterSpeciality.setVisibility(View.GONE);
////                    buttonCancelFilterSpeciality.setVisibility(View.GONE);
//                    if (btnMan.isChecked() && btnWoman.isChecked()) {
//                        btnMan.setChecked(false);
//                        btnWoman.setChecked(false);
//                        useFilter = false;
//                    } else if (btnMan.isChecked())
//                        useFilter = true;
//                    else if (btnWoman.isChecked())
//                        useFilter = true;
//                        //    else if (btnDate.isChecked())
//                        //        useFilter = true;
//                    else
//                        useFilter = false;
//                } else {
//                    layoutFilterSpeciality.setVisibility(View.VISIBLE);
////                    textFilterSpeciality.setVisibility(View.VISIBLE);
////                    buttonCancelFilterSpeciality.setVisibility(View.VISIBLE);
//                    textFilterSpeciality.setText(specialityTemp);
//                    if (specialityTemp.length() > 0) {
//                        useFilter = true;
//                    } else {
//                        if (btnMan.isChecked() && btnWoman.isChecked()) {
//                            useFilter = false;
//                            btnMan.setChecked(false);
//                            btnWoman.setChecked(false);
//                        } else if (btnMan.isChecked())
//                            useFilter = true;
//                        else if (btnWoman.isChecked())
//                            useFilter = true;
//                            //    else if (btnDate.isChecked())
//                            //       useFilter = true;
//                        else
//                            useFilter = false;
//                    }
//                }
//                if (recyclerViewAdvancedAdapter != null) {
//                    configureRvAdapter();
//                    //TODO: fristy - get all package
//                    listTrainerPresenter.getAllPackage(apiService,
//                            getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//                    listTrainerPresenter.getListTrainerBySpecialityGender(apiService, getSessionManager().getUserDetails()
//                            .get(SessionManager.KEY_ACCESS_TOKEN), tempGender, specialityTemp, tempCurrentDate);
//
//                }
//
//                if (useFilter)
//                    layoutFilterTitle.setVisibility(View.VISIBLE);
//                break;
//            case R.id.button_cancel_filter_date:
//                etDateTime.setText("");
//                counterPage = 0;
//                recyclerViewAdvancedAdapter.clearList();
//                if (recyclerViewAdvancedAdapter != null) {
//                    if (specialityFromHome == null) {
//                        listTrainerPresenter.getAllTrainers(apiService,
//                                getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//                    } else {
//                        listTrainerPresenter.getListTrainerFiltered(apiService, getSessionManager().getUserDetails()
//                                .get(SessionManager.KEY_ACCESS_TOKEN), null, specialityFromHome, null, counterPage);
//                    }
//                }
//                break;
//            case R.id.button_cancel_filter_name:
//                etTrainerName.setText("");
//                break;
//            case R.id.button_cancel_filter_gender:
//                layoutFilterGender.setVisibility(View.GONE);
////                textFilterGender.setVisibility(View.GONE);
////                buttonCancelFilterGender.setVisibility(View.GONE);
//                btnMan.setChecked(false);
//                btnWoman.setChecked(false);
//                tempGender = "";
//                useFilter = false;
//                if (recyclerViewAdvancedAdapter != null) {
//                    if (specialityFromHome == null) {
//                        listTrainerPresenter.getAllTrainers(apiService,
//                                getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//                    } else {
//                        listTrainerPresenter.getListTrainerFiltered(apiService, getSessionManager().getUserDetails()
//                                .get(SessionManager.KEY_ACCESS_TOKEN), null, specialityFromHome, null, counterPage);
//                    }
//                }
//                if (specialityTemp.equals(""))
//                    layoutFilterTitle.setVisibility(View.GONE);
//                break;
//            case R.id.button_cancel_filter_speciality:
//                specialityTemp = "";
//                layoutFilterSpeciality.setVisibility(View.GONE);
////                textFilterSpeciality.setVisibility(View.GONE);
////                buttonCancelFilterSpeciality.setVisibility(View.GONE);
//                if (recyclerViewAdvancedAdapter != null) {
//                    configureRvAdapter();
//                    //TODO: fristy - get all package
//                    listTrainerPresenter.getAllPackage(apiService,
//                            getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//                    listTrainerPresenter.getListTrainerBySpecialityGender(apiService, getSessionManager().getUserDetails()
//                            .get(SessionManager.KEY_ACCESS_TOKEN), tempGender, specialityTemp, tempCurrentDate);
//
//                }
//                listTrainerPresenter.getAllSpeciality(apiService, getSessionManager().getUserDetails()
//                        .get(SessionManager.KEY_ACCESS_TOKEN));
//                if (tempGender.equals(""))
//                    layoutFilterTitle.setVisibility(View.GONE);
//                break;
//        }
//        drawerLayout.closeDrawer(Gravity.END);
//    }
//
//    //Popup date time filter
//
//    private void showPopupDate() {
//
////        dialogDatePicker.setContentView(R.layout.date_picker_layout);
////
////        textTrainingSchedule = dialogDatePicker.findViewById(R.id.text_Training_Schedule);
////        btnApplyDate = dialogDatePicker.findViewById(R.id.btnApplyDate);
////        datePicker = dialogDatePicker.findViewById(R.id.datePicker);
////        datePicker.setMinDate(System.currentTimeMillis() - 1000);
////
//        Calendar calendar = Calendar.getInstance();
//        int currentYear = calendar.get(Calendar.YEAR);
//        int currentMonth = calendar.get(Calendar.MONTH);
//        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//
//        new SpinnerDatePickerDialogBuilder()
//                .context(this)
//                .callback(this)
//                .showTitle(true)
//                .showDaySpinner(true)
//                .defaultDate(year, month, day)
//                .maxDate(currentYear + 1, currentMonth, currentDay)
//                .minDate(currentYear, currentMonth, currentDay)
//                .build()
//                .show();
//
////        textTrainingSchedule.setTypeface(typefaceRegular);
////        btnApplyDate.setTypeface(typefaceMedium);
////        btnApplyDate.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                dialogDatePicker.dismiss();
////                //showPopUpTime();
////                updateLabel(view);
//////                String dateTime = etDateTime.getText().toString();
//////                Intent returnIntent = new Intent();
//////                returnIntent.putExtra("resultDateForList", dateTime);
//////                setResult(RESULT_OK, returnIntent);
//////                finish();
////                listTrainerPresenter.getAllTrainers(apiService, getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN), String.valueOf(day), null, null,
////                        sbStart.toString(), sbEnd.toString(), date);
////            }
////        });
//////
////        Window window = dialogDatePicker.getWindow();
////        if (window != null) {
////            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
////        }
////        dialogDatePicker.show();
//    }
//
////    private void updateLabel(View view) {
//////        year = datePicker.getYear();
//////        month = datePicker.getMonth() + 1;
//////        day = datePicker.getDayOfMonth();
////        //hourSelectedStart = String.valueOf(timePickerStart.getCurrentHour()); //todo: fristy comment for now because not use time
////
////        //todo: fristy comment for now because not use time
////        //String currentMinuteStart = String.format("%02d", timePickerStart.getCurrentMinute());
////        //minuteSelectedStart = currentMinuteStart;
////        //hourSelectedEnd = String.valueOf(timePickerEnd.getCurrentHour());
////        //String currentMinuteEnd = String.format("%02d", timePickerEnd.getCurrentMinute());
////        //minuteSelectedEnd = currentMinuteEnd;
////
//////        sbStart = new StringBuilder();
//////        sbStart.append("00");//(hourSelectedStart); //todo: fristy comment for now because not use time
//////        sbStart.append(":");
//////        sbStart.append("00");//(minuteSelectedStart); //todo: fristy comment for now because not use time
////        // sbStart.append(":00");
////
//////        sbEnd = new StringBuilder();
//////        sbEnd.append("24");//(hourSelectedEnd); //todo: fristy comment for now because not use time
//////        sbEnd.append(":");
//////        sbEnd.append("59");//(minuteSelectedEnd);//todo: fristy comment for now because not use time
////        //sbEnd.append(":00");
/////*
////        sbDate = new StringBuilder();
////        sbDate.append(yearSelected);
////        sbDate.append("-");
////        sbDate.append(monthSelected);
////        sbDate.append("-");
////        sbDate.append(daySelected);*/
////
////        sbDate = new StringBuilder();
////        sbDate.append(day);
////        sbDate.append("/");
////        sbDate.append(month);
////        sbDate.append("/");
////        sbDate.append(year);
////
////        sb = new StringBuilder();
////        sb.append(sbDate);
////        sb.append(" ");
////        //todo: fristy comment for now because not use time
////        //sb.append(sbStart);
////        //sb.append(" - ");
////        //sb.append(sbEnd);
////
////        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
////        Date selectedDate = null;
////        try {
////            selectedDate = dateFormat.parse(sbDate.toString());
////        } catch (ParseException e) {
////            e.printStackTrace();
////        }
////
//////        if (selectedDate != null) {
//////            date = selectedDate.getTime();
//////        }
////
////        Calendar calendar = Calendar.getInstance();
////        calendar.setTime(selectedDate);
////
////        SimpleDateFormat dayName = new SimpleDateFormat("EEEE", Locale.US);
////        dayOff = (dayName.format(selectedDate));
////
////        DateFormat dateFormat2 = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
////        String dateFormatted2 = dateFormat2.format(selectedDate);
////        String eventDate = dateFormatted2;
////
////        etDateTime.setText(eventDate);
////    }
//
//    @Override
//    public void onBackPressed() {
//        //check activity
//        String dateTime = etDateTime.getText().toString();
//        if (getCallingActivity() != null) {
//            String callingClass = getCallingActivity().getClassName();
//            if (callingClass.equals(SubCategoriesActivity.class.getName())) {
//                Intent i = new Intent();
//                i.putExtra("resultDateForList", dateTime);
//                setResult(RESULT_OK, i);
//                finish();
//            }
//        } else {
//            finishActivity();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (specialityTemp == null || tempGender == null || tempCurrentDate == 0) {
//            listTrainerPresenter.observeSearch(etTrainerName);
//        }
//    }
//
//    //Called by adapter to open DetailTrainerActivity
//    @Override
//    public void openDetailTrainer(String trainerId) {
//
//        //First check if user is logged in
//        if (!getSessionManager().checkLogin2(LoginActivity.OPEN_LIST_TRAINER)) {
//
//            Intent intent = new Intent(this, DetailTrainerActivity.class);
//
//            //Add trainerId for DetailTrainer to load data
//            intent.putExtra(Constants.INTENT_EXTRA_TRAINER_ID, trainerId);
//
//            //Voucher related variables
//            intent.putExtra(Constants.INTENT_EXTRA_VOUCHER_TYPE, getIntent().getSerializableExtra(Constants.INTENT_EXTRA_VOUCHER_TYPE));
//            intent.putExtra(Constants.INTENT_EXTRA_VOUCHER_CODE, voucherCode);
//            intent.putExtra(Constants.INTENT_EXTRA_VOUCHER_DISCOUNT_VALUE, discountValue);
//
//            //start activity for result to catch if Voucher is cancelled in DetailTrainerActivity
//            startActivityForResult(intent, 1);
//        }
//
//    }
//
//
//    /**
//     * If voucher is cancelled result will be RESULT_VOUCHER_CANCELLED,
//     * close activity immediately so user will be moved back to MyVoucherActivity
//     * <p>
//     * Else if voucher is not cancelled, do nothing
//     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 1 && resultCode == Constants.RESULT_VOUCHER_CANCELLED) {
//            finish();
//        }
//    }
//
//    private void showFilterFromSubCategories() {
//        Bundle data = getIntent().getExtras();
//        if (data != null) {
//
//            specialityTemp = data.getString(Constants.INTENT_SUB_CATEGORIES_SPECIALITY, "");
//            textFilterSpeciality.setText(specialityTemp);
//            String subCategoriesGender = data.getString(Constants.INTENT_SUB_CATEGORIES_GENDER, "");
//            tempCurrentDate = data.getLong(Constants.INTENT_SUB_CATEGORIES_DATE, 0);
//
//            useFilter = true;
//            if (specialityTemp.equals("") && subCategoriesGender.equals(""))
//                layoutFilterTitle.setVisibility(View.GONE);
//            else {
//                layoutFilterTitle.setVisibility(View.VISIBLE);
//
//                if (!specialityTemp.equals(""))
//                    layoutFilterSpeciality.setVisibility(View.VISIBLE);
//
//                if (subCategoriesGender.equals("M")) {
//                    layoutFilterGender.setVisibility(View.VISIBLE);
//                    textFilterGender.setText(getContext().getResources().getString(R.string.man));
//                    tempGender = "M";
//                } else if (subCategoriesGender.equals("F")) {
//                    layoutFilterGender.setVisibility(View.VISIBLE);
//                    textFilterGender.setText(getContext().getResources().getString(R.string.woman));
//                    tempGender = "F";
//                }
//            }
//
//            Date selectedDate = new Date(tempCurrentDate);
//            DateFormat dateFormat2 = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
//            String dateFormatted2 = dateFormat2.format(selectedDate);
//            String eventDate = dateFormatted2;
//
//            etDateTime.setText(eventDate);
//
//            if (recyclerViewAdvancedAdapter != null) {
////                configureRvAdapter();
//                //TODO: fristy - get all package
//                listTrainerPresenter.getAllPackage(apiService,
//                        getSessionManager().getUserDetails().get(SessionManager.KEY_ACCESS_TOKEN));
//                listTrainerPresenter.getListTrainerBySpecialityGender(apiService, getSessionManager().getUserDetails()
//                        .get(SessionManager.KEY_ACCESS_TOKEN), tempGender, specialityTemp, tempCurrentDate);
//
//            }
//        }
//    }
//
//    private void setDateBooking() {
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.DAY_OF_MONTH, day);
//        cal.set(Calendar.MONTH, month);
//        cal.set(Calendar.YEAR, year);
//
//        cal.getTimeInMillis();
//
//        Date date = new Date(cal.getTimeInMillis());
//
//        DateFormat dateFormat2 = new SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault());
//        String dateFormatted2 = dateFormat2.format(date);
//        etDateTime.setText(dateFormatted2);
//        //etDateTime.setText(String.format("%d/%d/%d", day, month + 1, year));
//    }
//
//    @Override
//    public void onDateSet(com.tsongkha.spinnerdatepicker.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        this.year = year;
//        this.month = monthOfYear;
//        this.day = dayOfMonth;
//        setDateBooking();
//    }
//}
//
