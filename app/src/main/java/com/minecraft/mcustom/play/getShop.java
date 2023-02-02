package com.minecraft.mcustom.play;

import com.google.gson.Gson;
import com.minecraft.mcustom.entity.ListData;
import com.minecraft.mcustom.util.gson.JsonBean;
import com.minecraft.mcustom.util.http.HttpUrl;
import com.minecraft.mcustom.util.http.OKHttpUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class getShop {
    final private static Gson gson= JsonBean.getGson();

    public static ArrayList<List> getShopList() {
        String jsonResult = OKHttpUtil.getAsyncRequest(HttpUrl.getBaseUrl(), "get", "shopList");
        byte[] utf8Bytes = jsonResult.getBytes(StandardCharsets.UTF_8);
        String utf8Str = new String(utf8Bytes, StandardCharsets.UTF_8);
        // 数据传输没有任何加密处理，就算我写了一个RSA加解密工具类，也不会用的。
        ListData result=gson.fromJson(utf8Str, ListData.class);
        return result.getData();
    }

}
