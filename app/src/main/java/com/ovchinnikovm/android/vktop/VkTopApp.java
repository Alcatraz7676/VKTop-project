package com.ovchinnikovm.android.vktop;

import android.app.Activity;
import android.content.Intent;

import com.ovchinnikovm.android.vktop.group.di.DaggerGroupComponent;
import com.ovchinnikovm.android.vktop.group.di.GroupComponent;
import com.ovchinnikovm.android.vktop.group.di.GroupModule;
import com.ovchinnikovm.android.vktop.group.ui.GroupView;
import com.ovchinnikovm.android.vktop.groups.di.DaggerGroupsComponent;
import com.ovchinnikovm.android.vktop.groups.di.GroupsComponent;
import com.ovchinnikovm.android.vktop.groups.di.GroupsModule;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.posts.di.DaggerPostsComponent;
import com.ovchinnikovm.android.vktop.posts.di.PostsComponent;
import com.ovchinnikovm.android.vktop.posts.di.PostsModule;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

public class VkTopApp extends android.app.Application {

    private static VkTopApp instance;
    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(VkTopApp.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        }
    };
    private RefWatcher refWatcher;

    public static VkTopApp get() {
        return instance;
    }

    public static RefWatcher getRefWatcher() {
        return VkTopApp.get().refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        instance = (VkTopApp) getApplicationContext();
        refWatcher = LeakCanary.install(this);

        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
    }

    public GroupsComponent getGroupsComponent(Activity activity, GroupsView view,
                                              com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener clickListener) {
        return DaggerGroupsComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .groupsModule(new GroupsModule(view, clickListener))
                .build();
    }

    public GroupComponent getGroupComponent(Activity activity, GroupView view) {
        return DaggerGroupComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .groupModule(new GroupModule(view))
                .build();
    }

    public PostsComponent getPostsComponent(Activity activity, PostsView view,
                                            com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener clickListener) {
        return DaggerPostsComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .postsModule(new PostsModule(view, clickListener))
                .build();
    }

}
