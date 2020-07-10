package com.ovchinnikovm.android.vktop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;

import com.afollestad.materialdialogs.MaterialDialog;
import com.ovchinnikovm.android.vktop.main.MainActivity;
import com.squareup.leakcanary.RefWatcher;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;
import com.vk.sdk.util.VKUtil;

public class LoginActivity extends AppCompatActivity {

    public static final String KEY_PREF_CURRENT_USER = "current_user_key";

    MaterialDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        dialog = new MaterialDialog.Builder(this)
                .title(R.string.login_title)
                .customView(R.layout.dialog_login, false)
                .cancelable(false)
                .build();
        dialog.getCustomView()
                .findViewById(R.id.login_by_yourself_button)
                .setOnClickListener( view -> VKSdk.login(this, false, VKScope.GROUPS));

        Button loginThroughAppButton = dialog.getCustomView().findViewById(R.id.login_through_app_button);
        if (VKUtil.isAppInstalled(getApplicationContext(), "com.vkontakte.android")) {
            loginThroughAppButton.setOnClickListener(view -> VKSdk.login(this, true, VKScope.GROUPS));
        } else {
            loginThroughAppButton.setEnabled(false);
            loginThroughAppButton.setClickable(false);
        }
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken token) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_PREF_CURRENT_USER, Integer.valueOf(token.userId));
                editor.apply();
                navigateToMainScreen();
            }
            @Override
            public void onError(VKError error) {

            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void navigateToMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
        //        | Intent.FLAG_ACTIVITY_NEW_TASK
        //        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        dialog.dismiss();
        startActivity(intent);
        //overridePendingTransition( 0, R.anim.screen_splash_fade_out );
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = VkTopApp.getRefWatcher();
        refWatcher.watch(this);
    }
}
