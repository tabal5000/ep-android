package com.example.tabs.ep_rest;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabs.ep_rest.api.resource.MyAppService;
import com.example.tabs.ep_rest.model.Item;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tabs on 12/01/2018.
 */

public class ItemDetailActivity extends AppCompatActivity implements Callback<Item> {

    private Item item;
    private TextView tvDescription;
    private TextView tvTitle;
    private TextView tvPrice;
    private MyAppService myAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        myAppService = ApiUtils.getMyAppService();
        tvDescription = (TextView) findViewById(R.id.tv_description);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvPrice = findViewById(R.id.tv_price);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int id = getIntent().getIntExtra("item.id", 0);
        if (id > 0) {
            myAppService.get(id).enqueue(this);
        }
    }

    @Override
    public void onResponse(Call<Item> call, Response<Item> response) {
        item = response.body();
        Log.i("ItemDetailActivity", "Result item title: " + item.getTitle());
        Log.i("ItemDetailActivity", "Result item description: " + item.getDescription());
        Log.i("ItemDetailActivity", "Result item price:  " + item.getPrice());
        if (response.isSuccessful()) {
            tvDescription.setText(item.getDescription());
            tvTitle.setText(item.getTitle());
            tvPrice.setText(String.format(Locale.ENGLISH, "%.2f EUR", item.getPrice()));
        } else {
            String errorMessage;
            try {
                errorMessage = "An error occurred: " + response.errorBody().string();
            } catch (IOException e) {
                errorMessage = "An error occurred: error while decoding the error message.";
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            Log.e("ItemDetailActivity", errorMessage);
        }
    }

    @Override
    public void onFailure(Call<Item> call, Throwable t) {
        Toast.makeText(this, "Something went wrong :(", Toast.LENGTH_LONG).show();
        Log.w("ItemDetailActivity", "Error: " + t.getMessage(), t);
    }
}
