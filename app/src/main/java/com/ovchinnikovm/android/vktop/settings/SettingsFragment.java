package com.ovchinnikovm.android.vktop.settings;


import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.XpPreferenceFragment;
import android.text.TextUtils;
import android.widget.Toast;

import com.ovchinnikovm.android.vktop.R;

import net.xpece.android.support.preference.PreferenceCategory;
import net.xpece.android.support.preference.RingtonePreference;
import net.xpece.android.support.preference.SafeRingtone;

public class SettingsFragment extends XpPreferenceFragment {

    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = (preference, value) -> {
        String stringValue = value.toString();

        if (preference instanceof RingtonePreference) {
            // For ringtone preferences, look up the correct display value using RingtoneManager.
            if (TextUtils.isEmpty(stringValue)) {
                // Empty values correspond to 'silent' (no ringtone).
                preference.setSummary(R.string.pref_tone_silent);
            } else {
                final Context context = preference.getContext();
                final Uri selectedUri = Uri.parse(stringValue);
                final SafeRingtone ringtone = SafeRingtone.obtain(context, selectedUri);
                try {
                    final String name = ringtone.getTitle();

                    // Set the summary to reflect the new ringtone display name.
                    preference.setSummary(name);
                } finally {
                    ringtone.stop();
                }
            }

        } else {
            // For all other preferences, set the summary to the value's
            // simple string representation.
            preference.setSummary(stringValue);
        }
        return true;
    };

    @Override
    public void onCreatePreferences2(Bundle savedInstanceState, String rootKey) {
        // Set an empty screen so getPreferenceScreen doesn't return null -
        // so we can create fake headers from the get-go.
        setPreferenceScreen(getPreferenceManager().createPreferenceScreen(getPreferenceManager().getContext()));

        // Add 'notifications' preferences, and a corresponding header.
        PreferenceCategory fakeHeader = new PreferenceCategory(getPreferenceManager().getContext());
        fakeHeader.setTitle(R.string.notifications_title);
        getPreferenceScreen().addPreference(fakeHeader);
        addPreferencesFromResource(R.xml.pref_notifications);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            bindPreferenceSummaryToValue(findPreference("pref_tone"));
        }
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        final String key = preference.getKey();
        String value = PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(key, "");
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, value);
    }
}
