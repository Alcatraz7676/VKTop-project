package com.ovchinnikovm.android.vktop.groups;

import com.ovchinnikovm.android.vktop.groups.events.GroupsEvent;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class GroupsPresenterImpl implements GroupsPresenter {
    private EventBus eventBus;
    private GroupsView view;
    private GroupsInteractor interactor;

    public GroupsPresenterImpl(EventBus eventBus, GroupsView view, GroupsInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onStart() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        view = null;
    }

    @Override
    public void getGroups() {
        interactor.execute();
    }

    @Override
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventMainThread(GroupsEvent event) {
        String errorMsg = event.getError();
        if (view != null) {
            if(errorMsg != null) {
                view.onError(errorMsg);
            } else {
                view.setGroups(event.getGroups());
            }
        }
    }
}
