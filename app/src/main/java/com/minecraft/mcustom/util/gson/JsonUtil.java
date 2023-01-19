package com.minecraft.mcustom.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * json工具类
 */
public class JsonUtil {
    private static Gson gson= new GsonBuilder().serializeNulls().create();
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
    /**
     * 实体对象转json字符串
     * @param bean bean对象
     * @return json字符串
     */
    public static String beanToJson(Object bean){
        String json=null;
        try{
            json=gson.toJson(bean);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**因为函数返回是立刻执行的，而result要在请求完成之后才能获得
         * 所以需要等待result获得返回值之后再执行return*/
        delay(json==null);
        return json;
    }
    /**
     * json字符串转实体类
     * @param cls 实体类
     * @param json json字符串
     * @param <E>
     * @return
     */
    public static <E> E jsonToBean(Class<E> cls,String json){
        E bean=null;
        try {
            bean=cls.newInstance();
            bean=gson.fromJson(json,cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /**因为函数返回是立刻执行的，而result要在请求完成之后才能获得
         * 所以需要等待result获得返回值之后再执行return*/
        delay(bean==null);
        return bean;
    }
    /**
     * 实体类集合对象转json数组
     * @param beanList 实体类集合
     * @return json字符串
     */
    public static <E> String beanListToJsonArray(List<E> beanList){
        String jsonArray=null;
        try{
            jsonArray=gson.toJson(beanList);
        }catch (Exception e){
            e.printStackTrace();
        }
        /**因为函数返回是立刻执行的，而result要在请求完成之后才能获得
         * 所以需要等待result获得返回值之后再执行return*/
        delay(jsonArray==null);
        return jsonArray;
    }

    /**
     * json数组转实体类对象
     * json为json字符串
     * 方式：List<User> users=gson.fromJson(json,new TypeToken<List<User>>() {}.getType());
     */

    /**
     * json数组转实体类对象
     * @param cls 实体类对象
     * @param jsonArray json字符串
     * @param <E>
     * @return 实体类集合
     */
    public static <E> List<E> jsonArrayToBeanList(Class<E> cls, String jsonArray){
        List<E> beanList=new ArrayList<>();
        List list=null;
        E entity=null;
        try{
            entity=cls.newInstance();
            list=gson.fromJson(jsonArray,List.class);
            for(int i=0;i<list.size();i++){
                String json=""+ list.get(i)+"";
                entity= gson.fromJson(json,cls);
                beanList.add(entity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return beanList;
    }
    /**
     * 延时
     * @param condition 条件
     */
    private static void delay(boolean condition){
        while(condition){
            try {
                TimeUnit.MILLISECONDS.sleep(1);//等待xx毫秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
