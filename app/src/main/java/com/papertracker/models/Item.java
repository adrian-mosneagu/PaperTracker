package com.papertracker.models;

import java.util.ArrayList;

public class Item {
    private String name;
    private String details;

    private ArrayList<Document> documents;


    // private Image image;
    private int itemID;

    public Item(String name, String details) {
        this.name = name;
        this.details = details;
    }

    public Item(String name, String details, int id) {
        this.name = name;
        this.details = details;
        this.itemID = id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}