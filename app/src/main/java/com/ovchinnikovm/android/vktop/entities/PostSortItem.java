package com.ovchinnikovm.android.vktop.entities;


// This class is used in sorting
public class PostSortItem {
    private Integer id;
    private SocialValue likes;
    private SocialValue reposts;
    private SocialValue comments;
    private Integer date;

    public PostSortItem(Integer id, SocialValue likes, SocialValue reposts, SocialValue comments) {
        this.id = id;
        this.likes = likes;
        this.reposts = reposts;
        this.comments = comments;
    }

    public PostSortItem(Integer id, SocialValue likes, SocialValue reposts,
                            SocialValue comments, Integer date) {
        this.id = id;
        this.likes = likes;
        this.reposts = reposts;
        this.comments = comments;
        this.date = date;
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

    public Integer getDate() {
        return date;
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
