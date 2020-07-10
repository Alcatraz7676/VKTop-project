package com.ovchinnikovm.android.vktop.groups.ui;

import com.ovchinnikovm.android.vktop.entities.Group;

import java.util.List;

public interface GroupsView {
    void onError(String error);
    void setGroups(List<Group> groups);
}