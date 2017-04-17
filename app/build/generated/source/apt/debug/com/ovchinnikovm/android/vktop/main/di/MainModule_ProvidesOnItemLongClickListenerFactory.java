package com.ovchinnikovm.android.vktop.main.di;

import com.ovchinnikovm.android.vktop.main.adapters.OnItemLongClickListener;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvidesOnItemLongClickListenerFactory
    implements Factory<OnItemLongClickListener> {
  private final MainModule module;

  public MainModule_ProvidesOnItemLongClickListenerFactory(MainModule module) {
    this.module = module;
  }

  @Override
  public OnItemLongClickListener get() {
    return provideInstance(module);
  }

  public static OnItemLongClickListener provideInstance(MainModule module) {
    return proxyProvidesOnItemLongClickListener(module);
  }

  public static MainModule_ProvidesOnItemLongClickListenerFactory create(MainModule module) {
    return new MainModule_ProvidesOnItemLongClickListenerFactory(module);
  }

  public static OnItemLongClickListener proxyProvidesOnItemLongClickListener(MainModule instance) {
    return Preconditions.checkNotNull(
        instance.providesOnItemLongClickListener(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
