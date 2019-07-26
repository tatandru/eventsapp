package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Image implements Serializable {

    @SerializedName("url")
    private String imageURL;

    private int width;

    private int height;

    public Image(String imageURL, int width, int height) {
        this.imageURL = imageURL;
        this.width = width;
        this.height = height;
    }

    /**
     * Getter and Setter
     */
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    @Override
    public String toString() {
        return "Image{" +
                "imageURL='" + imageURL + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
