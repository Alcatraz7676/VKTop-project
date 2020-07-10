package com.ovchinnikovm.android.vktop.lib.di;

import android.content.Context;

import com.ovchinnikovm.android.vktop.lib.PicassoImageLoader;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class LibsModule {
    private Context context;

    public LibsModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context providesActivity() {
        return this.context;
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(Picasso picasso, Context context) {
        return new PicassoImageLoader(picasso, context);
    }

    @Provides
    @Singleton
    Picasso providesLibraryImageLoader(Context context) {
        return Picasso.with(context);
    }

    @Provides
    @Singleton
    EventBus providesEventBus() {
        return EventBus.getDefault();
    }
}
