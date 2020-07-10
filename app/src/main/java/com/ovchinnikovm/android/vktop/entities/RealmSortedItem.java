package com.ovchinnikovm.android.vktop.entities;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


public class RealmSortedItem extends RealmObject {
    @PrimaryKey
    @Required
    private Integer sortId;
    private String groupId;
    private Integer postsCount;
    private String groupIconUrl;
    private String groupName;
    // For example sortRange 14.03.2016 - 26.08.2017
    private String sortRange;

    private RealmList<PostSortItem> byLikes = new RealmList<>();
    private RealmList<PostSortItem> byShares = new RealmList<>();
    private RealmList<PostSortItem> byComments = new RealmList<>();

    public RealmSortedItem() {
    }

    public RealmSortedItem(String groupId, Integer postsCount, String groupIconUrl, String groupName) {
        this.groupId = groupId;
        this.postsCount = postsCount;
        this.groupIconUrl = groupIconUrl;
        this.groupName = groupName;
    }

    public int getItemId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getSortRange() {
        return sortRange;
    }

    public void setSortRange(String sortRange) {
        this.sortRange = sortRange;
    }

    public String getGroupId() {
        return groupId;
    }

    public Integer getPostsCount() {
        return postsCount;
    }

    public String getGroupIconUrl() {
        return groupIconUrl;
    }

    public String getGroupName() {
        return groupName;
    }

    public RealmList<PostSortItem> getByLikes() {
        return byLikes;
    }

    public RealmList<PostSortItem> getByShares() {
        return byShares;
    }

    public RealmList<PostSortItem> getByComments() {
        return byComments;
    }

    public void addByLikes(List<PostSortItem> byLikes) {
        this.byLikes.addAll(byLikes);
    }

    public void addByShares(List<PostSortItem> byShares) {
        this.byShares.addAll(byShares);
    }

    public void addByComments(List<PostSortItem> byComments) {
        this.byComments.addAll(byComments);
    }
}
