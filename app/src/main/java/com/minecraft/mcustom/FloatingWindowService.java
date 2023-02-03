package com.minecraft.mcustom;

import com.minecraft.mcustom.util.ConvertUtil;
import com.minecraft.mcustom.play.getShop;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FloatingWindowService extends Service {
    private WindowManager windowManager;
    private Context mService;
    private LinearLayout floatingWindow1;
    private FrameLayout floatingWindow2;
    private MediaPlayer player;

    private int clickedButtonIndex = -1;

    public static boolean isFloatingWindowShowing = false;
    public static boolean isFloatingWindow2Showing = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        mService=this;
        player = MediaPlayer.create(this, R.raw.u1nz5_geywz);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startXFCMain();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (floatingWindow1 != null) {
            windowManager.removeView(floatingWindow1);
        }
        if (floatingWindow2 != null) {
            windowManager.removeView(floatingWindow2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"ClickableViewAccessibility", "InflateParams", "RtlHardcoded"})
    public void startXFCMain() {
        // x50 y130
        isFloatingWindowShowing = true;
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        // 加载布局文件
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        floatingWindow1 = (LinearLayout) inflater.inflate(R.layout.floating_window, null);
// 设置悬浮窗参数
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 50;
        params.y = 130;
        // 显示悬浮窗
        windowManager.addView(floatingWindow1, params);
        if (isFloatingWindowShowing) {
            Button button1 = floatingWindow1.findViewById(R.id.XFCbuttonn);
            button1.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    button1.setBackgroundResource(R.drawable.retouch_2022122219030731);
                    button1.setAlpha(1.0f);
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    button1.setBackgroundResource(R.drawable.retouch_2022122214163714);
                    button1.setAlpha(0.75f);
                    try {
                        player.prepare();
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                    player.start();
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        // 当前是横屏
                        if (isFloatingWindowShowing) {
                            windowManager.removeView(floatingWindow1);
                            isFloatingWindow2Showing = true;
                            startFloatingWindow2();
                        }
                    }
                }
                return false;
            });
        }
    }

    @SuppressLint({"ClickableViewAccessibility", "UseCompatLoadingForDrawables", "InflateParams", "RtlHardcoded"})
    public void startFloatingWindow2() {
        mService = this;
        isFloatingWindow2Showing = true;
        windowManager = (WindowManager) mService.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        // 加载布局文件
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        floatingWindow2 = (FrameLayout) inflater.inflate(R.layout.shopping, null);
        // 设置悬浮窗参数

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.height = getScreenHeight(mService) + getStatusBarHeight(mService);
        params.flags =WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN//将window放置在整个屏幕之内,无视其他的装饰(比如状态栏)
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS//允许window扩展值屏幕之外
                | WindowManager.LayoutParams.FLAG_FULLSCREEN;//当这个window显示的时候,隐藏所有的装饰物(比如状态栏)这个flag允许window使用整个屏幕区域
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        params.format = PixelFormat.RGBA_8888;
        setTheme(R.style.AppTheme);
        params.type = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ? WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY : WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        if (windowManager.getDefaultDisplay() != null && windowManager.getDefaultDisplay().isValid()) {
            windowManager.addView(floatingWindow2, params);
        }
        LinearLayout linearLayout = floatingWindow2.findViewById(R.id.menu_list);
        linearLayout.post(() -> {
            ArrayList<List> shopList = getShop.getShopList();
            if (shopList != null) {
                Button[] buttons = new Button[shopList.size()];
                for (int i = 0; i < shopList.size(); i++) {
                    Button button = new Button(this);
                    button.setText((CharSequence) shopList.get(i).get(0));
                    button.setBackgroundResource(R.drawable.release_button_com_netease_x19);
                    ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    );

                    layoutParams.height = ConvertUtil.convertDpToPixel(35, getApplicationContext());
                    layoutParams.bottomMargin = ConvertUtil.convertDpToPixel(6, getApplicationContext());
                    button.setLayoutParams(layoutParams);
                    button.setOnClickListener(v -> {
                        clickedButtonIndex = (int) v.getTag();
                        v.setEnabled(false);
                        v.setBackgroundResource(R.drawable.pin_button_com_neteas_x19);
                        try {
                            player.prepare();
                        } catch (IllegalStateException | IOException e) {
                            e.printStackTrace();
                        }
                        player.start();
                        for (int j = 0; j < buttons.length; j++) {
                            if (j != clickedButtonIndex) {
                                buttons[j].setEnabled(true);
                                buttons[j].setBackgroundResource(R.drawable.release_button_com_netease_x19);
                            }
                        }
                        // 开启指定的界面
                        // ...
                    });
                    button.setTag(i);
                    buttons[i] = button;
                    linearLayout.addView(button);
                }
            }
        });
        // 显示悬浮窗
        if (isFloatingWindow2Showing) {
            Button button2 = floatingWindow2.findViewById(R.id.shoppingback);
            button2.setOnTouchListener((view, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    try {
                        player.prepare();
                    } catch (IllegalStateException | IOException e) {
                        e.printStackTrace();
                    }
                    player.start();

                    if (isFloatingWindow2Showing) {
                        windowManager.removeView(floatingWindow2);
                        isFloatingWindow2Showing = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            startXFCMain();
                        }
                    }
                }
                return false;
            });
        }
    }
    public static int getStatusBarHeight(@NonNull Context context) {
        Resources resources = context.getResources();
        @SuppressLint({"DiscouragedApi", "InternalInsetResource"}) int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

}


