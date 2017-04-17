package com.ovchinnikovm.android.vktop.main.di;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.RealmResults;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvidesItemsListFactory
    implements Factory<RealmResults<RealmSortedItem>> {
  private final MainModule module;

  private final Provider<Integer> userIdProvider;

  public MainModule_ProvidesItemsListFactory(MainModule module, Provider<Integer> userIdProvider) {
    this.module = module;
    this.userIdProvider = userIdProvider;
  }

  @Override
  public RealmResults<RealmSortedItem> get() {
    return provideInstance(module, userIdProvider);
  }

  public static RealmResults<RealmSortedItem> provideInstance(
      MainModule module, Provider<Integer> userIdProvider) {
    return proxyProvidesItemsList(module, userIdProvider.get());
  }

  public static MainModule_ProvidesItemsListFactory create(
      MainModule module, Provider<Integer> userIdProvider) {
    return new MainModule_ProvidesItemsListFactory(module, userIdProvider);
  }

  public static RealmResults<RealmSortedItem> proxyProvidesItemsList(
      MainModule instance, Integer userId) {
    return Preconditions.checkNotNull(
        instance.providesItemsList(userId),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
