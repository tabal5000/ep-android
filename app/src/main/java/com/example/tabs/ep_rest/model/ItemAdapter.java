package com.example.tabs.ep_rest.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.tabs.ep_rest.R;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tabs on 12/01/2018.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    public ItemAdapter(Context context) {
        super(context, 0, new ArrayList<Item>());
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Item item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemlist_element, parent, false);
        }

        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        final TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_price);

        tvTitle.setText(item.getTitle());
        tvPrice.setText(String.format(Locale.ENGLISH, "%.2f EUR", item.getPrice()));

        return convertView;
    }
}

