package com.example.eventsapp.holders;

import java.util.List;

public class Categories {

    List<String> categories;


    public Categories() {
        // ---
    }

    public Categories(List<String> categories) {

        this.categories = categories;
    }


    public List<String> getItem() {
        return categories;
    }

    public void setItem(List<String> categories) {
        this.categories = categories;
    }
}
