package com.ovchinnikovm.android.vktop.groups.di;

import com.ovchinnikovm.android.vktop.entities.Group;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class GroupsModule_ProvidesItemsListFactory implements Factory<List<Group>> {
  private final GroupsModule module;

  public GroupsModule_ProvidesItemsListFactory(GroupsModule module) {
    this.module = module;
  }

  @Override
  public List<Group> get() {
    return provideInstance(module);
  }

  public static List<Group> provideInstance(GroupsModule module) {
    return proxyProvidesItemsList(module);
  }

  public static GroupsModule_ProvidesItemsListFactory create(GroupsModule module) {
    return new GroupsModule_ProvidesItemsListFactory(module);
  }

  public static List<Group> proxyProvidesItemsList(GroupsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesItemsList(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
