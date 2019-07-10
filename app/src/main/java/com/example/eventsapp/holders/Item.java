package com.example.eventsapp.holders;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

    List<String> item;


    public Item() {
        // ---
    }

    public Item(List<String> item) {

        this.item = item;
    }


    public List<String> getItem() {
        return item;
    }

    public void setItem(List<String> item) {
        this.item = item;
    }
}
