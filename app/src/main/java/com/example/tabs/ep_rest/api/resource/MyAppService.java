package com.example.tabs.ep_rest.api.resource;

import com.example.tabs.ep_rest.model.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tabs on 12/01/2018.
 */

public interface MyAppService {

    @GET("items")
    Call<List<Item>> getItems();

    @GET("items/{id}")
    Call<Item> get(@Path("id") int id);
}
