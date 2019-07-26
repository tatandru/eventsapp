package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Classfication implements Serializable {

    @SerializedName("segment")
    private Segment segment;

    @SerializedName("genre")
    private Genre genre;

    public Classfication(Segment segment, Genre genre) {
        this.segment = segment;
        this.genre = genre;
    }

    /**
     * Getter and Setter
     */

    public Segment getSegment() {
        return segment;
    }

    public void setSegment(Segment segment) {
        this.segment = segment;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Classfication{" +
                "segment=" + segment +
                ", genre=" + genre +
                '}';
    }
}
