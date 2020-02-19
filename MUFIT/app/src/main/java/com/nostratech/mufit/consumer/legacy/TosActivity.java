//package com.nostratech.mufit.consumer.register;
//
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.TextView;
//
//import com.nostratech.mufit.consumer.R;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class TosActivity extends BaseActivity {
//    @BindView(R.id.text_title)
//    TextView textTitle;
//    @BindView(R.id.text_tos_desc)
//    TextView textTosDesc;
//    @BindView(R.id.checkbox_agree)
//    CheckBox checkboxAgree;
//    @BindView(R.id.button_next)
//    Button buttonNext;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tos);
//        ButterKnife.bind(this);
//        initTypefaces();
//    }
//
//    @OnClick(R.id.button_next)
//    public void onClick() {
//        if(checkboxAgree.isChecked()) {
//            showActivity(TosActivity.this, IntroActivity.class);
//            finish();
//        } else {
//            showToastMessageShort(getResources().getString(R.string.msg_must_agree_tos));
//        }
//    }
//
//    private void initTypefaces(){
//        textTitle.setTypeface(getTfMedium());
//        textTosDesc.setTypeface(getTfRegular());
//        checkboxAgree.setTypeface(getTfMedium());
//        buttonNext.setTypeface(getTfMedium());
//    }
//}
