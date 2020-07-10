package com.ovchinnikovm.android.vktop.group;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ovchinnikovm.android.vktop.entities.PostsQuantity;
import com.ovchinnikovm.android.vktop.entities.PostsResponse;
import com.ovchinnikovm.android.vktop.group.events.GroupEvent;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiWall;

import org.greenrobot.eventbus.EventBus;

public class GroupRepositoryImpl implements GroupRepository{
    private EventBus eventBus;

    public GroupRepositoryImpl(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void getPostsCount(Integer groupId) {
        VKRequest vkRequest = new VKApiWall()
                .get(VKParameters
                        .from(VKApiConst.OWNER_ID, "-" + groupId,
                                VKApiConst.COUNT, 1));
        vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                Gson gson = new GsonBuilder()
                        .create();
                PostsResponse<PostsQuantity> postsResponse = gson
                        .fromJson(response.responseString, new TypeToken<PostsResponse<PostsQuantity>>(){}.getType());
                post(postsResponse.response.count);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
                post(error.toString());
            }
        });
    }

    private void post(int postsCount) {
        post(postsCount, null);
    }

    private void post(String error) {
        post(0, error);
    }

    private void post(int postsCount, String error) {
        GroupEvent event = new GroupEvent();
        event.setError(error);
        event.setPostsNumber(postsCount);
        eventBus.post(event);
    }
}
