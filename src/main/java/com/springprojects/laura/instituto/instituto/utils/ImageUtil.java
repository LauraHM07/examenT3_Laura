package com.springprojects.laura.instituto.instituto.utils;

import java.util.Base64;

public class ImageUtil {
    public static String getImgData(byte[] byteData) {
        if(byteData != null) {
            return Base64.getMimeEncoder().encodeToString(byteData);
        } else {
            return null;
        }
    }
}
