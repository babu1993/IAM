package com.iam.common.util;

public class StringUtil {

    public static String makeFirstCharUpperCase(String str){
    if (str == null || str.isEmpty()) {
      return str;
    }
    if (str.length() == 1) {
      return str.toUpperCase();
    }
    return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}
