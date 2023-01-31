package com.minecraft.mcustom.util.websocket;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.minecraft.mcustom.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketService extends Service{

    private Socket sock;
    private OutputStream outx;
    private InputStream inx;

    private final Handler handler = new Handler(Looper.getMainLooper());


    private MediaPlayer player;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        player = MediaPlayer.create(this, R.raw.xbox_live_notification);
        new Socket_thread(SocketUrl.getBaseUrl()).start();
    }

    /**
     * 连接服务器
     */
    class Socket_thread extends Thread {
        private String IP = "";
        private int PORT = 0;

        public Socket_thread(String strip) {
            String[] stripx = strip.split(":");
            this.IP = stripx[0];
            this.PORT = Integer.parseInt(stripx[1]);
        }

        @Override
        public void run() {
            try {
                disSocket();
                sock = new Socket(this.IP, this.PORT);
            } catch (Exception e) {
                e.printStackTrace();
                new Socket_thread(SocketUrl.getBaseUrl()).start();
                return;
            }
            try {
                outx = sock.getOutputStream();
                inx = sock.getInputStream();
                sendStrSocket("连接成功");
            } catch (Exception e) {
                e.printStackTrace();
                new Socket_thread(SocketUrl.getBaseUrl()).start();
                return;
            }
            new Inx().start();
        }
    }


    /**
     * 循环接收数据
     */
    class Inx extends Thread {
        @Override
        public void run() {
            while (true) {
                byte[] bu = new byte[1024];
                try {
                    int conut = inx.read(bu);//设备重启，异常 将会一直停留在这
                    if (conut == -1) {
                        disSocket();
                        new Socket_thread(SocketUrl.getBaseUrl()).start();
                        return;
                    }
                    String strread = new String(bu, StandardCharsets.UTF_8).trim();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            startXFCMain(strread.replace("runSTRXFC ", ""));
                        }
                    });

                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 发送消息
     */
    private void sendStrSocket(final String senddata) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    outx.write(senddata.getBytes(StandardCharsets.UTF_8));
                } catch (Exception ignored) {
                }
            }
        }).start();
    }

    /**
     * 断开连接
     */
    private void disSocket() {
        if (sock != null) {
            try {
                outx.close();
                inx.close();
                sock.close();
                sock = null;
            } catch (Exception ignored) {
            }
        }
    }

    public void startXFCMain(String point) {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        // 加载布局文件
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout floatingWindow1 = (LinearLayout) inflater.inflate(R.layout.point_out, null);
        // 显示悬浮窗
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.LEFT | Gravity.TOP;
        params.x = 0;
        params.y = 0;
        windowManager.addView(floatingWindow1, params);
        TextView pointText = floatingWindow1.findViewById(R.id.point_text);
        pointText.setText(point);
        floatingWindow1.post(new Runnable() {
            public void run() {
                try {
                    player.prepare();
                } catch (IllegalStateException | IOException e) {
                    e.printStackTrace();
                }
                player.start();
                ObjectAnimator animator = ObjectAnimator.ofFloat(floatingWindow1, "translationY", -100f, 0f);
                animator.setDuration(500);
                animator.start();
                floatingWindow1.setAlpha(1);
            }
        });
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {}
            public void onFinish() {
                windowManager.removeView(floatingWindow1);
            }
        }.start();

        sendStrSocket("执行成功");

    }

}
