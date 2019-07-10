package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BaseRouteEvents implements Serializable {

    @SerializedName("_embedded")
    private Embedded embedded;


    public BaseRouteEvents(Embedded embedded) {
        this.embedded = embedded;
    }

    public BaseRouteEvents() {

    }


    /**
     * Getter and Setter
     */

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    @Override
    public String toString() {
        return "BaseRouteEvents{" +
                "embedded=" + embedded +
                '}';
    }
}
