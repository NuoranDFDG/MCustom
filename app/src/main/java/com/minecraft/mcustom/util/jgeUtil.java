package com.minecraft.mcustom.util;

import java.util.regex.Pattern;

public class jgeUtil {

    private static final String EMAIL_REGEX = "^[a-zA-Z\\d_!#$%&'*+/=?`{|}~^\\\\.-]*+@[a-zA-Z\\d-]+(?:\\.[a-zA-Z\\d-]+)";
    private static final String NAME_REGEX = "^\\w+$";

    public static boolean checkString(String str) {
        if (Pattern.compile(EMAIL_REGEX).matcher(str).matches()) {
            return true;
        } else {
            return str.matches(NAME_REGEX);
        }

    }

    public static boolean checkUserString(String str) {
        return str.matches(NAME_REGEX);
    }

    public static boolean checkMailboxString(String str) {
        return Pattern.compile(EMAIL_REGEX).matcher(str).matches();
    }

}