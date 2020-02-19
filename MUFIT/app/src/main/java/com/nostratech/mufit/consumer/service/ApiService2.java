package com.nostratech.mufit.consumer.service;

import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService2 {
    @GET(UrlServices.Public.GET_LIST_ACTIVE_TRAINERS)
    Call<StandardResponseModel> testApiCuy();
}
