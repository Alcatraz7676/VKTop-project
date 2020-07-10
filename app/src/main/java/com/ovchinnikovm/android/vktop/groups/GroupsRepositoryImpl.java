package com.ovchinnikovm.android.vktop.groups;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.entities.GroupResponse;
import com.ovchinnikovm.android.vktop.groups.events.GroupsEvent;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class GroupsRepositoryImpl implements GroupsRepository {
    private EventBus eventBus;

    public GroupsRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getGroups() {
        VKRequest vkRequest = new VKApiGroups()
                .get(VKParameters
                        .from(VKApiConst.USER_ID, VKAccessToken.currentToken().userId,
                                VKApiConst.EXTENDED, 1,
                                VKApiConst.FIELDS, "activity,members_count,status"));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                Gson gson = new GsonBuilder()
                        .create();
                GroupResponse groupResponse = gson
                        .fromJson(response.responseString, GroupResponse.class);
                List<Group> items = new ArrayList<>();
                items.addAll(groupResponse.response.items);
                post(items);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                post(error.toString());
            }
        });
    }

    private void post(List<Group> items) {
        post(items, null);
    }

    private void post(String error) {
        post(null, error);
    }

    private void post(List<Group> items, String error) {
        GroupsEvent event = new GroupsEvent();
        event.setError(error);
        event.setGroups(items);
        eventBus.postSticky(event);
    }
}
