package com.ovchinnikovm.android.vktop;

import android.content.Intent;

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

}
