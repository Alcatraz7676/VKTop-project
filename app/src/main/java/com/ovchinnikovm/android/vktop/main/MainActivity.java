package com.ovchinnikovm.android.vktop.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.ovchinnikovm.android.vktop.LoginActivity;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.lib.RecyclerViewEmptySupport;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.entities.RealmSortedItem;
import com.ovchinnikovm.android.vktop.groups.ui.GroupsActivity;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.OnItemLongClickListener;
import com.ovchinnikovm.android.vktop.main.adapters.SortDataAdapter;
import com.ovchinnikovm.android.vktop.main.di.MainComponent;
import com.ovchinnikovm.android.vktop.posts.ui.PostsActivity;
import com.ovchinnikovm.android.vktop.settings.SettingsActivity;
import com.squareup.leakcanary.RefWatcher;
import com.vk.sdk.VKSdk;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.support.v7.preference.PreferenceManager.getDefaultSharedPreferences;
import static com.ovchinnikovm.android.vktop.posts.ui.PostsActivity.RETURN_FROM_ACTIVITY_KEY;

public class MainActivity extends AppCompatActivity implements OnItemClickListener, OnItemLongClickListener {

    public static final String KEY_PREF_FIRST_ENTER = "first_enter_key";

    @BindView(R.id.recycler_view)
    RecyclerViewEmptySupport recyclerView;
    @BindView(R.id.empty_view)
    RelativeLayout emptyView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    SortDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupInjection();
        setupAdapter();
        PreferenceManager.setDefaultValues(this, R.xml.pref_notifications, false);

        InterstitialAd interstitialAd = new InterstitialAd (this);
        interstitialAd.setAdUnitId("ca-app-pub-5717076824212218/7203549240");
        interstitialAd.loadAd(new AdRequest
                .Builder()
                .build());

        if (!VKSdk.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
            //        | Intent.FLAG_ACTIVITY_NEW_TASK
            //        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            //overridePendingTransition(0, 0);
            finish();
        } else {
            SharedPreferences sharedPreferences = getDefaultSharedPreferences(getApplicationContext());
            boolean firstEnter = sharedPreferences.getBoolean(KEY_PREF_FIRST_ENTER, true);
            boolean returnFromActivity = sharedPreferences.getBoolean(RETURN_FROM_ACTIVITY_KEY, false);
            if (firstEnter) {
                new MaterialTapTargetPrompt.Builder(this)
                        .setTarget(fab)
                        .setPrimaryText(R.string.fab_title)
                        .setSecondaryText(R.string.fab_subtitle)
                        .setBackgroundColour(ContextCompat.getColor(this, R.color.colorPrimaryTransparent))
                        .setAnimationInterpolator(new FastOutSlowInInterpolator())
                        .setPromptStateChangeListener((prompt, state) -> {
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(KEY_PREF_FIRST_ENTER, false);
                            editor.apply();
                        }).show();
            }
            if (returnFromActivity) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(RETURN_FROM_ACTIVITY_KEY, false);
                editor.apply();
                interstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        interstitialAd.show();
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        MainComponent mainComponent = app.getMainComponent(this, this, this);
        mainComponent.inject(this);
    }

    private void setupAdapter() {
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setEmptyView(emptyView);
        recyclerView.setAdapter(adapter);
        initSwipe();
        setUpAnimationDecoratorHelper();
    }

    private void initSwipe()
    {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.removeItem(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    Paint p = new Paint();

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    } else {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void setUpAnimationDecoratorHelper() {
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.parseColor("#D32F2F"));
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Realm realmInstance = Realm.getDefaultInstance();
        Integer itemId = adapter.getItem(position).getItemId();
        RealmSortedItem item = realmInstance.where(RealmSortedItem.class)
                .equalTo("sortId", itemId).findFirst();
        String iconUrl = item.getGroupIconUrl();
        Intent intent = new Intent(this, PostsActivity.class);
        intent.putExtra("itemId", itemId);
        intent.putExtra("groupIconURL", iconUrl);
        startActivity(intent);
        overridePendingTransition(0, R.anim.screen_splash_fade_out);
    }

    @Override
    public boolean onItemLongClick(int position) {
        new MaterialDialog.Builder(this)
                .items(R.array.delete_array)
                .itemsCallback((dialog, view, which, text) -> {
                    adapter.removeItem(position);
                })
                .show();
        return true;
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        Intent intent = new Intent(MainActivity.this, GroupsActivity.class);
        startActivity(intent);
        overridePendingTransition(0, R.anim.screen_splash_fade_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout && VKSdk.isLoggedIn()) {
            VKSdk.logout();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            overridePendingTransition(0, R.anim.screen_splash_fade_out);
            return true;
        } else if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
