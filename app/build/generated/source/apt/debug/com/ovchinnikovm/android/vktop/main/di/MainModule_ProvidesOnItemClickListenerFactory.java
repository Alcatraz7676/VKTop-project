package com.ovchinnikovm.android.vktop.main.di;

import com.ovchinnikovm.android.vktop.main.adapters.OnItemClickListener;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class MainModule_ProvidesOnItemClickListenerFactory
    implements Factory<OnItemClickListener> {
  private final MainModule module;

  public MainModule_ProvidesOnItemClickListenerFactory(MainModule module) {
    this.module = module;
  }

  @Override
  public OnItemClickListener get() {
    return provideInstance(module);
  }

  public static OnItemClickListener provideInstance(MainModule module) {
    return proxyProvidesOnItemClickListener(module);
  }

  public static MainModule_ProvidesOnItemClickListenerFactory create(MainModule module) {
    return new MainModule_ProvidesOnItemClickListenerFactory(module);
  }

  public static OnItemClickListener proxyProvidesOnItemClickListener(MainModule instance) {
    return Preconditions.checkNotNull(
        instance.providesOnItemClickListener(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
