package com.minecraft.mcustom.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class lisUtil {
    public static List<String> useDo = new ArrayList<String>();
    public static String getDate(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //Set time format.
        return df.format(new Date()).toString();
    }
}
