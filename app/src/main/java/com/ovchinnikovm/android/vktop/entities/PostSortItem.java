package com.ovchinnikovm.android.vktop.entities;


// This class is used in sorting
public class PostSortItem {
    private Integer id;
    private SocialValue likes;
    private SocialValue reposts;
    private SocialValue comments;

    public PostSortItem(Integer id, SocialValue likes, SocialValue reposts, SocialValue comments) {
        this.id = id;
        this.likes = likes;
        this.reposts = reposts;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
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
