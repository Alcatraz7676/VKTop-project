package com.ovchinnikovm.android.vktop.posts.di;

import com.ovchinnikovm.android.vktop.posts.PostsInteractor;
import com.ovchinnikovm.android.vktop.posts.PostsRepository;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;
import javax.inject.Provider;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PostsModule_ProvidesPostsInteractorFactory implements Factory<PostsInteractor> {
  private final PostsModule module;

  private final Provider<PostsRepository> repositoryProvider;

  public PostsModule_ProvidesPostsInteractorFactory(
      PostsModule module, Provider<PostsRepository> repositoryProvider) {
    this.module = module;
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public PostsInteractor get() {
    return provideInstance(module, repositoryProvider);
  }

  public static PostsInteractor provideInstance(
      PostsModule module, Provider<PostsRepository> repositoryProvider) {
    return proxyProvidesPostsInteractor(module, repositoryProvider.get());
  }

  public static PostsModule_ProvidesPostsInteractorFactory create(
      PostsModule module, Provider<PostsRepository> repositoryProvider) {
    return new PostsModule_ProvidesPostsInteractorFactory(module, repositoryProvider);
  }

  public static PostsInteractor proxyProvidesPostsInteractor(
      PostsModule instance, PostsRepository repository) {
    return Preconditions.checkNotNull(
        instance.providesPostsInteractor(repository),
        "Cannot return null from a non-@Nullable @Provides method");
  }
}
