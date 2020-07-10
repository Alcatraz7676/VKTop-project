package com.ovchinnikovm.android.vktop.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;


// This class is responsible for containing reply of the post.
public class NestedPost {
    public List<Attachment> attachments;
    public transient List<String> photos;
    public transient List<Attachment> other;
    private Integer id;
    private Integer date;
    @SerializedName("signer_id")
    private Integer authorId;
    private String text;
    @SerializedName("owner_id")
    private Integer groupId;
    private transient String iconUrl;
    private transient String replyName;
    private transient String authorFullname;

    public String getAuthorFullname() {
        return authorFullname;
    }

    public void setAuthorFullname(String authorFullname) {
        this.authorFullname = authorFullname;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

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
