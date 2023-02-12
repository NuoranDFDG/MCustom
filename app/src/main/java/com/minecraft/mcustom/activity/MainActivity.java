package com.minecraft.mcustom.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.rxbinding3.view.RxView;
import com.minecraft.mcustom.FloatingWindowService;
import com.minecraft.mcustom.R;
import com.minecraft.mcustom.entity.Result;
import com.minecraft.mcustom.ui.About;
import com.minecraft.mcustom.util.file.DataFileUtility;
import com.minecraft.mcustom.util.gson.JsonBean;
import com.minecraft.mcustom.util.http.HttpAddress;
import com.minecraft.mcustom.util.http.HttpUrl;
import com.minecraft.mcustom.util.http.OKHttpUtil;
import com.minecraft.mcustom.util.websocket.SocketService;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;

public class MainActivity extends AppCompatActivity {

    final private static Gson gson= JsonBean.getGson();
    private long backPressedTime;

    private String token;

    LinearLayout mainList;

    private ImageView startIcon;
    private TextView startText;

    @SuppressLint({"ClickableViewAccessibility", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = getIntent();
            if (Objects.equals(intent.getStringExtra("extra_data"), "offI")) {
                InformationActivity.instance.finish();
            }
            if (Objects.equals(intent.getStringExtra("extra_data"), "off")) {
                LoginActivity.instance.finish();
            }
        }
        String tokenUser = DataFileUtility.readFileToData("token", getApplicationContext());
        if (tokenUser!=null) {
            token = tokenUser;
        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        startIcon = findViewById(R.id.main_run);
        startText = findViewById(R.id.start_main_text);
        mainList = findViewById(R.id.list_main2);

        new Thread(()->{
            String result = OKHttpUtil.postAsyncRequest(HttpUrl.getBaseUrl(), "{'token':'"
                    + token
                    + "'}", "mailbox", "situation");
            if (!Objects.equals(result, "")) {
                Result rs = null;
                try {
                    rs = gson.fromJson(result, Result.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                assert rs != null;
                if (rs.getCode()==400) {
                    mainList.postDelayed(() -> {
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        View cardViewLayout = inflater.inflate(R.layout.mailbox_binding, mainList, false);
                        cardViewLayout.findViewById(R.id.binding_mailbox).setOnClickListener(view -> {
                        });
                        mainList.addView(cardViewLayout);
                    }, 10);
                }
            }
        }).start();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MCustom");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_setting:
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                case R.id.action_run:
                    RxView.clicks(this.findViewById(R.id.action_run))
                            .throttleFirst(1000L, TimeUnit.MILLISECONDS) // 1秒内只有第一次点击有效
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(x -> {
                                if (!FloatingWindowService.isFloatingWindowShowing) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        if (!Settings.canDrawOverlays(this)) {
                                            Intent intent2 = new Intent(this, Aauthority.class);
                                            startService(intent2);
                                        } else {
                                            start_info(true);
                                            startService(new Intent(MainActivity.this, FloatingWindowService.class));
                                            Intent websocketServiceIntent = new Intent(this, SocketService.class);
                                            startService(websocketServiceIntent);

                                        }
                                    }
                                }
                            });
                    break;
                case R.id.action_about:
                    About about = new About();
                    about.setmActivity(MainActivity.this);
                    about.show();
                    break;
            }
            return true;
        });
        findViewById(R.id.pluginAbout).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
            intent.putExtra("extra_data", "http://mcustom.asia/document");
            startActivity(intent);
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
                                start_info(true);
                                startService(new Intent(MainActivity.this, FloatingWindowService.class));
                                Intent websocketServiceIntent = new Intent(this, SocketService.class);
                                startService(websocketServiceIntent);
                            }
                        }
                    }
                });
    }

    @SuppressLint("SetTextI18n")
    public void start_info(boolean ln) {
        if (ln) {
            startIcon.setBackgroundResource(R.drawable.circle3);
            startIcon.setImageResource(R.drawable.start_icon);
            startText.setText("MCustom 正在运行");
        } else {
            startIcon.setBackgroundResource(R.drawable.circle2);
            startIcon.setImageResource(R.drawable.mcustom_stopped);
            startText.setText("MCustom 没有运行");
        }
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