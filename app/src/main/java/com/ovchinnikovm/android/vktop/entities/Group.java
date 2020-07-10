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
    @SerializedName("is_closed")
    private int closed;
    @SerializedName("is_member")
    private int member;
    private String type;

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

    public boolean isClosed() {
        if (closed == 1)
            return true;
        return false;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }

    public boolean isMember() {
        if (member == 1)
            return true;
        return false;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
