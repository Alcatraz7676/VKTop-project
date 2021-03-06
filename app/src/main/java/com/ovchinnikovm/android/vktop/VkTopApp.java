package com.ovchinnikovm.android.vktop;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.MobileAds;
import com.ovchinnikovm.android.vktop.group.di.DaggerGroupComponent;
import com.ovchinnikovm.android.vktop.group.di.GroupComponent;
import com.ovchinnikovm.android.vktop.group.di.GroupModule;
import com.ovchinnikovm.android.vktop.group.ui.GroupView;
import com.ovchinnikovm.android.vktop.groups.di.DaggerGroupsComponent;
import com.ovchinnikovm.android.vktop.groups.di.GroupsComponent;
import com.ovchinnikovm.android.vktop.groups.di.GroupsModule;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemLongClickListener;
import com.ovchinnikovm.android.vktop.main.di.DaggerMainComponent;
import com.ovchinnikovm.android.vktop.main.di.MainComponent;
import com.ovchinnikovm.android.vktop.main.di.MainModule;
import com.ovchinnikovm.android.vktop.posts.di.DaggerPostsComponent;
import com.ovchinnikovm.android.vktop.posts.di.PostsComponent;
import com.ovchinnikovm.android.vktop.posts.di.PostsModule;
import com.ovchinnikovm.android.vktop.posts.ui.PostsView;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;

public class VkTopApp extends MultiDexApplication {

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

        Fabric.with(this, new Crashlytics());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        Realm.init(this);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        instance = (VkTopApp) getApplicationContext();
        refWatcher = LeakCanary.install(this);

        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);

        MobileAds.initialize(this, "ca-app-pub-5717076824212218~7622351649");
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

    public MainComponent getMainComponent(Activity activity, OnItemClickListener clickListener,
                                          OnItemLongClickListener longClickListener) {
        return DaggerMainComponent
                .builder()
                .libsModule(new LibsModule(activity))
                .mainModule(new MainModule(clickListener, longClickListener))
                .build();
    }
}
