package com.example.tabs.ep_rest.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tabs.ep_rest.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Tabs on 12/01/2018.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private Context context;
    public ItemAdapter(Context context) {
        super(context, 0, new ArrayList<Item>());
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        final Item item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.itemlist_element, parent, false);
        }

        final TextView tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imgView);
        final TextView tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
        final TextView tvDescription = (TextView) convertView.findViewById(R.id.tv_description);

        tvTitle.setText(item.getTitle());
//        if(item.getDescription().length() > 200) {
//            tvDescription.setText(item.getDescription().substring(0,197)+ "...");
//        } else {
//            tvDescription.setText(item.getDescription());
//        }
        tvDescription.setText(item.getDescription());
        final String IMG_URL = "http://10.0.2.2/storage/" + item.getImg();
        Picasso
                .with(context)
                .load(IMG_URL)
                .fit()
                .centerInside()
                .into(imageView);
        tvPrice.setText(String.format(Locale.ENGLISH, "%.2f EUR", item.getPrice()));

        return convertView;
    }
}

