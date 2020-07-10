package com.ovchinnikovm.android.vktop.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;


// This class is used in ExtendedPosts class
public class ExtendedPost {
    private static final String BASE_POST_URL = "https://vk.com/wall";
    public List<Attachment> attachments;
    @SerializedName("copy_history")
    public List<NestedPost> nestedPost;
    public transient List<String> photos;
    public transient List<Attachment> other;
    private Integer id;
    private Integer date;
    @SerializedName("signer_id")
    private Integer authorId;
    private String text;
    @SerializedName("owner_id")
    private Integer groupId;
    private SocialValue likes;
    private SocialValue reposts;
    private SocialValue comments;
    private transient String authorFullname;

    public String getAuthorFullname() {
        return authorFullname;
    }

    public void setAuthorFullname(String authorFullname) {
        this.authorFullname = authorFullname;
    }

    public String getPostUrl () {
        return BASE_POST_URL + groupId + "_" + id;
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

    public Integer getLikes() {
        return likes.getCount();
    }

    public Integer getReposts() {
        return reposts.getCount();
    }

    public Integer getComments() {
        return comments.getCount();
    }

}
