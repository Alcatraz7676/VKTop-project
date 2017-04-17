package com.ovchinnikovm.android.vktop.posts.di;

import com.ovchinnikovm.android.vktop.posts.ui.PostsView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.annotation.Generated;

@Generated(
  value = "dagger.internal.codegen.ComponentProcessor",
  comments = "https://google.github.io/dagger"
)
public final class PostsModule_ProvidesPostsViewFactory implements Factory<PostsView> {
  private final PostsModule module;

  public PostsModule_ProvidesPostsViewFactory(PostsModule module) {
    this.module = module;
  }

  @Override
  public PostsView get() {
    return provideInstance(module);
  }

  public static PostsView provideInstance(PostsModule module) {
    return proxyProvidesPostsView(module);
  }

  public static PostsModule_ProvidesPostsViewFactory create(PostsModule module) {
    return new PostsModule_ProvidesPostsViewFactory(module);
  }

  public static PostsView proxyProvidesPostsView(PostsModule instance) {
    return Preconditions.checkNotNull(
        instance.providesPostsView(), "Cannot return null from a non-@Nullable @Provides method");
  }
}
