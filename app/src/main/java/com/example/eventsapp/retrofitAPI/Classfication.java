package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Classfication implements Serializable {

    @SerializedName("segment")
    private Segment segment;

    public Classfication(Segment segment) {
        this.segment = segment;
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

    @Override
    public String toString() {
        return "Classfication{" +
                "segment=" + segment +
                '}';
    }
}
