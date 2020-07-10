package com.ovchinnikovm.android.vktop.posts.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
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
import com.ovchinnikovm.android.vktop.lib.PreCachingLayoutManager;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.entities.ExtendedPost;
import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.entities.SortType;
import com.ovchinnikovm.android.vktop.lib.SparseBooleanArrayParcelable;
import com.ovchinnikovm.android.vktop.lib.base.ImageLoader;
import com.ovchinnikovm.android.vktop.main.MainActivity;
import com.ovchinnikovm.android.vktop.posts.PostsPresenter;
import com.ovchinnikovm.android.vktop.posts.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.posts.adapters.PostsAdapter;
import com.ovchinnikovm.android.vktop.posts.di.PostsComponent;
import com.ovchinnikovm.android.vktop.posts.events.DialogEvent;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostsActivity extends AppCompatActivity implements PostsView, OnItemClickListener {

    private final static String REALM_ID_KEY = "realm_id";
    private final static String RV_ITEMS_KEY = "rv_items";
    private final static String TOGGLE_EXPANDABLE_TV_KEY = "toggle_tv";
    private final static String SORT_ITEMS_TYPE_KEY = "sort_type";
    private final static String CURRENT_PAGE_KEY = "current_page";

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

    private SortType sortTypeForConstructor = SortType.LIKES;

    private boolean activityStoped = false;

    private int currentPage = 0;

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

        if (savedInstanceState != null)
            itemId = savedInstanceState.getInt(REALM_ID_KEY);

        if (itemId == null) {
            lockOrientation();
            if (sortIntervalType == 0)
                showDeterminateProgressDialog();
            else
                showIndeterminateProgressDialog();
            RealmSortedItem realmItem = new RealmSortedItem("-" + groupId, postsCount, groupIconUrl, groupName);
            presenter.downloadPostsIds(sortIntervalType, sortStart, sortEnd, realmItem);
        } else {
            presenter.setSortedItem(itemId);
            if (savedInstanceState != null) {
                ArrayList<ExtendedPost> items = savedInstanceState.getParcelableArrayList(RV_ITEMS_KEY);
                sortTypeForConstructor = (SortType) savedInstanceState.getSerializable(SORT_ITEMS_TYPE_KEY);
                currentPage = savedInstanceState.getInt(CURRENT_PAGE_KEY, 0);
                setPosts(items);
                SparseBooleanArray sbarray = savedInstanceState.getParcelable(TOGGLE_EXPANDABLE_TV_KEY);
                adapter.setTogglePositions(sbarray);
            } else {
                presenter.getPosts(0);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityStoped = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityStoped = true;
    }

    private void lockOrientation() {
        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
        else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    private void setupActionBar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ArrayAdapter<?> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.sort_items, R.layout.spinner_item);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinner.setAdapter(spinnerAdapter);
        spinner.setSelection(0, false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SortType sortType;
                switch (position) {
                    case 0:
                        sortType = SortType.LIKES;
                        break;
                    case 1:
                        sortType = SortType.SHARES;
                        break;
                    case 2:
                        sortType = SortType.COMMENTS;
                        break;
                    default:
                        sortType = SortType.LIKES;
                }
                sortTypeForConstructor = sortType;
                presenter.setSortType(sortType);
                currentPage = 0;

                if (adapter != null) {
                    adapter.setSortType(sortType);

                    recyclerview.clearOnScrollListeners();
                    adapter.removeItems();
                    adapter.notifyDataSetChanged();
                    recyclerview.getRecycledViewPool().clear();
                    addScrollListener();

                    presenter.getPosts(0);
                } else {
                    presenter.stopVkRequest();

                    presenter.getPosts(0);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imageLoader.loadIcon(groupIconImageView, groupIconUrl);
        groupIconImageView.setOnClickListener(v -> spinner.performClick());
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
    protected void onSaveInstanceState(Bundle outState) {
        if (adapter != null) {
            if (itemId != null)
                outState.putInt(REALM_ID_KEY, itemId);
            outState.putParcelableArrayList(RV_ITEMS_KEY, adapter.getItems());
            outState.putParcelable(TOGGLE_EXPANDABLE_TV_KEY, new SparseBooleanArrayParcelable(adapter.getTogglePositions()));
            outState.putSerializable(SORT_ITEMS_TYPE_KEY, sortTypeForConstructor);
            outState.putInt(CURRENT_PAGE_KEY, currentPage);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void incrementDialogNumber(DialogEvent event) {
        if (event.isLast()) {
            Log.i("get_posts", event.getRealmId().toString());
            dismissDialog();
            if (event.getRealmId() != null) {
                itemId = event.getRealmId();
            }
        } else {
            dialog.incrementProgress(1);
        }
    }

    private void dismissDialog() {
        dialog.dismiss();
        if (activityStoped) {
            showNotification();
        }
        presenter.getPosts(0);
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Sort end notification", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("This notification pop up when sorting ends and app is hidden");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(ContextCompat.getColor(this, R.color.colorPrimary));
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        Bitmap icon = ((RoundedBitmapDrawable)groupIconImageView.getDrawable()).getBitmap();

        Intent intent = getApplicationContext().getPackageManager()
                .getLaunchIntentForPackage(getPackageName())
                .setPackage(null)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        notificationBuilder
                .setAutoCancel(true)
                .setLargeIcon(icon)
                .setTicker(getResources().getString(R.string.notification_title))
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_text))
                .setContentIntent(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setSmallIcon(R.drawable.ic_lolipop_notification);
            notificationBuilder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        } else {
            notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        }

        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        PostsComponent postsComponent = app.getPostsComponent(this, this, this);
        postsComponent.inject(this);
    }

    private void setupRecyclerView() {
        preCachingLayoutManager = new PreCachingLayoutManager(getApplicationContext(),
                PreCachingLayoutManager.VERTICAL, false);
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
        recyclerview.addOnScrollListener(new EndlessRecyclerViewScrollListener(preCachingLayoutManager, currentPage) {
            @Override
            public void onLoadMore(int page) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new item to the bottom of the list
                presenter.getPosts(page);
                currentPage++;
            }
        });
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPosts(ArrayList<ExtendedPost> items) {
        loadingBar.setVisibility(View.GONE);
        if (items != null && items.size() > 0) {
            if (adapter == null) {
                adapter = new PostsAdapter(items, imageLoader, onItemClickListener,
                        this, sortTypeForConstructor);
                setupRecyclerView();
            } else {
                int before = adapter.getItemCount();
                adapter.addItems(items);
                adapter.notifyItemRangeInserted(before, adapter.getItemCount() - 1);
            }
        } else if(adapter == null) {
            emptyView.setVisibility(View.VISIBLE);
            recyclerview.setVisibility(View.GONE);
            spinner.setVisibility(View.GONE);
            groupNameTextView.setText(groupName);
            groupNameTextView.setVisibility(View.VISIBLE);
        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
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
        Picasso.with(this).cancelTag("PostImage");
        recyclerview.getRecycledViewPool().clear();
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
