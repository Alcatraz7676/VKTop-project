package com.ovchinnikovm.android.vktop.posts.di;

import com.ovchinnikovm.android.vktop.posts.PostsRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;
import org.greenrobot.eventbus.EventBus;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PostsModule_ProvidesPostsRepositoryFactory implements Factory<PostsRepository> {
  private final PostsModule module;

  private final Provider<EventBus> eventBusProvider;

  public PostsModule_ProvidesPostsRepositoryFactory(
      PostsModule module, Provider<EventBus> eventBusProvider) {
    this.module = module;
    this.eventBusProvider = eventBusProvider;
  }

  @Override
  public PostsRepository get() {
    return provideInstance(module, eventBusProvider);
  }

  public static PostsRepository provideInstance(
      PostsModule module, Provider<EventBus> eventBusProvider) {
    return proxyProvidesPostsRepository(module, eventBusProvider.get());
  }

  public static PostsModule_ProvidesPostsRepositoryFactory create(
      PostsModule module, Provider<EventBus> eventBusProvider) {
    return new PostsModule_ProvidesPostsRepositoryFactory(module, eventBusProvider);
  }

  public static PostsRepository proxyProvidesPostsRepository(
      PostsModule instance, EventBus eventBus) {
    return Preconditions.checkNotNull(
        instance.providesPostsRepository(eventBus),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
