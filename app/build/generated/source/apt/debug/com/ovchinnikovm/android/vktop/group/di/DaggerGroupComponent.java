package com.ovchinnikovm.android.vktop.group.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.group.GroupInteractor;
import com.ovchinnikovm.android.vktop.group.GroupPresenter;
import com.ovchinnikovm.android.vktop.group.GroupRepository;
import com.ovchinnikovm.android.vktop.group.ui.GroupActivity;
import com.ovchinnikovm.android.vktop.group.ui.GroupActivity_MembersInjector;
import com.ovchinnikovm.android.vktop.group.ui.GroupView;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesActivityFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesEventBusFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesImageLoaderFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesLibraryImageLoaderFactory;
import com.squareup.picasso.Picasso;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerGroupComponent implements GroupComponent {
  private Provider<EventBus> providesEventBusProvider;

  private Provider<GroupView> providesGroupViewProvider;

  private Provider<GroupRepository> providesGroupRepositoryProvider;

  private Provider<GroupInteractor> providesGroupInteractorProvider;

  private Provider<Picasso> providesLibraryImageLoaderProvider;

  private Provider<Context> providesActivityProvider;

  private Provider<ImageLoader> providesImageLoaderProvider;

  private Provider<GroupPresenter> provideGroupPresenterProvider;

  private DaggerGroupComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.providesEventBusProvider =
        DoubleCheck.provider(LibsModule_ProvidesEventBusFactory.create(builder.libsModule));
    this.providesGroupViewProvider =
        DoubleCheck.provider(GroupModule_ProvidesGroupViewFactory.create(builder.groupModule));
    this.providesGroupRepositoryProvider =
        DoubleCheck.provider(
            GroupModule_ProvidesGroupRepositoryFactory.create(
                builder.groupModule, providesEventBusProvider));
    this.providesGroupInteractorProvider =
        DoubleCheck.provider(
            GroupModule_ProvidesGroupInteractorFactory.create(
                builder.groupModule, providesGroupRepositoryProvider));
    this.providesLibraryImageLoaderProvider =
        DoubleCheck.provider(
            LibsModule_ProvidesLibraryImageLoaderFactory.create(builder.libsModule));
    this.providesActivityProvider =
        DoubleCheck.provider(LibsModule_ProvidesActivityFactory.create(builder.libsModule));
    this.providesImageLoaderProvider =
        DoubleCheck.provider(
            LibsModule_ProvidesImageLoaderFactory.create(
                builder.libsModule, providesLibraryImageLoaderProvider, providesActivityProvider));
    this.provideGroupPresenterProvider =
        DoubleCheck.provider(
            GroupModule_ProvideGroupPresenterFactory.create(
                builder.groupModule,
                providesEventBusProvider,
                providesGroupViewProvider,
                providesGroupInteractorProvider,
                providesImageLoaderProvider,
                providesActivityProvider));
  }

  @Override
  public void inject(GroupActivity activity) {
    injectGroupActivity(activity);
  }

  private GroupActivity injectGroupActivity(GroupActivity instance) {
    GroupActivity_MembersInjector.injectPresenter(instance, provideGroupPresenterProvider.get());
    return instance;
  }

  public static final class Builder {
    private LibsModule libsModule;

    private GroupModule groupModule;

    private Builder() {}

    public GroupComponent build() {
      if (libsModule == null) {
        throw new IllegalStateException(LibsModule.class.getCanonicalName() + " must be set");
      }
      if (groupModule == null) {
        throw new IllegalStateException(GroupModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerGroupComponent(this);
    }

    public Builder libsModule(LibsModule libsModule) {
      this.libsModule = Preconditions.checkNotNull(libsModule);
      return this;
    }

    public Builder groupModule(GroupModule groupModule) {
      this.groupModule = Preconditions.checkNotNull(groupModule);
      return this;
    }
  }
}
