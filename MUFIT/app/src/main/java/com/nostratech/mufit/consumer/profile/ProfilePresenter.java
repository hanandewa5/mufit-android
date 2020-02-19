package com.nostratech.mufit.consumer.profile;

import android.content.Context;

import com.google.gson.JsonObject;
import com.nostratech.mufit.consumer.base.MyBasePresenter;
import com.nostratech.mufit.consumer.model.ProfileModel;
import com.nostratech.mufit.consumer.model.ProfileUpdateRequestModel;
import com.nostratech.mufit.consumer.service.ApiClient;
import com.nostratech.mufit.consumer.service.ApiService;
import com.nostratech.mufit.consumer.utils.callback.SimpleCallback;

import java.io.File;

import id.mufit.core.base.MvpView;
import id.mufit.core.network.RetrofitCallback;
import id.mufit.core.network.models.StandardResponseModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

class ProfilePresenter extends MyBasePresenter implements ProfileContract.Presenter {

    private ProfileContract.View view;

    ProfilePresenter(Context context, MvpView mvpView, ProfileContract.View view) {
        super(context, mvpView);
        this.view = view;
    }

    @Override
    public void getProfileDetail() {
        if (isConnectedToInternet()) {
            getMvpView().showLoading();
            Call<StandardResponseModel> call = getApiService().getProfileDetail(getAccessToken());
            call.enqueue(new RetrofitCallback<>(this, response -> {
                JsonObject jsonObject = getGson().toJsonTree(response.body().getResult())
                        .getAsJsonObject();
                ProfileModel profileModel = getGson().fromJson(jsonObject.toString(),
                        ProfileModel.class);
                view.showProfileDetail(profileModel);

                //Determine if user is eligible to check their BCM
                if(profileModel.getProfileB2BId() != null){
                    view.showButtonCheckBcm(profileModel.getProfileB2BId());
                } else {
                    view.hideButtonCheckBcm();
                }

                getMvpView().dismissLoading();
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    @Override
    public void initiateUpdateProcess(String id, String fullName, String phone, String email,
                                      File profileImage, Integer version) {
        getMvpView().showLoading();

        //Check if user updated the profile image
        //If so, upload image first then use resulting imageUrl to update user profile
        if (profileImage != null) {
            uploadImageToServer(profileImage, imageUrl -> updateProfile(null,
                    null, fullName, id,
                    null, null, phone, imageUrl, version));
        }


        //Else, update user profile right away
        else {
            updateProfile(null, null, fullName, id,
                    null, null, phone, null, version);
        }
    }

    @Override
    public void updateProfile(String address, String currentPassword,
                              String fullName, String id, String newPassword,
                              String newPasswordConfirmation, String phone, String photoSelfie, Integer version) {

        if (isConnectedToInternet()) {
            Call<StandardResponseModel> call = getApiService().updateProfile(getAccessToken(), new ProfileUpdateRequestModel
                    (address, currentPassword, fullName, id, newPassword, newPasswordConfirmation, phone,
                            photoSelfie, version));
            call.enqueue(new RetrofitCallback<>(this, response -> {
                view.showEditProfileSuccessMessage();

                //Update cache
                getmAppCache().setPhoneNumber(phone);
                getmAppCache().setUserFullName(fullName);
            }));
        } else {
            getMvpView().showNoInternetError();
        }
    }

    private void uploadImageToServer(File f, SimpleCallback<String> onUploadSuccess) {
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", f.getName(), reqFile);
        ApiService apiService = ApiClient.createService(ApiService.class);
        Call<StandardResponseModel> call = apiService.uploadProfileImageCustomer(body, getAccessToken());
        call.enqueue(new RetrofitCallback<>(this, response -> {
            String imageUrl = getGson().toJsonTree(response.body().getResult()).getAsString();
            onUploadSuccess.call(imageUrl);
        }));
    }
}
