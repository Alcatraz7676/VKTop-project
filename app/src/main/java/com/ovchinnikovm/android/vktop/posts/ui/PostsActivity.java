package com.ovchinnikovm.android.vktop.posts.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.ovchinnikovm.android.vktop.MainActivity;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.entities.PostItem;
import com.ovchinnikovm.android.vktop.entities.Posts;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.posts.PostsPresenter;
import com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.posts.adapters.PostsAdapter;
import com.ovchinnikovm.android.vktop.posts.di.PostsComponent;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsActivity extends AppCompatActivity implements PostsView, OnItemClickListener {

    @Nullable @InjectExtra
    Integer groupId;
    @Nullable @InjectExtra
    Integer postsCount;

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Inject
    ImageLoader imageLoader;
    @Inject
    OnItemClickListener onItemClickListener;
    @Inject
    PostsPresenter presenter;

    PostsAdapter adapter;

    public PostsActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);
        setContentView(R.layout.recyclerview_container);
        ButterKnife.bind(this);
        setupInjection();
        presenter.getPosts(groupId, postsCount);
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        PostsComponent postsComponent = app.getPostsComponent(this, this, this);
        postsComponent.inject(this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        presenter.onPause();
        super.onPause();
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPosts(Posts posts) {
        if (adapter == null) {
            adapter = new PostsAdapter(posts, imageLoader, onItemClickListener);
            setupRecyclerView();
        } else {
            adapter.setItems(posts);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition( 0, R.anim.screen_splash_fade_out );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition( 0, R.anim.screen_splash_fade_out );
    }

    @Override
    public void onItemClick(PostItem postItem) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(postItem.getPostUrl()));
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}