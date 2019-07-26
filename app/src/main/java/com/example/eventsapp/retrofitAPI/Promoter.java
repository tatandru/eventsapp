package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Promoter implements Serializable {

    @SerializedName("name")
    private String promoterName;

    public Promoter(String promoterName) {
        this.promoterName = promoterName;
    }

    /**
     * Getter and Setter
     */

    public String getPromoterName() {
        return promoterName;
    }

    public void setPromoterName(String promoterName) {
        this.promoterName = promoterName;
    }

    @Override
    public String toString() {
        return "Promoter{" +
                "promoterName='" + promoterName + '\'' +
                '}';
    }
}
