package com.minecraft.mcustom.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.minecraft.mcustom.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;


@RequiresApi(api = Build.VERSION_CODES.M)
public class Aauthority extends AppCompatActivity {

    public static Aauthority instance = null;
    private LinearLayout rightSuspend;
    private LinearLayout rightSave;

    private ImageView rightFD;

    private ImageView rightCC;

    @SuppressLint({"CheckResult", "UseCompatLoadingForDrawables"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.authority);
        instance=this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Aauthority.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
            Toolbar toolbar = findViewById(R.id.toolbar_get);
            toolbar.setTitle("MCustom");
            toolbar.inflateMenu(R.menu.menu_browser);

            rightFD = findViewById(R.id.right_fd);
            rightCC = findViewById(R.id.right_cc);
            rightSuspend = findViewById(R.id.float_right);
            rightSave = findViewById(R.id.save_right);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final Button rightAll = findViewById(R.id.right_all);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) final Button rightContinue = findViewById(R.id.continue_right);
            if (Settings.canDrawOverlays(this)) {
                rightFD.setBackgroundResource(R.drawable.circle3);
                rightFD.setImageDrawable(getResources().getDrawable(R.drawable.baseline_check_24));
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                rightCC.setBackgroundResource(R.drawable.circle3);
                rightCC.setImageDrawable(getResources().getDrawable(R.drawable.baseline_check_24));
            }
            rightSuspend.setOnClickListener(view -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1023);
                    }
                }
            });
            rightSave.setOnClickListener(view -> {
                RxPermissions permissions = new RxPermissions(this);
                permissions.setLogging(true);
                permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @SuppressLint("UseCompatLoadingForDrawables")
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    RxPermissions permissions = new RxPermissions(instance);
                                    permissions.setLogging(true);
                                    permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                                            .subscribe(new Consumer<Boolean>() {
                                                @SuppressLint("UseCompatLoadingForDrawables")
                                                @Override
                                                public void accept(Boolean aBoolean) throws Exception {
                                                    if (aBoolean) {
                                                        rightCC.setBackgroundResource(R.drawable.circle3);
                                                        rightCC.setImageDrawable(getResources().getDrawable(R.drawable.baseline_check_24));
                                                    }
                                                }
                                            });
                                }
                            }
                        });
            });
            rightAll.setOnClickListener(view -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        if (!Environment.isExternalStorageManager()) {
                            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 1024);
                        }
                    }
                }
            });
            rightContinue.setOnClickListener(view -> {
                if (Settings.canDrawOverlays(this)) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        Intent intent = new Intent(Aauthority.this, LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(this, "权限不完整", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "权限不完整", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    // 带回授权结果
    @SuppressLint({"CheckResult", "UseCompatLoadingForDrawables"})
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1023 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Settings.canDrawOverlays(this)) {
                rightFD.setBackgroundResource(R.drawable.circle3);
                rightFD.setImageDrawable(getResources().getDrawable(R.drawable.baseline_check_24));
            }
        }
        if (requestCode == 1024 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Settings.canDrawOverlays(this)) {
                rightFD.setBackgroundResource(R.drawable.circle3);
                rightFD.setImageDrawable(getResources().getDrawable(R.drawable.baseline_check_24));
                RxPermissions permissions = new RxPermissions(this);
                permissions.setLogging(true);
                permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Consumer<Boolean>() {
                            @Override
                            public void accept(Boolean aBoolean) throws Exception {
                                if (aBoolean) {
                                    rightCC.setBackgroundResource(R.drawable.circle3);
                                    rightCC.setImageDrawable(getResources().getDrawable(R.drawable.baseline_check_24));
                                }
                            }
                        });
            }
        }
    }
}
