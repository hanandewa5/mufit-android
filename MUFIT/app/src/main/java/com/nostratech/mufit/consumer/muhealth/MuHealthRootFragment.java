package com.nostratech.mufit.consumer.muhealth;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseFragment;
import com.nostratech.mufit.consumer.muhealth.checklist.ChecklistFragmentAdapter;
import com.nostratech.mufit.consumer.muhealth.checklist.DailyQuest;
import com.nostratech.mufit.consumer.muhealth.component.HealthComponentAdapter;
import com.nostratech.mufit.consumer.muhealth.component.HealthComponentModel;
import com.nostratech.mufit.consumer.muhealth.detail.ComponentDetailActivity;
import com.nostratech.mufit.consumer.muhealth.weightbar.BarData;
import com.nostratech.mufit.consumer.muhealth.weightbar.MultiColorBar;
import com.nostratech.mufit.consumer.settings.SettingsActivity;
import com.nostratech.mufit.consumer.utils.Constants;
import com.nostratech.mufit.consumer.utils.CustomTypefaceSpan;
import com.nostratech.mufit.consumer.utils.FontUtils;
import com.nostratech.mufit.consumer.utils.ViewPagerFragment;
import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MuHealthRootFragment extends MyBaseFragment implements MuHealthContract.View,
        HealthComponentAdapter.OnClickListener,
        ViewPagerFragment {

    @BindView(R.id.muhealth_toolbar)
    Toolbar toolbar;

    @BindView(R.id.newProfile_imageProfile)
    ImageView imgProfile;

    @BindView(R.id.newProfile_textGreetings)
    TextView textGreetings;

    @BindView(R.id.newProfile_chartBmiCategory)
    MultiColorBar chartWeightCategorization;

    @BindView(R.id.newProfile_healthComponents)
    RecyclerView rvHealthComponents;

    @BindView(R.id.newProfile_checklist)
    ViewPager2 pagerChecklist;

    private HealthComponentAdapter adapter;

    private Unbinder unbinder;

    private boolean isInit = false;

    private MuHealthPresenter muHealthPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_root_muhealth, container, false);
        unbinder = ButterKnife.bind(this, view);

        muHealthPresenter = new MuHealthPresenter(getActivity(),this,this);

        initToolbar();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unbinder = null;
    }

    private void initToolbar(){
        toolbar.inflateMenu(R.menu.toolbar_muhealth);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.muhealth_toolbarMenuSettings) {
                    goToSettings();
//                    muHealthPresenter.testAja();
                    return true;
                }
                return false;
            }
        });

        //TODO: Tint the icon temporarily while waiting for properly colored assets
        MenuItem settings = toolbar.getMenu().findItem(R.id.muhealth_toolbarMenuSettings);
        Drawable drawable = settings.getIcon();
        if (drawable != null) {
            // If we don't mutate the drawable, then all drawable's with this id will have a color
            // filter applied to it.
            drawable.mutate();
            drawable.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void initChecklist(){
        ChecklistFragmentAdapter adapter = new ChecklistFragmentAdapter(getActivity());
        pagerChecklist.setAdapter(adapter);

        DailyQuest quest1 = new DailyQuest(R.drawable.ic_checklist_run, true);
        DailyQuest quest2 = new DailyQuest(R.drawable.ic_checklist_junk_food, true);
        DailyQuest quest3 = new DailyQuest(R.drawable.ic_checklist_dumbbells, false);
        DailyQuest quest4 = new DailyQuest(R.drawable.ic_checklist_tasks, false);
        DailyQuest quest5 = new DailyQuest(R.drawable.ic_checklist_timer, false);
        DailyQuest quest6 = new DailyQuest(R.drawable.ic_checklist_run, false);
        DailyQuest quest7 = new DailyQuest(R.drawable.ic_checklist_timer, true);
        DailyQuest quest8 = new DailyQuest(R.drawable.ic_checklist_tasks, true);

        List<DailyQuest> quests = new ArrayList<>();
        quests.add(quest1);
        quests.add(quest2);
        quests.add(quest3);
        quests.add(quest4);
        quests.add(quest5);
        quests.add(quest6);
        quests.add(quest7);
        quests.add(quest8);

        adapter.insertData(quests);
    }

    private void initUserData(){
        String url = "https://www.goldenglobes.com/sites/default/files/styles/portrait_medium/public/gallery_images/17-tomcruiseag.jpg?itok=qNj0cQGV&c=c9a73b7bdf609d72214d226ab9ea015e";
        GlideApp.with(this)
                .load(url)
                .centerCrop()
                .into(imgProfile);

        String hi = "Hi, ";
        String name = "Tom Cruise";
        SpannableStringBuilder sb = new SpannableStringBuilder(hi + name);

        TypefaceSpan lightSpan = new CustomTypefaceSpan("", FontUtils.getTruenoLight(getContext()));
        TypefaceSpan mediumSpan = new CustomTypefaceSpan("", FontUtils.getTruenoMedium(getContext()));

        // set source typeface to trueno medium and the rest of article to trueno regular
        sb.setSpan(lightSpan, 0,
                hi.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        sb.setSpan(mediumSpan, hi.length(),
                hi.length() + name.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
        textGreetings.setText(sb);
    }

    private void initChartWeightCategorization(){
        chartWeightCategorization.post(()->{
            chartWeightCategorization.setValueRange(12, 38);

            List<BarData> data = new ArrayList<>();
            data.add(new BarData(getResources().getColor(R.color.muhealth_underweight), 18.5, "Underweight"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_normal), 25, "Normal"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_monitor), 28, "OB I"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_bad), 32, "OB II"));
            data.add(new BarData(getResources().getColor(R.color.muhealth_verybad), 38, "OB III"));
            chartWeightCategorization.setData(data, 23.0);
        });
    }

    private void configureRvHealthComponents(){
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);

        adapter = new HealthComponentAdapter(this);
        rvHealthComponents.setAdapter(adapter);
        rvHealthComponents.setLayoutManager(layoutManager);
        rvHealthComponents.setNestedScrollingEnabled(false);
        rvHealthComponents.setHasFixedSize(false);
    }

    private void initAdapterData(){

        List<HealthComponentModel> models = new ArrayList<>();

        models.add(new HealthComponentModel("https://dev-file.mufit.id/muhealth/running.png", "Steps", 8123 , 1324, ""));
        models.add(new HealthComponentModel("https://dev-file.mufit.id/muhealth/weight.png", "Body Weight", 83 ,  -0.5, "kg"));
        models.add(new HealthComponentModel("https://dev-file.mufit.id/muhealth/obesity.png", "Body Fat", 14, 1.5, "%"));
        models.add(new HealthComponentModel("https://dev-file.mufit.id/muhealth/muscle.png", "Muscle Mass", 66 , 0.5, "%"));
        models.add(new HealthComponentModel("https://dev-file.mufit.id/muhealth/drops.png", "Body Water", 55, 0, "%"));
        adapter.insertData(models);

    }

    @Override
    public void onShowDetailClick(HealthComponentModel model) {
        Intent i = new Intent(getActivity(), ComponentDetailActivity.class);
        i.putExtra(Constants.HealthComponent.ID, model.getId());
        showActivity(i);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoInternetError() {

    }

    @Override
    public void onFragmentOpenedByUser() {
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            @Override
            public void run() {

                //When Fragment is attached to its Activity, start to query Backend
                if (isAdded()) {
                    if (isInit) return;
                    initializePage();
                    isInit = true;
                }

                //If Fragment is not yet attached, retry in 200 ms
                else {
                    handler.postDelayed(this, 200);
                }
            }
        };

        handler.post(r);
    }

    private void initializePage(){
        initChecklist();
        initUserData();
        initChartWeightCategorization();
        configureRvHealthComponents();
        initAdapterData();
    }

    @Override
    public void refreshFragment() {

    }

    @Override
    public void goToSettings() {
        Intent i = new Intent(getContext(), SettingsActivity.class);
        showActivity(i);
    }
}
