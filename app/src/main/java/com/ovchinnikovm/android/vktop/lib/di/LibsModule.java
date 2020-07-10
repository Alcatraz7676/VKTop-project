package com.ovchinnikovm.android.vktop.lib.di;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import com.ovchinnikovm.android.vktop.LoginActivity;
import com.ovchinnikovm.android.vktop.lib.PicassoImageLoader;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LibsModule {
    private Activity activityContext;

    public LibsModule(Activity activityContext) {
        this.activityContext = activityContext;
    }

    @Provides
    @Singleton
    Context providesActivity() {
        return this.activityContext;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(Picasso picasso, Context context) {
        return new PicassoImageLoader(picasso, context);
    }

    @Provides
    @Singleton
    Picasso providesLibraryImageLoader() {
        return Picasso.get();
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return EventBus.getDefault();
    }

    @Provides
    @Named("userId")
    @Singleton
    Integer providesUserId() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activityContext);
        return sharedPreferences.getInt(LoginActivity.KEY_PREF_CURRENT_USER, 0);
    }
}
