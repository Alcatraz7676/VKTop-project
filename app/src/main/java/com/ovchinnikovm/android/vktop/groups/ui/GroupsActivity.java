package com.ovchinnikovm.android.vktop.groups.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.ovchinnikovm.android.vktop.group.ui.GroupActivity;
import com.ovchinnikovm.android.vktop.lib.PreCachingLayoutManager;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.groups.GroupsPresenter;
import com.ovchinnikovm.android.vktop.groups.adapters.GroupsAdapter;
import com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.groups.di.GroupsComponent;
import com.squareup.leakcanary.RefWatcher;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;

import static com.ovchinnikovm.android.vktop.lib.PicassoImageLoader.POST_IMAGE_TAG;

public class GroupsActivity extends AppCompatActivity implements GroupsView, OnItemClickListener {

    private final static String LIST_ITEMS_KEY = "recycler_list_items";
    private final static String FILTER_TEXT_KEY = "filter_text";
    private final static String SEARCHVIEW_EXPANDED = "searchview_expanded";

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.disconnected_view)
    RelativeLayout disconnectedView;

    @Inject
    GroupsAdapter adapter;
    @Inject
    GroupsPresenter presenter;

    private SearchView searchView;
    private Boolean searchviewExpanded = false;

    private String filterText = "";

    public GroupsActivity() {
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setupInjection();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        ButterKnife.bind(this);
        setupRecyclerView();
        presenter.onStart();
        if (savedInstanceState != null) {
            if (!((ArrayList<Group>) savedInstanceState.getSerializable(LIST_ITEMS_KEY)).isEmpty()) {
                setGroups((ArrayList<Group>) savedInstanceState.getSerializable(LIST_ITEMS_KEY));
                showGroups();
            } else {
                presenter.getGroups();
            }
            if (!savedInstanceState.getString(FILTER_TEXT_KEY, "").equals("")) {
                filterText = savedInstanceState.getString(FILTER_TEXT_KEY, "");
            }
            if (savedInstanceState.getBoolean(SEARCHVIEW_EXPANDED))
                searchviewExpanded = true;
        } else
            presenter.getGroups();
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        GroupsComponent groupsComponent = app.getGroupsComponent(this, this, this);
        groupsComponent.inject(this);
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new PreCachingLayoutManager(this));
        adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_ITEMS_KEY, adapter.getItems());
        outState.putString(FILTER_TEXT_KEY, filterText);
        outState.putBoolean(SEARCHVIEW_EXPANDED, !searchView.isIconified());
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        Crashlytics.log("Error in onError method of GroupsActivity class. Text of error: " + error);
    }

    @Override
    public void setGroups(List<Group> groups) {
        adapter.setItems(groups);
    }

    @Override
    public void addGlobalGroups(List<Group> groups) {
        adapter.addGlobalGroups(groups);
    }

    @Override
    public void showDisconnectedView() {
        loadingIndicator.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        disconnectedView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showGroups() {
        loadingIndicator.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        disconnectedView.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        disconnectedView.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_group_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        adapter.filter(filterText);
        searchView.setQuery(filterText, false);
        if (searchviewExpanded) {
            searchView.setIconified(false);
        }
        RxSearchView.queryTextChanges(searchView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(charSequence -> {
                    filterText = charSequence.toString();
                    adapter.filter(filterText);
                    if (!charSequence.toString().trim().equals(""))
                        presenter.getGlobalGroups(filterText);
                });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(GroupsActivity.this);
                overridePendingTransition(0, R.anim.screen_splash_fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.screen_splash_fade_out);
    }

    @OnClick(R.id.disconnected_button)
    public void onViewClicked(View view) {
        presenter.getGroups();
    }

    @Override
    public void onItemClick(Group group) {
        Intent intent = new Intent(this, GroupActivity.class);
        intent.putExtra("groupTitle", group.getName());
        intent.putExtra("groupDescription", group.getStatus());
        intent.putExtra("groupIconURL", group.getPhotoURL());
        intent.putExtra("memberNumber", group.getMembers());
        intent.putExtra("groupId", group.getId());

        startActivity(intent);
        overridePendingTransition(0, R.anim.screen_splash_fade_out);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        Picasso.get().cancelTag(POST_IMAGE_TAG);
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
