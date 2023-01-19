package com.minecraft.mcustom;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {

    private static final OkHttpClient client = new OkHttpClient();

    public static String sendGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            String str = response.body().string();
            return str.substring(1, str.length() - 1).replace("\\n", "<br>");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
