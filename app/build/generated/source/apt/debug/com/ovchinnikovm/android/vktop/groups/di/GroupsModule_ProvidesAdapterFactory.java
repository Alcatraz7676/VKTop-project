package com.ovchinnikovm.android.vktop.groups.di;

import android.content.Context;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.groups.adapters.GroupsAdapter;
import com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsModule_ProvidesAdapterFactory implements Factory<GroupsAdapter> {
  private final GroupsModule module;

  private final Provider<List<Group>> groupsProvider;

  private final Provider<ImageLoader> imageLoaderProvider;

  private final Provider<OnItemClickListener> clickListenerProvider;

  private final Provider<Context> contextProvider;

  public GroupsModule_ProvidesAdapterFactory(
      GroupsModule module,
      Provider<List<Group>> groupsProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> clickListenerProvider,
      Provider<Context> contextProvider) {
    this.module = module;
    this.groupsProvider = groupsProvider;
    this.imageLoaderProvider = imageLoaderProvider;
    this.clickListenerProvider = clickListenerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public GroupsAdapter get() {
    return provideInstance(
        module, groupsProvider, imageLoaderProvider, clickListenerProvider, contextProvider);
  }

  public static GroupsAdapter provideInstance(
      GroupsModule module,
      Provider<List<Group>> groupsProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> clickListenerProvider,
      Provider<Context> contextProvider) {
    return proxyProvidesAdapter(
        module,
        groupsProvider.get(),
        imageLoaderProvider.get(),
        clickListenerProvider.get(),
        contextProvider.get());
  }

  public static GroupsModule_ProvidesAdapterFactory create(
      GroupsModule module,
      Provider<List<Group>> groupsProvider,
      Provider<ImageLoader> imageLoaderProvider,
      Provider<OnItemClickListener> clickListenerProvider,
      Provider<Context> contextProvider) {
    return new GroupsModule_ProvidesAdapterFactory(
        module, groupsProvider, imageLoaderProvider, clickListenerProvider, contextProvider);
  }

  public static GroupsAdapter proxyProvidesAdapter(
      GroupsModule instance,
      List<Group> groups,
      ImageLoader imageLoader,
      OnItemClickListener clickListener,
      Context context) {
    return Preconditions.checkNotNull(
        instance.providesAdapter(groups, imageLoader, clickListener, context),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
