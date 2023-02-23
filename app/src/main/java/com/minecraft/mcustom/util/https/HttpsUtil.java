package com.minecraft.mcustom.util.https;

import com.minecraft.mcustom.R;
import com.minecraft.mcustom.util.EncryptionUtil;
import com.minecraft.mcustom.util.http.HttpUrl;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class HttpsUtil {

    private static final String httpUrl = HttpUrl.getBaseUrl();

    private static String jsonString;

    private static String responseString;

    public static String HttpsPost(String json, Context context, String... args) {
        List<String> result=new ArrayList<>();
        StringBuilder type = new StringBuilder();
        for (String arg : args) {
            type.append("/").append(arg);
        }
        InputStream keyStore = context.getResources().openRawResource(R.raw.mc);
        RequestQueue queue = Volley.newRequestQueue(context,
                new ExtHttpClientStack(new SslHttpClient(keyStore, "Nuoran0424", 443)));
        try {
            JSONObject jsonObject = new JSONObject(json);
            jsonObject.put("type", type);
            jsonString = jsonObject.toString();
            jsonString = EncryptionUtil.encrypt(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        StringRequest myReq = new StringRequest(Request.Method.POST, httpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ByteArrayInputStream in;
                        in = new ByteArrayInputStream(response.getBytes(StandardCharsets.ISO_8859_1));
                        GZIPInputStream gzip;
                        try {
                            gzip = new GZIPInputStream(in);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        byte[] buffer = new byte[256];
                        int n;
                        while (true) {
                            try {
                                if (!((n = gzip.read(buffer)) >= 0)) break;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            out.write(buffer, 0, n);
                        }
                        try {
                            result.add(EncryptionUtil.decrypt(out.toString("utf-8")));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                createMyReqErrorListener()) {
            @Override
            public byte[] getBody() {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    GZIPOutputStream gzipStream = new GZIPOutputStream(outputStream);
                    gzipStream.write(jsonString.getBytes(StandardCharsets.UTF_8));
                    gzipStream.close();
                    return outputStream.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            @Override
            public String getBodyContentType() {
                return "application/x-gzip";
            }
        };
        queue.add(myReq);
        int count=1;
        while(result.size()==0){
            try {
                TimeUnit.MILLISECONDS.sleep(10);//等待xx毫秒
                if(count++>300){
                    result.add("{'code':114}");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return result.get(0);
    }

    private static Response.ErrorListener createMyReqErrorListener() {
        return error -> {

        };
    }
}
