package com.example.tabs.ep_rest;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tabs.ep_rest.api.resource.MyAppService;
import com.example.tabs.ep_rest.model.Item;
import com.example.tabs.ep_rest.model.ItemAdapter;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements Callback<List<Item>> {

    private ListView list;
    private ItemAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private MyAppService myAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myAppService = ApiUtils.getMyAppService();
        list = findViewById(R.id.items);
        adapter = new ItemAdapter(this);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Item item = adapter.getItem(i);
                if (item != null) {
                    final Intent intent = new Intent(MainActivity.this,ItemDetailActivity.class);
                    intent.putExtra("item.id", item.getId());
                    startActivity(intent);
                }
            }
        });

        refreshLayout = findViewById(R.id.container);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAppService.getItems().enqueue(MainActivity.this);
            }
        });

        myAppService.getItems().enqueue(MainActivity.this);
    }

    @Override
    public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
        final List<Item> hits = response.body();

        if (response.isSuccessful()) {
            Log.i("MainActivity", "Hits: " + hits.size());
            adapter.clear();
            adapter.addAll(hits);
        } else {
            String errorMessage;
            try {
                errorMessage = "An error occurred: " + response.errorBody().string();
            } catch (IOException e) {
                errorMessage = "An error occurred: error while decoding the error message.";
            }
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            Log.e("MainActivity", errorMessage);
        }
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onFailure(Call<List<Item>> call, Throwable t) {
        Toast.makeText(this,"Something went wrong :(",LENGTH_LONG);
        refreshLayout.setRefreshing(false);
        Log.e("MainActivity","Something went wrong :(",t);
    }
}
