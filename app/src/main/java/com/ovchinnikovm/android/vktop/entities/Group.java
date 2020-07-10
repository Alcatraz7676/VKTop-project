package com.ovchinnikovm.android.vktop.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


// This class contains information about the group in it.
public class Group implements Serializable {
    private Integer id;
    private String name;
    @SerializedName("activity")
    private String subName;
    @SerializedName("photo_100")
    private String smallPhotoUrl;
    @SerializedName("photo_200")
    private String photoURL;
    @SerializedName("members_count")
    private Integer members;
    private String status;

    public String getSmallPhotoUrl() {
        return smallPhotoUrl;
    }

    public void setSmallPhotoUrl(String smallPhotoUrl) {
        this.smallPhotoUrl = smallPhotoUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getMembers() {
        return members;
    }

    public void setMembers(Integer members) {
        this.members = members;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*
    @Override
    public String toString() {
        return "Id: " + getId() + ", " + "Name: " + getName() + ", " + "SubName: " + getSubName() +
                ", " + "Photo URL: " + getPhotoURL() + ", Members count: " + getMembers() +
                ", Status: " + ".";
    }
    */
}
