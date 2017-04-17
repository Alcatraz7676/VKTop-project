package com.ovchinnikovm.android.vktop.groups.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.groups.GroupsInteractor;
import com.ovchinnikovm.android.vktop.groups.GroupsPresenter;
import com.ovchinnikovm.android.vktop.groups.GroupsRepository;
import com.ovchinnikovm.android.vktop.groups.adapters.GroupsAdapter;
import com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsActivity;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsActivity_MembersInjector;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesActivityFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesEventBusFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesImageLoaderFactory;
import com.ovchinnikovm.android.vktop.lib.di.LibsModule_ProvidesLibraryImageLoaderFactory;
import com.squareup.picasso.Picasso;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.util.List;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class DaggerGroupsComponent implements GroupsComponent {
  private Provider<List<Group>> providesItemsListProvider;

  private Provider<Picasso> providesLibraryImageLoaderProvider;

  private Provider<Context> providesActivityProvider;

  private Provider<ImageLoader> providesImageLoaderProvider;

  private Provider<OnItemClickListener> providesOnItemClickListenerProvider;

  private Provider<GroupsAdapter> providesAdapterProvider;

  private Provider<EventBus> providesEventBusProvider;

  private Provider<GroupsView> providesGroupsViewProvider;

  private Provider<GroupsRepository> providesGroupsRepositoryProvider;

  private Provider<GroupsInteractor> providesGroupsInteractorProvider;

  private Provider<GroupsPresenter> provideGroupsPresenterProvider;

  private DaggerGroupsComponent(Builder builder) {
    initialize(builder);
  }

  public static Builder builder() {
    return new Builder();
  }

  @SuppressWarnings("unchecked")
  private void initialize(final Builder builder) {
    this.providesItemsListProvider =
        DoubleCheck.provider(GroupsModule_ProvidesItemsListFactory.create(builder.groupsModule));
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
            GroupsModule_ProvidesOnItemClickListenerFactory.create(builder.groupsModule));
    this.providesAdapterProvider =
        DoubleCheck.provider(
            GroupsModule_ProvidesAdapterFactory.create(
                builder.groupsModule,
                providesItemsListProvider,
                providesImageLoaderProvider,
                providesOnItemClickListenerProvider,
                providesActivityProvider));
    this.providesEventBusProvider =
        DoubleCheck.provider(LibsModule_ProvidesEventBusFactory.create(builder.libsModule));
    this.providesGroupsViewProvider =
        DoubleCheck.provider(GroupsModule_ProvidesGroupsViewFactory.create(builder.groupsModule));
    this.providesGroupsRepositoryProvider =
        DoubleCheck.provider(
            GroupsModule_ProvidesGroupsRepositoryFactory.create(
                builder.groupsModule, providesEventBusProvider));
    this.providesGroupsInteractorProvider =
        DoubleCheck.provider(
            GroupsModule_ProvidesGroupsInteractorFactory.create(
                builder.groupsModule, providesGroupsRepositoryProvider));
    this.provideGroupsPresenterProvider =
        DoubleCheck.provider(
            GroupsModule_ProvideGroupsPresenterFactory.create(
                builder.groupsModule,
                providesEventBusProvider,
                providesGroupsViewProvider,
                providesGroupsInteractorProvider,
                providesActivityProvider));
  }

  @Override
  public void inject(GroupsActivity activity) {
    injectGroupsActivity(activity);
  }

  private GroupsActivity injectGroupsActivity(GroupsActivity instance) {
    GroupsActivity_MembersInjector.injectAdapter(instance, providesAdapterProvider.get());
    GroupsActivity_MembersInjector.injectPresenter(instance, provideGroupsPresenterProvider.get());
    return instance;
  }

  public static final class Builder {
    private GroupsModule groupsModule;

    private LibsModule libsModule;

    private Builder() {}

    public GroupsComponent build() {
      if (groupsModule == null) {
        throw new IllegalStateException(GroupsModule.class.getCanonicalName() + " must be set");
      }
      if (libsModule == null) {
        throw new IllegalStateException(LibsModule.class.getCanonicalName() + " must be set");
      }
      return new DaggerGroupsComponent(this);
    }

    public Builder libsModule(LibsModule libsModule) {
      this.libsModule = Preconditions.checkNotNull(libsModule);
      return this;
    }

    public Builder groupsModule(GroupsModule groupsModule) {
      this.groupsModule = Preconditions.checkNotNull(groupsModule);
      return this;
    }
  }
}
