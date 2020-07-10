package com.ovchinnikovm.android.vktop.model;

import java.util.List;

public class PostsResponse {

    public Response response;

    public class Response {

        //public int count;
        public List<Post> items;

        public class Post {
            //public int id;
            //public int from_id;
            //public int date;
            public String text;
            //public String post_type;

            //public int reply_post_id;
        }
    }
}
