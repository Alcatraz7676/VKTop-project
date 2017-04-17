package com.ovchinnikovm.android.vktop.main.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesActivityFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesImageLoaderFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesLibraryImageLoaderFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesUserIdFactory;
import com.ovchinnikovm.android.vktop.main.MainActivity;
import com.ovchinnikovm.android.vktop.main.MainActivity_MembersInjector;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemLongClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.SortDataAdapter;
import com.squareup.picasso.Picasso;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import io.realm.RealmResults;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerMainComponent implements MainComponent {
  private Provider<Integer> providesUserIdProvider;

  private Provider<RealmResults<RealmSortedItem>> providesItemsListProvider;

  private Provider<Picasso> providesLibraryImageLoaderProvider;

  private Provider<Context> providesActivityProvider;

  private Provider<ImageLoader> providesImageLoaderProvider;

  private Provider<OnItemClickListener> providesOnItemClickListenerProvider;

  private Provider<OnItemLongClickListener> providesOnItemLongClickListenerProvider;

  private Provider<SortDataAdapter> providesAdapterProvider;

  private DaggerMainComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.providesUserIdProvider =
        DoubleCheck.provider(LibsModule_ProvidesUserIdFactory.create(builder.libsModule));
    this.providesItemsListProvider =
        DoubleCheck.provider(
            MainModule_ProvidesItemsListFactory.create(builder.mainModule, providesUserIdProvider));
    this.providesLibraryImageLoaderProvider =
        DoubleCheck.provider(
            LibsModule_ProvidesLibraryImageLoaderFactory.create(builder.libsModule));
    this.providesActivityProvider =
        DoubleCheck.provider(LibsModule_ProvidesActivityFactory.create(builder.libsModule));
    this.providesImageLoaderProvider =
        DoubleCheck.provider(
            LibsModule_ProvidesImageLoaderFactory.create(
                builder.libsModule, providesLibraryImageLoaderProvider, providesActivityProvider));
    this.providesOnItemClickListenerProvider =
        DoubleCheck.provider(
            MainModule_ProvidesOnItemClickListenerFactory.create(builder.mainModule));
    this.providesOnItemLongClickListenerProvider =
        DoubleCheck.provider(
            MainModule_ProvidesOnItemLongClickListenerFactory.create(builder.mainModule));
    this.providesAdapterProvider =
        DoubleCheck.provider(
            MainModule_ProvidesAdapterFactory.create(
                builder.mainModule,
                providesItemsListProvider,
                providesImageLoaderProvider,
                providesOnItemClickListenerProvider,
                providesOnItemLongClickListenerProvider));
  }

  @Override
  public void inject(MainActivity activity) {
    injectMainActivity(activity);
  }

  private MainActivity injectMainActivity(MainActivity instance) {
    MainActivity_MembersInjector.injectAdapter(instance, providesAdapterProvider.get());
    return instance;
  }

  public static final class Builder {
    private LibsModule libsModule;

    private MainModule mainModule;

    private Builder() {}

    public MainComponent build() {
      if (libsModule == null) {
        throw new IllegalStateException(LibsModule.class.getCanonicalName() + " must be set");
      }
      if (mainModule == null) {
        throw new IllegalStateException(MainModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerMainComponent(this);
    }

    public Builder libsModule(LibsModule libsModule) {
      this.libsModule = Preconditions.checkNotNull(libsModule);
      return this;
    }

    public Builder mainModule(MainModule mainModule) {
      this.mainModule = Preconditions.checkNotNull(mainModule);
      return this;
    }
  }
}
