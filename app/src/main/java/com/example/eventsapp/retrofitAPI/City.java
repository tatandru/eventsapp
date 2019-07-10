package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class City implements Serializable {

    @SerializedName("name")
    private String eventCityName;

    public City(String eventCityName) {
        this.eventCityName = eventCityName;
    }

    /**
     * Getter and Setter
     */

    public String getEventCityName() {
        return eventCityName;
    }

    public void setEventCityName(String eventCityName) {
        this.eventCityName = eventCityName;
    }

    @Override
    public String toString() {
        return "City{" +
                "eventCityName='" + eventCityName + '\'' +
                '}';
    }
}
