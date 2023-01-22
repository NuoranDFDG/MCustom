package com.minecraft.mcustom.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.minecraft.mcustom.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("设置");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        boolean spIsAuth = sharedPreferences.getBoolean("is_auth", false);  // 第一个参数是key，第二个是根据key取不到值时的默认值
        String spUsername = sharedPreferences.getString("username", "");
        String spPassword = sharedPreferences.getString("password", "");
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            // 通过xml设置的key找到要监听的项
            Preference sPEditUsername = findPreference("username");
            Preference sPEditPassword = findPreference("password");
            // 设置监听
            if (sPEditUsername != null) {
                sPEditUsername.setOnPreferenceChangeListener(this);
            }
            if (sPEditPassword != null) {
                sPEditPassword.setOnPreferenceChangeListener(this);
            }

        }

        @Override
        public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
            return true;
        }
    }

}