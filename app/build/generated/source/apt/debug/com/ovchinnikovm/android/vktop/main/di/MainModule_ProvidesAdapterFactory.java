package com.ovchinnikovm.android.vktop.main.di;

import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemLongClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.SortDataAdapter;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.realm.RealmResults;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvidesAdapterFactory implements Factory<SortDataAdapter> {
  private final MainModule module;

  private final Provider<RealmResults<RealmSortedItem>> dataProvider;

  private final Provider<ImageLoader> imageLoaderProvider;

  private final Provider<OnItemClickListener> clickListenerProvider;

  private final Provider<OnItemLongClickListener> longClickListenerProvider;

  public MainModule_ProvidesAdapterFactory(
      MainModule module,
      Provider<RealmResults<RealmSortedItem>> dataProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> clickListenerProvider,
      Provider<OnItemLongClickListener> longClickListenerProvider) {
    this.module = module;
    this.dataProvider = dataProvider;
    this.imageLoaderProvider = imageLoaderProvider;
    this.clickListenerProvider = clickListenerProvider;
    this.longClickListenerProvider = longClickListenerProvider;
  }

  @Override
  public SortDataAdapter get() {
    return provideInstance(
        module,
        dataProvider,
        imageLoaderProvider,
        clickListenerProvider,
        longClickListenerProvider);
  }

  public static SortDataAdapter provideInstance(
      MainModule module,
      Provider<RealmResults<RealmSortedItem>> dataProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> clickListenerProvider,
      Provider<OnItemLongClickListener> longClickListenerProvider) {
    return proxyProvidesAdapter(
        module,
        dataProvider.get(),
        imageLoaderProvider.get(),
        clickListenerProvider.get(),
        longClickListenerProvider.get());
  }

  public static MainModule_ProvidesAdapterFactory create(
      MainModule module,
      Provider<RealmResults<RealmSortedItem>> dataProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> clickListenerProvider,
      Provider<OnItemLongClickListener> longClickListenerProvider) {
    return new MainModule_ProvidesAdapterFactory(
        module,
        dataProvider,
        imageLoaderProvider,
        clickListenerProvider,
        longClickListenerProvider);
  }

  public static SortDataAdapter proxyProvidesAdapter(
      MainModule instance,
      RealmResults<RealmSortedItem> data,
      ImageLoader imageLoader,
      OnItemClickListener clickListener,
      OnItemLongClickListener longClickListener) {
    return Preconditions.checkNotNull(
        instance.providesAdapter(data, imageLoader, clickListener, longClickListener),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
