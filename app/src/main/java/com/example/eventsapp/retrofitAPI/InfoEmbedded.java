package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class InfoEmbedded implements Serializable {

    @SerializedName("venues")
    private List<Venues> venues;


    public InfoEmbedded(List<Venues> venues) {
        this.venues = venues;
    }

    /**
     * Getter and Setter
     */

    public List<Venues> getVenues() {
        return venues;
    }

    public void setVenues(List<Venues> venues) {
        this.venues = venues;
    }


    @Override
    public String toString() {
        return "InfoEmbedded{" +
                "venues=" + venues +
                '}';
    }
}
