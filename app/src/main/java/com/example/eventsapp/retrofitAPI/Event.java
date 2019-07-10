package com.example.eventsapp.retrofitAPI;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {

    @SerializedName("name")
    private String eventName;

    @SerializedName("images")
    private List<Image> imgList;

    @SerializedName("sales")
    private Sales sales;

    @SerializedName("dates")
    private Dates dates;

    @SerializedName("classifications")
    private List<Classfication> classficationList;

    @SerializedName("promoter")
    private Promoter promoter;

    @SerializedName("priceRanges")
    private List<PriceRange> priceRangeList;

    @SerializedName("_embedded")
    private InfoEmbedded infoEmbedded;


    public Event(){}

    public Event(String eventName, List<Image> imgList,
                 Sales sales, Dates dates, List<Classfication> classficationList, Promoter promoter, List<PriceRange> priceRangeList,
                 InfoEmbedded infoEmbedded) {
        this.eventName = eventName;
        this.imgList = imgList;
        this.sales = sales;
        this.dates = dates;
        this.classficationList = classficationList;
        this.promoter = promoter;
        this.priceRangeList = priceRangeList;
        this.infoEmbedded = infoEmbedded;
    }

    /**
     * Getter and Setter
     */

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<Image> getImgList() {
        return imgList;
    }

    public void setImgList(List<Image> imgList) {
        this.imgList = imgList;
    }

    public Sales getSales() {
        return sales;
    }

    public void setSales(Sales sales) {
        this.sales = sales;
    }

    public Dates getDates() {
        return dates;
    }

    public void setDates(Dates dates) {
        this.dates = dates;
    }

    public List<Classfication> getClassficationList() {
        return classficationList;
    }

    public void setClassficationList(List<Classfication> classficationList) {
        this.classficationList = classficationList;
    }

    public Promoter getPromoter() {
        return promoter;
    }

    public void setPromoter(Promoter promoter) {
        this.promoter = promoter;
    }

    public List<PriceRange> getPriceRangeList() {
        return priceRangeList;
    }

    public void setPriceRangeList(List<PriceRange> priceRangeList) {
        this.priceRangeList = priceRangeList;
    }

    public InfoEmbedded getInfoEmbedded() {
        return infoEmbedded;
    }

    public void setInfoEmbedded(InfoEmbedded infoEmbedded) {
        this.infoEmbedded = infoEmbedded;
    }


    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", imgList=" + imgList +
                ", sales=" + sales +
                ", dates=" + dates +
                ", classficationList=" + classficationList +
                ", promoter=" + promoter +
                ", priceRangeList=" + priceRangeList +
                ", infoEmbedded=" + infoEmbedded +
                '}';
    }
}
