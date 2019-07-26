package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Venues implements Serializable {

    @SerializedName("name")
    private String eventLocation;

    @SerializedName("city")
    private City city;

    @SerializedName("address")
    private Address address;


    public Venues(String eventLocation, City city, Address address) {
        this.eventLocation = eventLocation;
        this.city = city;
        this.address = address;
    }

    /**
     * Getter and Setter
     */

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Venues{" +
                "eventLocation='" + eventLocation + '\'' +
                ", city=" + city +
                ", address=" + address +
                '}';
    }
}
