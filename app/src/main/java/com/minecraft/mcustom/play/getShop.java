package com.minecraft.mcustom.play;

import android.content.Context;

import com.google.gson.Gson;
import com.minecraft.mcustom.entity.ListData;
import com.minecraft.mcustom.util.gson.JsonBean;
import com.minecraft.mcustom.util.http.HttpUrl;
import com.minecraft.mcustom.util.http.OKHttpUtil;
import com.minecraft.mcustom.util.https.HttpsUtil;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class getShop {
    final private static Gson gson= JsonBean.getGson();

    public static ArrayList<List> getShopList(Context context) throws Exception {
        String jsonResult = HttpsUtil.HttpsPost("", context, "get", "shopList");
        byte[] utf8Bytes = jsonResult.getBytes(StandardCharsets.UTF_8);
        String utf8Str = new String(utf8Bytes, StandardCharsets.UTF_8);
        ListData result = gson.fromJson(utf8Str, ListData.class);
        return result.getData();
    }

}
