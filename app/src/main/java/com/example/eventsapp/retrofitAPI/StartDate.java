package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.SimpleDateFormat;

public class StartDate implements Serializable {

    @SerializedName("localDate")
    private String dayStartEvent;

    @SerializedName("localTime")
    private String hourStartEvent;


    public StartDate(String dayStartEvent, String hourStartEvent) {
        this.dayStartEvent = dayStartEvent;
        this.hourStartEvent = hourStartEvent;
    }

    public String getDayStartEvent() {
        return dayStartEvent;
    }

    public void setDayStartEvent(String dayStartEvent) {
        this.dayStartEvent = dayStartEvent;
    }

    public String getHourStartEvent() {
        return hourStartEvent;
    }

    public void setHourStartEvent(String hourStartEvent) {
        this.hourStartEvent = hourStartEvent;
    }

    /**
     * Getter and Setter
     */


    @Override
    public String toString() {
        return "StartDate{" +
                "dayStartEvent=" + dayStartEvent +
                ", hourStartEvent='" + hourStartEvent + '\'' +
                '}';
    }
}
