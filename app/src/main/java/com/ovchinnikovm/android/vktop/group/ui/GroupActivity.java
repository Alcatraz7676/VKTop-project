package com.ovchinnikovm.android.vktop.group.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.ovchinnikovm.android.vktop.Henson;
import com.ovchinnikovm.android.vktop.R;
import com.ovchinnikovm.android.vktop.VkTopApp;
import com.ovchinnikovm.android.vktop.group.GroupPresenter;
import com.ovchinnikovm.android.vktop.group.di.GroupComponent;
import com.squareup.leakcanary.RefWatcher;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupActivity extends AppCompatActivity implements GroupView {

    private final static String POSTS_COUNT_KEY = "posts_count_key";
    private final static String TIME_KEY = "time_key";
    private final static String SORT_START_KEY = "sort_start_key";
    private final static String SORT_END_KEY = "sort_end_key";
    private final static String DIALOG_SELECTED_INDEX_KEY = "dialog_selected_index_key";
    private final static String DIALOG_PRESSED_INDEX_KEY = "dialog_pressed_index_key";
    private final static String DIALOG_STATE_KEY = "dialog_state_key";
    private final static int ALL_DIALOGS_CLOSED = 0;
    private final static int MATERIAL_DIALOG_OPENED = 1;
    private final static int DATEPICKER_DIALOG_OPENED = 2;

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
    @BindView(R.id.group_info)
    ConstraintLayout group_info;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.disconnected_view)
    RelativeLayout disconnectedView;

    @Inject
    GroupPresenter presenter;

    MaterialDialog sortIntervalDialog;
    DatePickerDialog dpd;
    Integer dialogSelectedIndex = 0;
    Integer dialogPressedIndex = 0;
    Long sortStart = 0L;
    Long sortEnd = 0L;
    private Integer postsCount = 0;
    private Integer time = 0;

    public GroupActivity() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setupInjection();
        super.onCreate(savedInstanceState);
        Dart.inject(this);
        Log.i("mytag", groupTitle + " " + groupDescription + " " + groupIconURL);
        setContentView(R.layout.activity_group);
        ButterKnife.bind(this);
        setupGroupInformation();

        if (savedInstanceState != null) {
            setPostsAndTime(savedInstanceState.getInt(POSTS_COUNT_KEY), savedInstanceState.getInt(TIME_KEY));
            sortStart = savedInstanceState.getLong(SORT_START_KEY);
            sortEnd = savedInstanceState.getLong(SORT_END_KEY);
            dialogSelectedIndex = savedInstanceState.getInt(DIALOG_SELECTED_INDEX_KEY);
            dialogPressedIndex = savedInstanceState.getInt(DIALOG_PRESSED_INDEX_KEY);
            changeText(dialogSelectedIndex);
        }

        sortIntervalDialog = new MaterialDialog.Builder(this)
                .title(R.string.sort_interval_dialog_title)
                .items(R.array.time_interval)
                .itemsCallbackSingleChoice(
                        dialogPressedIndex,
                        (dialog, view, which, text) -> {
                            dialogPressedIndex = which;
                            return true;
                        })
                .negativeText(R.string.cancel_label)
                .onNegative((dialog, which) -> {
                    dialog.dismiss();
                    dialog.setSelectedIndex(dialogSelectedIndex);
                    dialogPressedIndex = dialogSelectedIndex;
                })
                .onPositive((dialog, which) -> {
                    Log.i("mytag", Integer.toString(dialogSelectedIndex));
                    if (dialogPressedIndex != 5) {
                        dialogSelectedIndex = dialogPressedIndex;
                        dialog.dismiss();
                        changeText(dialogSelectedIndex);
                        sortStart = 0L;
                        sortEnd = 0L;
                    }
                    else {
                        showDatePickerDialog();
                    }
                })
                .cancelListener(dialogInterface -> sortIntervalDialog.setSelectedIndex(dialogSelectedIndex))
                .positiveText(R.string.choose_label)
                .canceledOnTouchOutside(false)
                .alwaysCallSingleChoiceCallback()
                .autoDismiss(false)
                .build();

        if (savedInstanceState == null || savedInstanceState.getInt(POSTS_COUNT_KEY) == 0)
            presenter.getPostsCount(groupId);
        if (savedInstanceState != null && savedInstanceState.getInt(DIALOG_STATE_KEY, 0) == MATERIAL_DIALOG_OPENED) {
            sortIntervalDialog.show();
        }
        else if (savedInstanceState != null && savedInstanceState.getInt(DIALOG_STATE_KEY, 1) == DATEPICKER_DIALOG_OPENED) {
            sortIntervalDialog.show();
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

    private void showDatePickerDialog() {
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(now.getTimeInMillis());
        if (sortStart == 0L && sortEnd == 0L) {
            dpd = DatePickerDialog.newInstance(
                    null,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
        } else {
            Calendar from = Calendar.getInstance();
            from.setTimeInMillis(sortStart);
            Calendar to = Calendar.getInstance();
            to.setTimeInMillis(sortEnd);
            dpd = DatePickerDialog.newInstance(
                    null,
                    from.get(Calendar.YEAR),
                    from.get(Calendar.MONTH),
                    from.get(Calendar.DAY_OF_MONTH),
                    to.get(Calendar.YEAR),
                    to.get(Calendar.MONTH),
                    to.get(Calendar.DAY_OF_MONTH)
            );
        }
        Calendar vkBornDate = Calendar.getInstance();
        long vkBornMilliseconds = 1160438400000L;
        vkBornDate.setTimeInMillis(vkBornMilliseconds);
        dpd.setOnDateSetListener((view1, year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd) -> {

            sortIntervalDialog.dismiss();

            Calendar sortStartCalendar = Calendar.getInstance();
            sortStartCalendar.set(Calendar.YEAR, year);
            sortStartCalendar.set(Calendar.MONTH, monthOfYear);
            sortStartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            sortStart = sortStartCalendar.getTimeInMillis();
            Calendar sortEndCalendar = Calendar.getInstance();
            sortEndCalendar.set(Calendar.YEAR, yearEnd);
            sortEndCalendar.set(Calendar.MONTH, monthOfYearEnd);
            sortEndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonthEnd);
            sortEnd = sortEndCalendar.getTimeInMillis();
            changeText(5);
            dialogSelectedIndex = 5;
        });
        dpd.setMinDate(vkBornDate);
        dpd.setMaxDate(now);
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(POSTS_COUNT_KEY, postsCount);
        outState.putInt(TIME_KEY, time);
        outState.putLong(SORT_START_KEY, sortStart);
        outState.putLong(SORT_END_KEY, sortEnd);
        outState.putInt(DIALOG_SELECTED_INDEX_KEY, dialogSelectedIndex);
        outState.putInt(DIALOG_PRESSED_INDEX_KEY, dialogPressedIndex);
        if (dpd != null && dpd.isVisible())
            outState.putInt(DIALOG_STATE_KEY, DATEPICKER_DIALOG_OPENED);
        else if (sortIntervalDialog.isShowing())
            outState.putInt(DIALOG_STATE_KEY, MATERIAL_DIALOG_OPENED);
        else
            outState.putInt(DIALOG_STATE_KEY, ALL_DIALOGS_CLOSED);
        super.onSaveInstanceState(outState);
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
        showGroupInfo();
    }

    private void changeText(int newIndex) {
        Log.i("mytag", "In changeText method, new index = " + String.valueOf(newIndex) + ", time = " + time);
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
                case 4:
                    averageTimeLabel.setText(R.string.max_time_label);
                    if (time > 1) {
                        // Number of maximum posts per day(50) divided by speed of getting posts
                        // (250 per second) and plus additional second(1)
                        setTime(1);
                    } else {
                        setTime(time);
                    }
                    break;
                case 5:
                    averageTimeLabel.setText(R.string.max_time_label);
                    int range = (int) ((System.currentTimeMillis() / 1000) - sortStart / 1000);
                    range = (range / 432000) + 1;
                    if (time < range)
                        setTime(time);
                    else
                        setTime(range);
            }
    }


    private void setTime(Integer time) {
        if (time < 60) {
            timeNumberTextView.setText(getResources().getQuantityString(R.plurals.seconds, time, time));
        } else {
            time = time / 60;
            timeNumberTextView.setText(getResources().getQuantityString(R.plurals.minutes, time, time));
        }
    }

    public void showDisconnectedView() {
        loadingIndicator.setVisibility(View.GONE);
        group_info.setVisibility(View.GONE);
        disconnectedView.setVisibility(View.VISIBLE);
    }

    public void showGroupInfo() {
        loadingIndicator.setVisibility(View.GONE);
        group_info.setVisibility(View.VISIBLE);
        disconnectedView.setVisibility(View.GONE);
    }

    public void showLoadingIndicator() {
        loadingIndicator.setVisibility(View.VISIBLE);
        group_info.setVisibility(View.GONE);
        disconnectedView.setVisibility(View.GONE);
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

    @OnClick({R.id.sort_button, R.id.sort_interval_button, R.id.disconnected_button})
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
                        .sortStart(sortStart)
                        .sortEnd(sortEnd)
                        .build();
                startActivity(intent);
                overridePendingTransition(0, R.anim.screen_splash_fade_out);
                break;
            case R.id.sort_interval_button:
                sortIntervalDialog.show();
                break;
            case R.id.disconnected_button:
                presenter.getPostsCount(groupId);
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
