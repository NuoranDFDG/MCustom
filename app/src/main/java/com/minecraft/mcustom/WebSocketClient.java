package com.minecraft.mcustom;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class WebSocketClient {
    private static final int NORMAL_CLOSURE_STATUS = 1000;

    private OkHttpClient client;
    private Request request;
    private WebSocket webSocket;

    // 连接成功时的回调
    private OnOpenListener onOpenListener;
    // 接收到消息时的回调
    private OnMessageListener onMessageListener;
    // 连接关闭时的回调
    private OnCloseListener onCloseListener;

    public WebSocketClient(String url) {
        client = new OkHttpClient.Builder()
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();
        request = new Request.Builder().url(url).build();
    }

    public void setOnOpenListener(OnOpenListener listener) {
        onOpenListener = listener;
    }

    public void setOnMessageListener(OnMessageListener listener) {
        onMessageListener = listener;
    }

    public void setOnCloseListener(OnCloseListener listener) {
        onCloseListener = listener;
    }

    public void connect() {
        webSocket = client.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                if (onOpenListener != null) {
                    onOpenListener.onOpen();
                }
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                if (onMessageListener != null) {
                    onMessageListener.onMessage(text);
                }
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                if (onCloseListener != null) {
                    onCloseListener.onClose();
                }
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                t.printStackTrace();
            }
        });
    }

    public void send(String message) {
        webSocket.send(message);
    }

    public void close() {
        webSocket.close(NORMAL_CLOSURE_STATUS, "Normal closure");
    }

    public interface OnOpenListener {
        void onOpen();
    }

    public interface OnMessageListener {
        void onMessage(String message);
    }

    public interface OnCloseListener {
        void onClose();
    }
}
