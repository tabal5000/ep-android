package com.example.tabs.ep_rest.model;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Tabs on 12/01/2018.
 */

public class Item implements Serializable {
    private int id;
    private String title;
    private String description;
    private double price;
    private String img;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getImg() {
        return img;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,
                "%d: %s,\n%s,%s, (%.2f EUR)",
                this.getId(), this.getTitle(), this.getDescription(),this.getImg(),this.getPrice());
    }
}
