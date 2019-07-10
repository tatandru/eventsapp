package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.TimeZone;

public class Dates implements Serializable {

    @SerializedName("start")
    private StartDate startDate;

    @SerializedName("timezone")
    private String eventTimeZone;

    public Dates(StartDate startDate, String eventTimeZone) {
        this.startDate = startDate;
        this.eventTimeZone = eventTimeZone;
    }


    public StartDate getStartDate() {
        return startDate;
    }

    public void setStartDate(StartDate startDate) {
        this.startDate = startDate;
    }

    public String getEventTimeZone() {
        return eventTimeZone;
    }

    public void setEventTimeZone(String eventTimeZone) {
        this.eventTimeZone = eventTimeZone;
    }

    /**
     * Getter and Setter
     */


    @Override
    public String toString() {
        return "Dates{" +
                "startDate='" + startDate + '\'' +
                ", eventTimeZone='" + eventTimeZone + '\'' +
                '}';
    }
}
