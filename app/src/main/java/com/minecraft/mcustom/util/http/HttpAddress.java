package com.minecraft.mcustom.util.http;

import java.io.Serializable;

public class HttpAddress {
    private static String[] args;
    final private static String userAddress="user";
    final private static String bookAddress="book";
    final private static String userPlaceAddress="userPlace";
    final private static String userInfo="userInfo";

    public static String user(){
        return userAddress;
    }
    public static String book(){return bookAddress;}
    public static String userPlace(){return userPlaceAddress;}
    public static String userInfo(){return userInfo;}
    /**
     *
     * @param address 首地址 例如：“user”
     * @param method 地址中的方法  例如： “insert”
     * @return
     */
    public static String[] get(String address,String method){
        switch (method){
            case "login":args=getLoginAddress();
                break;
            case "insert":args=getInsertAddress(address);
                break;
            case "update":args=getUpdateAddress(address);
                break;
            case "list":args=getListAddress(address);
                break;
        }
        return args;
    }
    /**
     *采用方法重载，分别处理两种情况，带id和不带id
     * @param address 首地址 例如：“user”
     * @param method 地址中的方法  例如： “delete”
     * @param id id则为相应参数  delete后的id参数
     * @return
     */
    public static String[] get(String address,String method,Serializable id){
        switch (method){
            case "delete":args=getDeleteAddress(address,id);
                break;
            case "line":args=getLineAddress(address,id);
                break;
        }
        return args;
    }
    private static String[] getLoginAddress(){
        args=new String[]{userAddress,"login"};
        return args;
    }
    private static String[] getInsertAddress(String address){
        args=new String[]{address,"insert"};
        return args;
    }
    private static String[] getDeleteAddress(String address, Serializable id){
        args=new String[]{address,"delete", String.valueOf(id)};
        return args;
    }
    private static String[] getUpdateAddress(String address){
        args=new String[]{address,"update"};
        return args;
    }
    private static String[] getLineAddress(String address, Serializable id){
        args=new String[]{address,"line", String.valueOf(id)};
        return args;
    }
    private static String[] getListAddress(String address){
        args=new String[]{address,"list"};
        return args;
    }
}
