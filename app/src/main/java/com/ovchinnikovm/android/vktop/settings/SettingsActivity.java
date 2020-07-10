package com.ovchinnikovm.android.vktop.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREF_NOTIFICATION_SWITCH = "enable_notifications";
    public static final String KEY_PREF_NOTIFICATION_VIBRATE = "notifications_vibrate";
    public static final String KEY_PREF_NOTIFICATION_SOUND = "pref_tone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}
