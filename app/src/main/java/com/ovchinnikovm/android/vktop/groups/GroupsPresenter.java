package com.ovchinnikovm.android.vktop.groups;

import com.ovchinnikovm.android.vktop.groups.events.GroupsEvent;

public interface GroupsPresenter {
    void onStart();
    void onDestroy();
    void getGroups();
    void onEventMainThread(GroupsEvent event);
}
