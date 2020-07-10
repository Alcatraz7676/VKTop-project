package com.ovchinnikovm.android.vktop.main.di;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.SortDataAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmResults;

@Module
public class MainModule {
    private OnItemClickListener clickListener;

    public MainModule(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Provides
    @Singleton
    SortDataAdapter providesAdapter(RealmResults<RealmSortedItem> data, ImageLoader imageLoader,
                                    OnItemClickListener clickListener) {
        return new SortDataAdapter(data, imageLoader, clickListener);
    }

    @Provides
    @Singleton
    OnItemClickListener providesOnItemClickListener() {
        return this.clickListener;
    }

    @Provides
    @Singleton
    RealmResults<RealmSortedItem> providesItemsList() {
        Realm realm = Realm.getDefaultInstance();
        return realm.where(RealmSortedItem.class).findAll();
    }
}
