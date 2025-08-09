package com.cystrix.blog.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.text.TextContentRenderer;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author chenyue7@foxmail.com
 * @date 21/12/2023
 * @description 关于字符串操作的处理写在着
 */
public class StringUtils {

    public static int countMarkdownWords(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        TextContentRenderer renderer = TextContentRenderer.builder().build();
        String plainText = renderer.render(document);
        return plainText.replaceAll("\\s+", "").length(); // 中文字符按字计数，英文可按空格分词
    }


    public static int countWords(String origin) {
        if (origin == null || origin.trim().equals("")) {
            return 0;
        }
        String[] res = origin.replaceAll("[^\\u4e00-\\u9fa5a-zA-Z0-9]", " ")
                .replaceAll("[\\u4e00-\\u9fa5]", "越 ").trim().split("\\s+");
        return res.length;
    }

    public static String generateDigest(String content) {
        String regex = "[\u4e00-\u9fa5\\pP\\pN\\pL&&[^#]]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            result.append(matcher.group());
        }
        return result.substring(0, 200);
    }


    public static void main(String[] args) {
    }
}
