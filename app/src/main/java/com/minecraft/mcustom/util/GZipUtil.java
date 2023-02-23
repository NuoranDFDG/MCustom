package com.minecraft.mcustom.util;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * GZip工具类
 */
public class GZipUtil {
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";

    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";

    /**
     * 字符串压缩为GZIP字节数组
     */
    public static byte[] compress(String str, String encoding) {
        if (str == null || str.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(str.getBytes(encoding));
            gzip.close();
        } catch (IOException e) {
            Log.e("gzip compress error.", e.getMessage());
        }
        return out.toByteArray();
    }

    /**
     * Gzip  byte[] 解压成字符串
     */
    public static String uncompressToString(byte[] bytes) {
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);
    }


    /**
     * Gzip  byte[] 解压成字符串
     */
    public static String uncompressToString(byte[] bytes, String encoding) {
        String content = null;
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream unGZip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = unGZip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            content =  out.toString(encoding);
        } catch (IOException e) {
            Log.e("gzip compress error.",e.getMessage());
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return content;
    }
    /**
     * 判断byte[]是否是Gzip格式
     */
    public static boolean isGzip(byte[] data) {
        int header = (int)((data[0]<<8) | data[1]&0xFF);
        return header == 0x1f8b;
    }

}
