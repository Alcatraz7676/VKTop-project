package com.ovchinnikovm.android.vktop.entities;


import java.util.List;

// This is a generic class which is used in gson deserialization (CTRL + click on class for more).
public class PostsApiResponse<T> {
    public List<T> response;
}
