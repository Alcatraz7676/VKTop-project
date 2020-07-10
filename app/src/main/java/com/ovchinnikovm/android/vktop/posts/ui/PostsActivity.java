package com.ovchinnikovm.android.vktop.posts.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
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
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsActivity extends AppCompatActivity implements PostsView, OnItemClickListener {

    @Nullable
    @InjectExtra
    Integer groupId;
    @Nullable
    @InjectExtra
    Integer postsCount;
    @Nullable
    @InjectExtra
    String groupName;
    @Nullable
    @InjectExtra
    String groupIconUrl;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.group_icon)
    ImageView groupIconImageView;
    @BindView(R.id.groupName)
    TextView groupNameTextView;

    @Inject
    ImageLoader imageLoader;
    @Inject
    OnItemClickListener onItemClickListener;
    @Inject
    PostsPresenter presenter;

    PostsAdapter adapter;

    private MaterialDialog dialog;

    public PostsActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);
        setContentView(R.layout.activity_posts);
        ButterKnife.bind(this);
        setupInjection();
        setupActionBar();
        showProgressDeterminateDialog();
        presenter.getPosts(groupId, postsCount);
    }

    private void setupActionBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        groupNameTextView.setText(groupName);
        imageLoader.loadIcon(groupIconImageView, groupIconUrl);
    }

    public void showProgressDeterminateDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.sort_progress_dialog_title)
                .content(R.string.please_wait)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, ((postsCount / 100) + 1), true)
                .cancelable(false)
                .showListener(
                        dialogInterface -> dialog = (MaterialDialog) dialogInterface)
                .show();
    }

    @Override
    public void incrementDialogNumber(DialogEvent event) {
        dialog.incrementProgress(1);
        if (event.isLast())
            dialog.dismiss();
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        PostsComponent postsComponent = app.getPostsComponent(this, this, this);
        postsComponent.inject(this);
    }

    private void setupRecyclerView() {
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setAdapter(adapter);
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
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPosts(Posts posts) {
        if (adapter == null) {
            adapter = new PostsAdapter(posts, imageLoader, onItemClickListener);
            setupRecyclerView();
        } else {
            adapter.setItems(posts);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, R.anim.screen_splash_fade_out);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        overridePendingTransition(0, R.anim.screen_splash_fade_out);
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
