package com.papertracker;

import java.util.ArrayList;

public class Item {
    private String name;
    private String details;

    private ArrayList<Item> items;

    // private Image image;

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
