package com.ovchinnikovm.android.vktop.entities;

import java.util.List;


// This class is used in gson deserialization (CTRL + click on class for more).
public class GroupResponse {

    public Response response;

    public class Response {

        public int count;
        public List<Group> items;
    }

}
