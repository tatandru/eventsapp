package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PriceRange implements Serializable {

    private String type;

    private String currency;

    @SerializedName("min")
    private Double minPrice;

    @SerializedName("max")
    private Double maxPrice;


    public PriceRange(String type, String currency, Double minPrice, Double maxPrice) {
        this.type = type;
        this.currency = currency;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    /**
     * Getter and Setter
     */

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    @Override
    public String toString() {
        return "PriceRange{" +
                "type='" + type + '\'' +
                ", currency='" + currency + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                '}';
    }
}
