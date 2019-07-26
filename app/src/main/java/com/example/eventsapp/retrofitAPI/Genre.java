package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Genre implements Serializable {

    @SerializedName("name")
    private String eventGenre;

    public Genre(String eventGenre) {
        this.eventGenre = eventGenre;
    }

    /**
     * Getter and Setter
     */
    public String getEventGenre() {
        return eventGenre;
    }

    public void setEventGenre(String eventGenre) {
        this.eventGenre = eventGenre;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "eventGenre='" + eventGenre + '\'' +
                '}';
    }
}
