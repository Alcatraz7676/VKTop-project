package com.ovchinnikovm.android.vktop.group.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
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

    @Nullable
    @InjectExtra
    String groupTitle;
    @Nullable
    @InjectExtra
    String groupDescription;
    @Nullable
    @InjectExtra
    String groupIconURL;
    @Nullable
    @InjectExtra
    Integer memberNumber;
    @Nullable
    @InjectExtra
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
    @BindView(R.id.average_time_label)
    TextView averageTimeLabel;

    @Inject
    GroupPresenter presenter;
    MaterialDialog sortIntervalDialog;
    Integer dialogSelectedIndex = 0;
    private Integer postsCount;
    private Integer time;

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

        sortIntervalDialog = new MaterialDialog.Builder(this)
                .title(R.string.sort_interval_dialog_title)
                .items(R.array.time_interval)
                .itemsCallbackSingleChoice(
                        dialogSelectedIndex,
                        (dialog, view, which, text) -> {
                            changeText(which);
                            dialogSelectedIndex = which;
                            return true;
                        })
                .positiveText(R.string.choose_label)
                .build();

        presenter.getPostsCount(groupId);
    }

    private void changeText(int newIndex) {
        if (dialogSelectedIndex != newIndex) {
            switch (newIndex) {
                case 0:
                    averageTimeLabel.setText(R.string.average_time_label);
                    setTime(time);
                    break;
                case 1:
                    averageTimeLabel.setText(R.string.max_time_label);
                    if (time > 74) {
                        // Number of maximum posts per day(50) multiplied by days in one year(365)
                        // divided by speed of getting posts (250 per second) and plus additional second(1)
                        setTime(74);
                    } else {
                        setTime(time);
                    }
                    break;
                case 2:
                    averageTimeLabel.setText(R.string.max_time_label);
                    if (time > 7) {
                        // Number of maximum posts per day(50) multiplied by days in one month(30)
                        // divided by speed of getting posts (250 per second) and plus additional second(1)
                        setTime(7);
                    } else {
                        setTime(time);
                    }
                    break;
                case 3:
                    averageTimeLabel.setText(R.string.max_time_label);
                    if (time > 3) {
                        // Number of maximum posts per day(50) multiplied by days in one week(7)
                        // divided by speed of getting posts (250 per second) and plus additional second(1)
                        setTime(3);
                    }  else {
                        setTime(time);
                    }
                    break;
            }
        }
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
        this.time = time;
        postsNumberTextView.setText(String.valueOf(postsCount));
        setTime(time);
    }

    private void setTime(Integer time) {
        if (time < 60) {
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
                overridePendingTransition(0, R.anim.screen_splash_fade_out);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.sort_button, R.id.sort_interval_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sort_button:
                Intent intent = Henson.with(this)
                        .gotoPostsActivity()
                        .groupId(groupId)
                        .postsCount(postsCount)
                        .groupName(groupTitle)
                        .groupIconUrl(groupIconURL)
                        .sortIntervalType(dialogSelectedIndex)
                        .build();

                startActivity(intent);
                overridePendingTransition(0, R.anim.screen_splash_fade_out);
                break;
            case R.id.sort_interval_button:
                sortIntervalDialog.show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
