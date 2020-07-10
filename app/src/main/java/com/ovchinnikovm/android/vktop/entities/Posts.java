package com.ovchinnikovm.android.vktop.entities;

import java.util.List;


// This class is used in getting the posts with id and SocialValue in it for further sorting.
public class Posts {
    public List<PostSortItem> items;

    @Override
    public String toString() {
        return "Posts{" +
                "items=" + items +
                '}';
    }
}
