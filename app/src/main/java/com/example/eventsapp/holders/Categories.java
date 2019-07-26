package com.example.eventsapp.holders;

import java.util.List;

public class Categories {

    List<String> categories;
    List<String> imagesURL;


    public Categories() {
        // ---
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getImagesURL() {
        return imagesURL;
    }

    public void setImagesURL(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }
}
