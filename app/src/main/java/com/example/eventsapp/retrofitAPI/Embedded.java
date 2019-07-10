package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Embedded implements Serializable {

    @SerializedName("events")
    private List<Event> eventList;

    public Embedded(List<Event> eventList) {
        this.eventList = eventList;
    }


    /**
     * Getter and Setter
     */

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public String toString() {
        return "Embedded{" +
                "eventList=" + eventList +
                '}';
    }
}
