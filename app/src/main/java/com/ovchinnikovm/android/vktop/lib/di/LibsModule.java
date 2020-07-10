package com.ovchinnikovm.android.vktop.lib.di;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.ovchinnikovm.android.vktop.lib.GlideImageLoader;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LibsModule {
    private Activity activity;

    public LibsModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @Singleton
    Activity providesActivity() {
        return this.activity;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager glideRequestManager) {
        return new GlideImageLoader(glideRequestManager);
    }

    @Provides
    @Singleton
    RequestManager providesLibraryImageLoader(Activity activity) {
        return Glide.with(activity);
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return EventBus.getDefault();
    }
}
