package com.cystrix.blog.util;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description 关于字符串操作的处理写在着
 */
public class StringUtils {

    public static int countWords(String origin) {
        if (origin == null || origin.trim().equals("")) {
            return 0;
        }
        String[] res = origin.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9]", " ")
                .replaceAll("[\\u4e00-\\u9fa5]", "越 ").trim().split("\\s+");
        return res.length;
    }


    public static void main(String[] args) {
    }
}
