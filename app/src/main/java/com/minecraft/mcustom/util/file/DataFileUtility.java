package com.minecraft.mcustom.util.file;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class DataFileUtility {
    public void saveFileToData(String name, String text, Context context) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public String DataFileToData(String name, Context context) {
        FileInputStream fileInputStream = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        StringBuilder result = null;
        try {
            fileInputStream = context.openFileInput(name);
            reader = new InputStreamReader(fileInputStream);
            bufferedReader = new BufferedReader(reader);
            result = new StringBuilder();
            String temp;
            while ((temp = bufferedReader.readLine()) != null) {
                result.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result.toString();
    }

}
