package com.minecraft.mcustom.util;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SDFileUtility {
    private Context context;

    public SDFileUtility() {
    }

    public SDFileUtility(Context context) {
        super();
        this.context = context;
    }

    //往SD卡写入文件的方法
    public void savaFileToSD(String fileName, String fileContents) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            fileName = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + fileName;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(fileName);
                output.write(fileContents.getBytes());
                //将String字符串以字节流的形式写入到输出流中
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    assert output != null;
                    output.close();
                    //关闭输出流
                } catch (Exception ignored) {
                }
            }

        } else Toast.makeText(context, "SD卡不存在或者不可读写", Toast.LENGTH_SHORT).show();
    }

    //读取SD卡中文件的方法
    //定义读取文件的方法:
    public String readFromSD(String fileName) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            fileName = Environment.getExternalStorageDirectory().getCanonicalPath() + "/" + fileName;
            FileInputStream input = null;
            try {
                //打开文件输入流
                input = new FileInputStream(fileName);
                byte[] temp = new byte[1024];

                int len;
                //读取文件内容:
                while ((len = input.read(temp)) > 0) {
                    sb.append(new String(temp, 0, len));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    //关闭输入流
                    assert input != null;
                    input.close();
                } catch (Exception ignored) {
                }
            }

        }
        return sb.toString();
    }
}