package com.ovchinnikovm.android.vktop.main.di;

import android.util.Log;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemLongClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.SortDataAdapter;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmResults;

@Module
public class MainModule {
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;

    public MainModule(OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @Provides
    @Singleton
    SortDataAdapter providesAdapter(RealmResults<RealmSortedItem> data, ImageLoader imageLoader,
                                    OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        return new SortDataAdapter(data, imageLoader, clickListener, longClickListener);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.clickListener;
    }

    @Provides
    @Singleton
    OnItemLongClickListener providesOnItemLongClickListener() {
        return this.longClickListener;
    }

    @Provides
    @Singleton
    RealmResults<RealmSortedItem> providesItemsList(@Named("userId") Integer userId) {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RealmSortedItem.class).equalTo("userId", userId).sort("sortId").findAll();
    }
}
