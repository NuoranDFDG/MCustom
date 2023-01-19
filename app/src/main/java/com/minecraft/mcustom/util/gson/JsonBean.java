package com.minecraft.mcustom.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonBean {
    private final static Gson gson= new GsonBuilder().serializeNulls().create();
    /**
     * 实体对象转json字符串
     * ****gson.toJson(bean);
     * json字符串转实体类
     * ****bean=gson.fromJson(json,bean.class);
     * 实体类集合对象转json数组
     * ****jsonArray=gson.toJson(beanList);
     * json数组转实体类对象
     * ****List<User> users=gson.fromJson(json,new TypeToken<List<User>>() {}.getType());
     */
    public static Gson getGson() {
        return gson;
    }
}
