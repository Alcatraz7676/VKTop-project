package com.ovchinnikovm.android.vktop.groups;

import com.ovchinnikovm.android.vktop.groups.events.GroupsEvent;

public interface GroupsPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getGroups();
    void onEventMainThread(GroupsEvent event);
}
