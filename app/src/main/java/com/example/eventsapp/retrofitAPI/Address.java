package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Address implements Serializable {

    @SerializedName("line1")
    private String eventLocation;


    public Address(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    /**
     * Getter and Setter
     */

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    @Override
    public String toString() {
        return "Address{" +
                "eventLocation='" + eventLocation + '\'' +
                '}';
    }
}
