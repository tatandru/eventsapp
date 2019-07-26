package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Sales implements Serializable {

    @SerializedName("public")
    private PublicSales publicSalesDataSales;


    public Sales(PublicSales publicSalesDataSales) {
        this.publicSalesDataSales = publicSalesDataSales;
    }


    /**
     * Getter and Setter
     */

    public PublicSales getPublicSalesDataSales() {
        return publicSalesDataSales;
    }

    public void setPublicSalesDataSales(PublicSales publicSalesDataSales) {
        this.publicSalesDataSales = publicSalesDataSales;
    }


    @Override
    public String toString() {
        return "Sales{" +
                "publicSalesDataSales=" + publicSalesDataSales +
                '}';
    }
}
