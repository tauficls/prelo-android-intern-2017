package com.taufic.prelo_android_intern.Item;

/**
 * Created by taufic on 4/11/2017.
 */

public class DataItem {
    private String name;
    private String cost;
    private String urlPhoto;
    private String id;

    public DataItem() {
        name = "";
        cost = "";
        urlPhoto = "";
        id = "";
    }
    public DataItem(String name, String cost, String urlPhoto, String id) {
        this.name = name;
        this.cost = cost;
        this.urlPhoto = urlPhoto;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
