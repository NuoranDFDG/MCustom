package com.minecraft.mcustom.util;

import java.util.regex.Pattern;

public class jgeUtil {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^\\\\.-]*+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)";
    private static final String NAME_REGEX = "^[a-zA-Z0-9_]+$";

    public static boolean checkString(String str) {
        if (Pattern.compile(EMAIL_REGEX).matcher(str).matches()) {
            return true;
        } else {
            return str.matches(NAME_REGEX);
        }

    }
}