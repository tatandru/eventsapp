package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class PublicSales implements Serializable {

    @SerializedName("startDateTime")
    private String startDateTime;

    @SerializedName("endDateTime")
    private String endDateTime;


    public PublicSales(String startDateTime, String endDateTime) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Getter and Setter
     */


    @Override
    public String toString() {
        return "PublicSales{" +
                "startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                '}';
    }
}
