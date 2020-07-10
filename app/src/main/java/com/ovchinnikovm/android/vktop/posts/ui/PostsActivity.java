package com.ovchinnikovm.android.vktop.posts.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.GravityEnum;
import com.afollestad.materialdialogs.MaterialDialog;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.ovchinnikovm.android.vktop.PreCachingLayoutManager;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.entities.ExtendedPosts;
import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.main.MainActivity;
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
    @Nullable
    @InjectExtra
    Integer sortIntervalType;
    @Nullable
    @InjectExtra
    Long sortStart;
    @Nullable
    @InjectExtra
    Long sortEnd;
    @Nullable
    @InjectExtra
    Integer itemId;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.group_icon)
    ImageView groupIconImageView;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingBar;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.group_name)
    TextView groupNameTextView;

    @Inject
    ImageLoader imageLoader;
    @Inject
    OnItemClickListener onItemClickListener;
    @Inject
    PostsPresenter presenter;

    private MaterialDialog dialog;

    private PreCachingLayoutManager preCachingLayoutManager;
    private PostsAdapter adapter;

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
        presenter.onCreate();
        if (itemId == null) {
            if (sortIntervalType == 0)
                showDeterminateProgressDialog();
            else
                showIndeterminateProgressDialog();
            RealmSortedItem realmItem = new RealmSortedItem("-" + groupId, postsCount, groupIconUrl, groupName);
            presenter.downloadPostsIds(sortIntervalType, sortStart, sortEnd, realmItem);
        } else {
            presenter.setSortedItem(itemId);
            presenter.getPosts(0);
        }
    }

    private void setupActionBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayAdapter<?> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.sort_items, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sortType;
                switch (position) {
                    case 0:
                        sortType = "likes";
                        break;
                    case 1:
                        sortType = "shares";
                        break;
                    case 2:
                        sortType = "comments";
                        break;
                    default:
                        sortType = "likes";
                }
                presenter.setSortType(sortType);

                recyclerview.clearOnScrollListeners();
                adapter.removeItems();
                adapter.notifyDataSetChanged();
                recyclerview.getRecycledViewPool().clear();
                addScrollListener();

                presenter.getPosts(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageLoader.loadIcon(groupIconImageView, groupIconUrl);
    }

    public void showDeterminateProgressDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.sort_progress_dialog_title)
                .content(R.string.please_wait)
                .contentGravity(GravityEnum.CENTER)
                .progress(false, ((postsCount / 100) + 1), true)
                .cancelable(false)
                .negativeText("Отмена")
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, R.anim.screen_splash_fade_out);
                })
                .showListener(
                        dialogInterface -> dialog = (MaterialDialog) dialogInterface)
                .show();
    }

    public void showIndeterminateProgressDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.sort_progress_dialog_title)
                .content(R.string.please_wait)
                .contentGravity(GravityEnum.CENTER)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .cancelable(false)
                .negativeText("Отмена")
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                            | Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(0, R.anim.screen_splash_fade_out);
                })
                .showListener(
                        dialogInterface -> dialog = (MaterialDialog) dialogInterface)
                .show();
    }

    @Override
    public void incrementDialogNumber(DialogEvent event) {
        if (event.isLast()) {
            dismissDialog();
        } else {
            dialog.incrementProgress(1);
        }
    }

    private void dismissDialog() {
        dialog.dismiss();
        presenter.getPosts(0);
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        PostsComponent postsComponent = app.getPostsComponent(this, this, this);
        postsComponent.inject(this);
    }

    private void setupRecyclerView() {
        preCachingLayoutManager = new PreCachingLayoutManager(getApplicationContext(), PreCachingLayoutManager.VERTICAL, false);
        recyclerview.setLayoutManager(preCachingLayoutManager);
        addScrollListener();

        adapter.setHasStableIds(true);
        recyclerview.setAdapter(adapter);
        recyclerview.setItemViewCacheSize(5);
        recyclerview.setDrawingCacheEnabled(true);
        recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerview.setHasFixedSize(true);
    }

    private void addScrollListener() {
        recyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(preCachingLayoutManager) {
            @Override
            public void onLoadMore(int page) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new item to the bottom of the list
                presenter.getPosts(page);
            }
        });
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPosts(ExtendedPosts extendedPosts) {
        loadingBar.setVisibility(View.GONE);
        if (extendedPosts != null) {
            if (adapter == null) {
                adapter = new PostsAdapter(extendedPosts, imageLoader, onItemClickListener, this);
                setupRecyclerView();
            } else {
                int before = adapter.getItemCount();
                adapter.setItems(extendedPosts);
                adapter.notifyItemRangeInserted(before, adapter.getItemCount() - 1);
            }
        } else if(adapter == null) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            groupNameTextView.setText(groupName);
            groupNameTextView.setVisibility(View.VISIBLE);
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
    public void onItemClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        recyclerview.getRecycledViewPool().clear();
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
