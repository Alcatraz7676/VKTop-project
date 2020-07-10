package com.ovchinnikovm.android.vktop.entities;

import java.util.List;

public class GroupResponse {

    public Response response;

    public class Response {

        public int count;
        public List<Group> items;
    }

}
