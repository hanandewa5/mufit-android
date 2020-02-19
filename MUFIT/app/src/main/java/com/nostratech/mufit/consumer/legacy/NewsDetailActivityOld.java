//package com.nostratech.mufit.consumer.news;
//
//import android.os.Bundle;
//import android.text.Spannable;
//import android.text.SpannableStringBuilder;
//import android.text.style.TypefaceSpan;
//import android.widget.ImageView;
//import android.widget.ScrollView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.widget.Toolbar;
//
//import com.nostratech.mufit.consumer.R;
//import com.nostratech.mufit.consumer.model.NewsModel;
//import com.nostratech.mufit.consumer.utils.CustomTypefaceSpan;
//import com.nostratech.mufit.consumer.utils.FontUtils;
//import com.nostratech.mufit.consumer.utils.glidehelper.GlideApp;
//
//import java.util.Objects;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class NewsDetailActivityOld extends BaseActivity {
//
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
//
//    @BindView(R.id.news_image)
//    ImageView image;
//    @BindView(R.id.news_title)
//    TextView title;
//    @BindView(R.id.news_content)
//    TextView content;
//    @BindView(R.id.scroll_view)
//    ScrollView scrollView;
//
//    public static final String EXTRA_NEWS_MODEL = "newsModel";
//
//    private NewsModel newsModel;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news_detail_old);
//        ButterKnife.bind(this);
//
//        configureToolbar(true, toolbar);
//        //karena ketika dibuka scrollview langsung ke scroll ke paling bawah
//        scrollView.smoothScrollTo(0,0);
//        content.setTextIsSelectable(true);
//
//        Bundle data = getIntent().getExtras();
//        Objects.requireNonNull(data, "Intent extras of NewsDetailActivityOld is null." +
//                " Ensure that news model is inserted into intent extras from calling activity");
//        newsModel = (NewsModel) data.getSerializable(EXTRA_NEWS_MODEL);
//
//
//        GlideApp.with(this)
//                .load(newsModel.getImageUrl())
//                .centerCrop()
//                .into(image);
//
//        title.setText(newsModel.getTitle());
//
//        setNewsArticle();
//
//    }
//    private void setNewsArticle(){
//        String source = newsModel.getSource().toUpperCase();
//        String separator = " - ";
//        String sourceAndDesc = source + separator + newsModel.getDescription();
//        SpannableStringBuilder sb = new SpannableStringBuilder(sourceAndDesc);
//
//        TypefaceSpan truenoMediumSpan = new CustomTypefaceSpan("", FontUtils.getTruenoMedium(this));
//        TypefaceSpan truenoRegularSpan = new CustomTypefaceSpan("", FontUtils.getTruenoRegular(this));
//
//        // set source typeface to trueno medium and the rest of article to trueno regular
//        sb.setSpan(truenoMediumSpan, 0,
//                source.length() + separator.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        sb.setSpan(truenoRegularSpan, source.length() + separator.length(),
//                sourceAndDesc.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE );
//
//        content.setText(sb);
//    }
//}
