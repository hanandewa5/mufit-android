package com.nostratech.mufit.consumer.settings;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;

import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyToolbarBackActivity;
import com.nostratech.mufit.consumer.logout.LogoutContract;
import com.nostratech.mufit.consumer.logout.LogoutPresenter;
import com.nostratech.mufit.consumer.root.RootActivity;
import com.nostratech.mufit.consumer.utils.LocaleManager;
import com.nostratech.mufit.consumer.utils.TutorialManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.mufit.core.dialog.MufitDialogTwoButtonsWithText;

public class SettingsActivity extends MyToolbarBackActivity implements LogoutContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_settings)
    Spinner spinnerSettings;
    @BindView(R.id.switch_repeat)
    SwitchCompat switchRepeat;

    private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    TutorialManager tutorialManager;


    private LogoutPresenter logoutPresenter;
    private static final String URL_WEB_MUFIT = "https://mufit.id/";
    private static final String URL_INSTAGRAM_MUFIT = "https://www.instagram.com/mufit.id/?hl=id";
    private static final String URL_FAQ_MUFIT = "https://mufit.id/faq";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initToolbar(toolbar);

        setupSwitchRepeatTutorial();

        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        tutorialManager = new TutorialManager(this);
        logoutPresenter = new LogoutPresenter(this, this, this);

        List<String> data = new ArrayList<>();
        data.add(getResources().getString(R.string.indonesia));
        data.add(getResources().getString(R.string.english));

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.settings_spinner,
                R.id.spinner_text,
                data);

        spinnerSettings.setAdapter(adapter);
        spinnerSettings.setSelection(LocaleManager.LANGUAGE_INDONESIA.equals(LocaleManager.getLanguage(getApplicationContext())) ?
                0 : 1);
        spinnerSettings.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        if (!LocaleManager.LANGUAGE_INDONESIA.equals(LocaleManager.getLanguage(getApplicationContext())))
                            setNewLocale(LocaleManager.LANGUAGE_INDONESIA);
                        break;
                    case 1:
                        if (!LocaleManager.LANGUAGE_ENGLISH.equals(LocaleManager.getLanguage(getApplicationContext())))
                            setNewLocale(LocaleManager.LANGUAGE_ENGLISH);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick({R.id.settings_layoutWebsite,
            R.id.settings_layoutSocialMedia,
            R.id.settings_layoutFAQ,
            R.id.settings_layoutLogout})
    public void onClick(View v){
        switch(v.getId()){
            case R.id.settings_layoutWebsite:
                launchWebBrowser(URL_WEB_MUFIT);
                break;
            case R.id.settings_layoutSocialMedia:
                launchWebBrowser(URL_INSTAGRAM_MUFIT);
                break;
            case R.id.settings_layoutFAQ:
                launchWebBrowser(URL_FAQ_MUFIT);
                break;
            case R.id.settings_layoutLogout:
                showLogoutDialog();
                break;
        }
    }

    private void launchWebBrowser(String url){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    private void showLogoutDialog(){
        MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(this,
                getString(R.string.confirmation),
                getString(R.string.logout_message));

        dialog.getBtnPositive().setOnClickListener(l -> {
            logoutPresenter.logout();
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * Instead of adding an OnClick to the switch itself, we manipulate the change in state
     * When the switch is checked (clicked by user), show a dialog asking for user confirmation
     */
    private void setupSwitchRepeatTutorial() {
        switchRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MufitDialogTwoButtonsWithText dialog = new MufitDialogTwoButtonsWithText(SettingsActivity.this,
                            getString(R.string.confirmation),
                            getString(R.string.repeat_tutorial_confirmation));

                    dialog.getBtnNegative().setOnClickListener(v -> {
                        switchRepeat.setChecked(false);
                        dialog.dismiss();
                    });

                    dialog.getBtnPositive().setOnClickListener(l -> {
                        tutorialManager.repeatTutorial();
                        Intent i = new Intent(SettingsActivity.this, RootActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        showActivity(i);
                    });

                    dialog.show();
                }
            }
        });
    }

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
        showGenericError(getString(R.string.no_internet));
    }

    private void setNewLocale(String language) {
        LocaleManager.setNewLocale(this, language);

        Intent rootIntent = new Intent(this, RootActivity.class);
        rootIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent settingsIntent = new Intent(this, SettingsActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(rootIntent);
        stackBuilder.addNextIntent(settingsIntent);
        stackBuilder.startActivities();
    }

    @Override
    public void showLogoutSuccessMessage() {
        Intent i = new Intent(this, RootActivity.class);

        //Previously CLEAR_TOP | NEW_TASK. Have to check for consistency in behavior
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        showActivity(i);
    }
}
