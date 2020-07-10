package com.ovchinnikovm.android.vktop.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NestedPost {
    public List<Attachment> attachments;
    private Integer id;
    private Integer date;
    @SerializedName("signer_id")
    private Integer authorId;
    private String text;
    @SerializedName("owner_id")
    private Integer groupId;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
