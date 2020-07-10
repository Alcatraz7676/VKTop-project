package com.ovchinnikovm.android.vktop.groups.events;

import com.ovchinnikovm.android.vktop.entities.Group;

import java.util.List;

public class GroupsEvent {
    private String error;
    private List<Group> groups;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> images) {
        this.groups = images;
    }
}
