package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Segment implements Serializable {

    @SerializedName("name")
    private String eventType;


    public Segment(String eventType) {
        this.eventType = eventType;
    }

    /**
     * Getter and Setter
     */

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "Segment{" +
                "eventType='" + eventType + '\'' +
                '}';
    }
}
