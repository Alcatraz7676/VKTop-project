package com.ovchinnikovm.android.vktop.groups;

import com.ovchinnikovm.android.vktop.groups.events.GroupsEvent;

public interface GroupsPresenter {
    void onStart();
    void onDestroy();
    void getGroups();
    void getGlobalGroups(String query);
    void onEventMainThread(GroupsEvent event);
}
