package com.ovchinnikovm.android.vktop.group.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.ovchinnikovm.android.vktop.Henson;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.group.GroupPresenter;
import com.ovchinnikovm.android.vktop.group.di.GroupComponent;
import com.squareup.leakcanary.RefWatcher;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupActivity extends AppCompatActivity implements GroupView {

    @Nullable @InjectExtra
    String groupTitle;
    @Nullable @InjectExtra
    String groupDescription;
    @Nullable @InjectExtra
    String groupIconURL;
    @Nullable @InjectExtra
    Integer memberNumber;
    @Nullable @InjectExtra
    Integer groupId;
    @BindView(R.id.group_title)
    TextView groupTitleTextView;
    @BindView(R.id.group_description)
    TextView groupDescriptionTextView;
    @BindView(R.id.group_icon)
    ImageView groupIconImageView;
    @BindView(R.id.member_number)
    TextView memberNumberTextView;
    @BindView(R.id.posts_number)
    TextView postsNumberTextView;
    @BindView(R.id.time_number)
    TextView timeNumberTextView;
    @BindView(R.id.sort_button)
    Button sortButton;
    @Inject
    GroupPresenter presenter;
    private Integer postsCount;

    public GroupActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Dart.inject(this);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        setupInjection();
        setupGroupInformation();
        presenter.getPostsCount(groupId);
    }

    private void setupGroupInformation() {
        groupTitleTextView.setText(groupTitle);
        groupDescriptionTextView.setText(groupDescription);
        presenter.loadIcon(groupIconImageView, groupIconURL);
        memberNumberTextView.setText(Integer.toString(memberNumber));
    }

    private void setupInjection() {
        VkTopApp app = (VkTopApp) getApplication();
        GroupComponent groupComponent = app.getGroupComponent(this, this);
        groupComponent.inject(this);
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
    public void setPostsAndTime(Integer postsCount, Integer time) {
        this.postsCount = postsCount;
        postsNumberTextView.setText(String.valueOf(postsCount));
        if (time < 60) {
            if (time == 0) {
                timeNumberTextView.setText(getResources().getQuantityString(R.plurals.seconds, 1, 1));
                return;
            }
            timeNumberTextView.setText(getResources().getQuantityString(R.plurals.seconds, time, time));
        } else {
            time = time / 60;
            timeNumberTextView.setText(getResources().getQuantityString(R.plurals.minutes, time, time));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(GroupActivity.this);
                overridePendingTransition( 0, R.anim.screen_splash_fade_out );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.sort_button)
    public void onViewClicked() {
        Intent intent = Henson.with(this)
                .gotoPostsActivity()
                .groupId(groupId)
                .postsCount(postsCount)
                .build();

        startActivity(intent);
        overridePendingTransition( 0, R.anim.screen_splash_fade_out );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
