package com.ovchinnikovm.android.vktop.entities;

import com.google.gson.annotations.SerializedName;

public class Group {
    private Integer id;
    private String name;
    @SerializedName("activity")
    private String subName;
    @SerializedName("photo_200")
    private String photoURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    @Override
    public String toString() {
        return "Id: " + getId() + ", " + "Name: " + getName() + ", " + "SubName: " + getSubName() +
                ", " + "Photo URL: " + getPhotoURL() + ".";
    }
}
