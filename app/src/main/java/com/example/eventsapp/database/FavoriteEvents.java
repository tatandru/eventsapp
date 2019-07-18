package com.example.eventsapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FavoriteEvents implements Serializable {
    @NonNull
    @PrimaryKey
    private String eventName;

    @ColumnInfo
    private String startDate;

    @ColumnInfo
    private String endDate;

    @ColumnInfo
    private Double minPrice;

    @ColumnInfo
    private Double maxPrice;

    @ColumnInfo
    private String Currency;

    @ColumnInfo
    private String urlImg;

    @ColumnInfo
    private String cityName;

    @ColumnInfo
    private String address;

    @ColumnInfo
    private String country;

    public FavoriteEvents(String eventName, String urlImg, String startDate) {
        this.eventName = eventName;
        this.urlImg = urlImg;
        this.startDate = startDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String currency) {
        Currency = currency;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
