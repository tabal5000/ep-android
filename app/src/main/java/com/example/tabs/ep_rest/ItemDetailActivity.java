package com.example.tabs.ep_rest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tabs.ep_rest.api.resource.MyAppService;
import com.example.tabs.ep_rest.model.Item;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Tabs on 12/01/2018.
 */

public class ItemDetailActivity extends AppCompatActivity implements Callback<Item> {
    private Context context;
    private Item item;
    private TextView tvDescription;
    private TextView tvTitle;
    private TextView tvPrice;
    private ImageView imageView;
    private CollapsingToolbarLayout toolbarLayout;
    private MyAppService myAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        context = getApplicationContext();
        toolbarLayout = findViewById(R.id.toolbar_layout);
        tvDescription = findViewById(R.id.tv_description);
        tvTitle = findViewById(R.id.tv_title);
        imageView = findViewById(R.id.imgView);
        myAppService = ApiUtils.getMyAppService();
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
            final String IMG_URL = "http://10.0.2.2/storage/" + item.getImg();
            Picasso
                    .with(context)
                    .load(IMG_URL)
                    .fit()
                    .centerInside()
                    .into(imageView);

            tvDescription.setText(item.getDescription());
            //toolbarLayout.setTitle(item.getTitle());
            tvTitle.setText(item.getTitle());
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
