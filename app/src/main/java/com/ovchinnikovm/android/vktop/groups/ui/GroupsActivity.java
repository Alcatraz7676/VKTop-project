package com.ovchinnikovm.android.vktop.groups.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ovchinnikovm.android.vktop.Henson;
import com.ovchinnikovm.android.vktop.PreCachingLayoutManager;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.groups.GroupsPresenter;
import com.ovchinnikovm.android.vktop.groups.adapters.GroupsAdapter;
import com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.groups.di.GroupsComponent;
import com.squareup.leakcanary.RefWatcher;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupsActivity extends AppCompatActivity implements GroupsView, OnItemClickListener {

    private final static String LIST_ITEMS_KEY = "recycler_list_items";
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
        if (savedInstanceState != null &&
                !((ArrayList<Group>) savedInstanceState.getSerializable(LIST_ITEMS_KEY)).isEmpty()) {

            setGroups((ArrayList<Group>) savedInstanceState.getSerializable(LIST_ITEMS_KEY));
            showGroups();
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
    }

    @Override
    public void onError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setGroups(List<Group> groups) {
        adapter.setItems(groups);
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
        Intent intent = Henson.with(this)
                .gotoGroupActivity()
                .groupTitle(group.getName())
                .groupDescription(group.getStatus())
                .groupIconURL(group.getPhotoURL())
                .memberNumber(group.getMembers())
                .groupId(group.getId())
                .build();

        startActivity(intent);
        overridePendingTransition(0, R.anim.screen_splash_fade_out);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
