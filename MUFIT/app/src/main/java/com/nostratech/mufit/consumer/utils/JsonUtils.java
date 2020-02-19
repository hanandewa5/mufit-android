package com.nostratech.mufit.consumer.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import id.mufit.core.network.models.StandardResponseModel;
import retrofit2.Response;

public class JsonUtils {

    public static <T> List<T> parseJsonArray(Gson gson,
                                                  Response<StandardResponseModel> response,
                                                  Class<T> className){
        JsonArray jsonArray = gson.toJsonTree(response.body().getResult()).getAsJsonArray();

        List<T> list = new ArrayList<T>();
        for(final JsonElement json : jsonArray){
            T entity = gson.fromJson(json, className);
            list.add(entity);
        }
        return list;
    }

    public static JsonArray getResultAsArray(Gson gson, Response<StandardResponseModel> response){
        return gson.toJsonTree(response.body().getResult()).getAsJsonArray();
    }

    public static <T> Type getCollectionWithTypeOf(Class<T> objectClass ){
        return new TypeToken<Collection<T>>() {}.getType();
    }

}
