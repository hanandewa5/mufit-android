package com.nostratech.mufit.consumer.detailtrainer.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nostratech.mufit.consumer.R;
import com.nostratech.mufit.consumer.base.MyBaseFragment;
import com.nostratech.mufit.consumer.detailtrainer.helper.ProfileTrainerDetailAdaper;
import com.nostratech.mufit.consumer.detailtrainer.helper.ProfiletrainerImageAdaper;
import com.nostratech.mufit.consumer.model.DetailTrainerModel;
import com.nostratech.mufit.consumer.model.TrainerCertificateModel;
import com.nostratech.mufit.consumer.model.TrainerImageModel;
import com.nostratech.mufit.consumer.utils.EndlessScrollListener;
import com.nostratech.mufit.consumer.utils.TutorialManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentProfile extends MyBaseFragment implements FragmentProfileContract.View {

    @BindView(R.id.scroll_view_profil_trainer)
    ScrollView scrollView;

    @BindView(R.id.rv_profile_detail)
    RecyclerView rvProfileCertificates;
    @BindView(R.id.rv_profile_image)
    RecyclerView rvProfileImage;
    @BindView(R.id.empty_content)
    LinearLayout emptyContent;
    @BindView(R.id.empty_no_certificates)
    ViewGroup emptyCertificates;
//    @BindView(R.id.progress_bar_content)
//    ProgressBar progressBarContent;

    @BindView(R.id.layout_profile_image)
    ViewGroup mLayoutProfileImage;

    private String tempId;
    private LinearLayoutManager layoutManager;
    private ProfiletrainerImageAdaper profiletrainerImageAdaper;
    private ProfileTrainerDetailAdaper profileTrainerDetailAdaper;
    private EndlessScrollListener endlessScrollListener;

    TutorialManager tutorialManager;

//    private FragmentProfilePresenter fpPresenter;
 
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);

        tutorialManager = new TutorialManager(getActivity());

        return view;
    }
    public static FragmentProfile newInstance(){
        FragmentProfile fragmentProfile = new FragmentProfile();
        return fragmentProfile;
    }

    @Override
    public void displayImagesAndCertificates(DetailTrainerModel model){

        List<TrainerImageModel> trainerImages = model.getListTrainerImageModel();
        List<TrainerCertificateModel> trainerCerts = model.getListtrainer_certificate();

        //Show empty profile message if trainer has no images / certificates
        if(trainerImages.isEmpty() && trainerCerts.isEmpty()){
            showEmptyProfile();
        } else if (trainerCerts.isEmpty()){
            showEmptyCertificates();
        } else if (trainerImages.isEmpty()){
            hideLayoutImage();
        }
            configureRvAdapterProfileTrainerDetailImage(trainerImages);
            configureRvAdapterProfileTrainerDetailDetail(trainerCerts);

    }

    private void hideLayoutImage(){
        emptyContent.setVisibility(View.GONE);
        emptyCertificates.setVisibility(View.GONE);
        mLayoutProfileImage.setVisibility(View.GONE);
    }

    private void showEmptyCertificates() {
        emptyContent.setVisibility(View.GONE);
        emptyCertificates.setVisibility(View.VISIBLE);
        rvProfileCertificates.setVisibility(View.GONE);
    }

    private void showEmptyProfile(){
        emptyContent.setVisibility(View.VISIBLE);
        emptyCertificates.setVisibility(View.GONE);
        mLayoutProfileImage.setVisibility(View.GONE);
        rvProfileCertificates.setVisibility(View.GONE);
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    @Override
    public void showLoading() {
//        progressBarContent.setVisibility(View.VISIBLE);
        emptyContent.setVisibility(View.GONE);
        rvProfileCertificates.setVisibility(View.VISIBLE);
        rvProfileImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissLoading() {
//        progressBarContent.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetError() {
        dismissLoading();
        showGenericError(getString(R.string.no_internet));
    }

    private void configureRvAdapterProfileTrainerDetailImage(final List<TrainerImageModel> listTrainerImageModel){

            layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
            rvProfileImage.setLayoutManager(layoutManager);
            profiletrainerImageAdaper = new ProfiletrainerImageAdaper();
            rvProfileImage.post(() -> profiletrainerImageAdaper.refresh(listTrainerImageModel));
            rvProfileImage.setAdapter(profiletrainerImageAdaper);

            endlessScrollListener = new EndlessScrollListener(layoutManager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    rvProfileImage.post(() -> profiletrainerImageAdaper.refresh(listTrainerImageModel));
                }
            };
            rvProfileImage.addOnScrollListener(endlessScrollListener);
//        }
    }

    private void configureRvAdapterProfileTrainerDetailDetail(final List<TrainerCertificateModel> listTrainerCertificateModel ){
        layoutManager = new LinearLayoutManager(getContext());
        rvProfileCertificates.setLayoutManager(layoutManager);
        profileTrainerDetailAdaper = new ProfileTrainerDetailAdaper();
        profileTrainerDetailAdaper.addListTrainerCertificateModel(listTrainerCertificateModel);
        rvProfileCertificates.setAdapter(profileTrainerDetailAdaper);

        endlessScrollListener = new EndlessScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                profileTrainerDetailAdaper.addListTrainerCertificateModel(listTrainerCertificateModel);
            }
        };
        rvProfileCertificates.addOnScrollListener(endlessScrollListener);
    }
}