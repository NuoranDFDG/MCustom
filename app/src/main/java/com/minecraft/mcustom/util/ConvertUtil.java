package com.minecraft.mcustom.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class ConvertUtil {
    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return (int) (dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
