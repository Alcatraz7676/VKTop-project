package com.ovchinnikovm.android.vktop.entities;

import java.util.List;

public class PostsResponse {

    public Response response;

    public class Response {

        public int count;
        public List<Post> items;
    }
}
