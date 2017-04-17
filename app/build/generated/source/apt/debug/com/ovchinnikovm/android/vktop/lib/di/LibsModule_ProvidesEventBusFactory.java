package com.ovchinnikovm.android.vktop.lib.di;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class LibsModule_ProvidesEventBusFactory implements Factory<EventBus> {
  private final LibsModule module;

  public LibsModule_ProvidesEventBusFactory(LibsModule module) {
    this.module = module;
  }

  @Override
  public EventBus get() {
    return provideInstance(module);
  }

  public static EventBus provideInstance(LibsModule module) {
    return proxyProvidesEventBus(module);
  }

  public static LibsModule_ProvidesEventBusFactory create(LibsModule module) {
    return new LibsModule_ProvidesEventBusFactory(module);
  }

  public static EventBus proxyProvidesEventBus(LibsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesEventBus(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
