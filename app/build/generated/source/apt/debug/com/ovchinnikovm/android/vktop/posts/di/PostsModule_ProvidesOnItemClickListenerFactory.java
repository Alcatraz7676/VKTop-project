package com.ovchinnikovm.android.vktop.posts.di;

import com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PostsModule_ProvidesOnItemClickListenerFactory
    implements Factory<OnItemClickListener> {
  private final PostsModule module;

  public PostsModule_ProvidesOnItemClickListenerFactory(PostsModule module) {
    this.module = module;
  }

  @Override
  public OnItemClickListener get() {
    return provideInstance(module);
  }

  public static OnItemClickListener provideInstance(PostsModule module) {
    return proxyProvidesOnItemClickListener(module);
  }

  public static PostsModule_ProvidesOnItemClickListenerFactory create(PostsModule module) {
    return new PostsModule_ProvidesOnItemClickListenerFactory(module);
  }

  public static OnItemClickListener proxyProvidesOnItemClickListener(PostsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesOnItemClickListener(),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
