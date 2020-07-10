package com.ovchinnikovm.android.vktop.entities;


// This class is used in sorting
public class PostSortItem {
    private Integer id;
    private SocialValue likes;
    private SocialValue reposts;
    private SocialValue comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "PostSortItem{" +
                "id=" + id +
                ", likes=" + likes +
                ", reposts=" + reposts +
                ", comments=" + comments +
                '}';
    }
}
