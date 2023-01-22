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

import com.minecraft.mcustom.FloatingWindowService;
import com.minecraft.mcustom.R;
import com.minecraft.mcustom.ui.About;

public class MainActivity extends AppCompatActivity {

    private Context context;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        LoginActivity.instance.finish();
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MCustom");
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_setting:
                        Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.action_run:

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
                intent.putExtra("extra_data", "http://124.222.157.95:7913");
                startActivity(intent);
            }
        });

        Button start_button = this.findViewById(R.id.start_mcustom);
        start_button.setOnTouchListener((view, motionEvent) -> {
            if (!FloatingWindowService.isFloatingWindowShowing) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
                        startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
                    } else {
                        startService(new Intent(MainActivity.this, FloatingWindowService.class));
                    }
                }
            }
            return true;
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            long time = 0;
            if(System.currentTimeMillis()-time>2000){
                time=System.currentTimeMillis();
            }else{
                moveTaskToBack(true);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

//
//    private MediaPlayer mediaPlayer;
//    private boolean isPaused;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        setContentView(R.layout.activity_main);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                String responseBody = OkHttpUtils.sendGetRequest("http://124.222.157.95:7913/get/gonggao");
//                TextView Announcemen = findViewById(R.id.announcementTextView);
//                if (responseBody != null) {
//                    Spanned spanned = Html.fromHtml(responseBody);
//                    Announcemen.setText(spanned);
//                } else {
//                    Announcemen.setText("公告获取失败qwq");
//                }
//            }
//        });
//        thread.start();
//
//
//
//
//        SurfaceView surfaceView = findViewById(R.id.surface_view);
//        surfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
//        mediaPlayer = new MediaPlayer();
//        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.febec8f8);
//        try {
//            mediaPlayer.setDataSource(this,videoUri);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                mediaPlayer.setSurface(holder.getSurface());
//                mediaPlayer.prepareAsync();
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//                // Do nothing
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//                // Do nothing
//            }
//        });
//        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mediaPlayer) {
//                mediaPlayer.setLooping(true);
//                mediaPlayer.start();
//            }
//        });
//        MediaPlayer player = MediaPlayer.create(this, R.raw.u1nz5_geywz);
//        Button button0 = this.findViewById(R.id.applist0);
//        button0.setOnTouchListener((view, motionEvent) -> {
//                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                        // 按住按钮时的效果
//                        button0.setBackgroundResource(R.drawable.pin_button_com_neteas_x19);
//                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//// 不按住按钮时的效果
//                        button0.setBackgroundResource(R.drawable.release_button_com_netease_x19);
//                        if (FloatingWindowService.isFloatingWindowShowing == false) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                if (!Settings.canDrawOverlays(this)) {
//                                    Toast.makeText(this, "当前无权限，请授权", Toast.LENGTH_SHORT).show();
//                                    startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
//                                } else {
//                                    startService(new Intent(MainActivity.this, FloatingWindowService.class));
//                                }
//                            }
//                        }
//
//                        try {
//                            player.prepare();
//                        } catch (IllegalStateException e) {
//// TODO Auto-generated catch block
//                            e.printStackTrace();
//                        } catch (IOException e) {
//// TODO Auto-generated catch block
//                            e.printStackTrace();
//                        }
//                        player.start();
//                    }
//            return false;
//        });
//        Button button = this.findViewById(R.id.applist);
//        button.setOnTouchListener((view, motionEvent) -> {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                // 按住按钮时的效果
//                button.setBackgroundResource(R.drawable.pin_button_com_neteas_x19);
//            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//// 不按住按钮时的效果
//                button.setBackgroundResource(R.drawable.release_button_com_netease_x19);
//                try {
//                    Intent intent = new Intent();
//                    intent.setPackage("com.netease.x19");
//                    startActivity(intent);
//                } catch (Exception e) {
//                    Toast.makeText(this, "未找到APP", Toast.LENGTH_SHORT).show();
//                }
//
//                try {
//                    player.prepare();
//                } catch (IllegalStateException e) {
//// TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//// TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                player.start();
//            }
//            return false;
//        });
//
//        Button button1 = this.findViewById(R.id.applist1);
//        button1.setOnTouchListener((view, motionEvent) -> {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                // 按住按钮时的效果
//                button1.setBackgroundResource(R.drawable.pin_button_com_neteas_x19);
//            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//// 不按住按钮时的效果
//                button1.setBackgroundResource(R.drawable.release_button_com_netease_x19);
//                Intent intent= new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse("https://m.bilibili.com/space/694958153?from=search");
//                intent.setData(content_url);
//                startActivity(intent);
//
//                try {
//                    player.prepare();
//                } catch (IllegalStateException e) {
//// TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//// TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                player.start();
//            }
//            return false;
//        });
//
//        Button button2 = this.findViewById(R.id.applist2);
//        button2.setOnTouchListener((view, motionEvent) -> {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                // 按住按钮时的效果
//                button2.setBackgroundResource(R.drawable.pin_button_com_neteas_x19);
//            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//// 不按住按钮时的效果
//                button2.setBackgroundResource(R.drawable.release_button_com_netease_x19);
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setMessage("by nuoran 1.0.0")
//                        .setPositiveButton("返回", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                // User clicked Yes button
//                            }
//                        });
//// Create the AlertDialog object and show it
//                AlertDialog dialog = builder.create();
//                dialog.show();
//
//                try {
//                    player.prepare();
//                } catch (IllegalStateException e) {
//// TODO Auto-generated catch block
//                    e.printStackTrace();
//                } catch (IOException e) {
//// TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                player.start();
//            }
//            return false;
//        });
//
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 0) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (!Settings.canDrawOverlays(this)) {
//                    Toast.makeText(this, "授权失败", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "授权成功", Toast.LENGTH_SHORT).show();
//                    startService(new Intent(MainActivity.this, FloatingWindowService.class));
//                }
//            }
//        }
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.pause();
//            isPaused = true;
//        }
//
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (isPaused) {
//            mediaPlayer.start();
//            isPaused = false;
//        }
//    }
//}
//

