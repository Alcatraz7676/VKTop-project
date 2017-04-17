package com.ovchinnikovm.android.vktop.groups.di;

import com.ovchinnikovm.android.vktop.groups.ui.GroupsView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsModule_ProvidesGroupsViewFactory implements Factory<GroupsView> {
  private final GroupsModule module;

  public GroupsModule_ProvidesGroupsViewFactory(GroupsModule module) {
    this.module = module;
  }

  @Override
  public GroupsView get() {
    return provideInstance(module);
  }

  public static GroupsView provideInstance(GroupsModule module) {
    return proxyProvidesGroupsView(module);
  }

  public static GroupsModule_ProvidesGroupsViewFactory create(GroupsModule module) {
    return new GroupsModule_ProvidesGroupsViewFactory(module);
  }

  public static GroupsView proxyProvidesGroupsView(GroupsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesGroupsView(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
