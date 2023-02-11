package com.minecraft.mcustom.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.jakewharton.rxbinding3.view.RxView;
import com.minecraft.mcustom.FloatingWindowService;
import com.minecraft.mcustom.R;
import com.minecraft.mcustom.ui.About;
import com.minecraft.mcustom.util.websocket.SocketService;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        LoginActivity.instance.finish();
        Context context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MCustom");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_setting:
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_run:
                        runMCustom();
                        break;
                    case R.id.action_about:
                        About about = new About();
                        about.setmActivity(MainActivity.this);
                        about.show();
                        break;
                }
                return true;
            }
        });
        findViewById(R.id.pluginAbout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("extra_data", "http://mcustom.asia/document");
                startActivity(intent);
            }
        });
        runMCustom();
    }

    @SuppressLint("CheckResult")
    public void runMCustom() {
        RxView.clicks(this.findViewById(R.id.start_mcustom))
                .throttleFirst(1000L, TimeUnit.MILLISECONDS) // 1秒内只有第一次点击有效
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                    if (!FloatingWindowService.isFloatingWindowShowing) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (!Settings.canDrawOverlays(this)) {
                                Intent intent = new Intent(this, Aauthority.class);
                                startService(intent);
                            } else {
                                startService(new Intent(MainActivity.this, FloatingWindowService.class));
                                Intent websocketServiceIntent = new Intent(this, SocketService.class);
                                startService(websocketServiceIntent);
                            }
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - backPressedTime > 2000) {
            Toast.makeText(this, "再按一次返回主页", Toast.LENGTH_SHORT).show();
            backPressedTime = currentTime;
        } else {
            super.onBackPressed();
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

}