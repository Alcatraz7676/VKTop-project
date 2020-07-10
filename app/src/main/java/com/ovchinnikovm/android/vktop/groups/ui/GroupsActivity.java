package com.ovchinnikovm.android.vktop.groups.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.entities.Group;
import com.ovchinnikovm.android.vktop.group.ui.Henson;
import com.ovchinnikovm.android.vktop.groups.GroupsPresenter;
import com.ovchinnikovm.android.vktop.groups.adapters.GroupsAdapter;
import com.ovchinnikovm.android.vktop.groups.adapters.OnItemClickListener;
import com.ovchinnikovm.android.vktop.groups.di.GroupsComponent;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupsActivity extends AppCompatActivity implements GroupsView, OnItemClickListener {

    @BindView(R.id.rv_groups)
    RecyclerView recyclerView;

    @Inject
    GroupsAdapter adapter;
    @Inject
    GroupsPresenter presenter;

    public GroupsActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);
        ButterKnife.bind(this);
        setupInjection();
        setupRecyclerView();
        presenter.getGroups();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        GroupsComponent groupsComponent = app.getGroupsComponent(this, this, this);
        groupsComponent.inject(this);
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

    }

    @Override
    public void setGroups(List<Group> groups) {
        adapter.setItems(groups);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(GroupsActivity.this);
                overridePendingTransition( 0, R.anim.screen_splash_fade_out );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( 0, R.anim.screen_splash_fade_out );
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
        overridePendingTransition( 0, R.anim.screen_splash_fade_out );
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
