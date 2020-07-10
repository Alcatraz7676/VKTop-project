package com.ovchinnikovm.android.vktop.group;

import android.widget.ImageView;

import com.ovchinnikovm.android.vktop.group.events.GroupEvent;

public interface GroupPresenter {
    void onResume();
    void onPause();
    void onDestroy();
    void getPostsCount(Integer groupId);
    void loadIcon(ImageView imageView, String URL);
    void onEventMainThread(GroupEvent event);
}
