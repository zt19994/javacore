package io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 超链接匹配
 *
 * @author zt1994 2019/5/22 22:05
 */
public class HrefMatch {

    /**
     * 主函数 匹配超链接
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            // 获取URL string
            String urlString;
            if (args.length > 0) {
                urlString = args[0];
            } else {
                urlString = "http://www1.baidu.com";
            }

            // 打开准备的链接
            InputStreamReader in = new InputStreamReader(new URL(urlString).openStream());

            // 读取内容
            StringBuilder stringBuilder = new StringBuilder();
            int ch;
            while ((ch = in.read()) != -1) {
                stringBuilder.append((char) ch);
            }

            String patternString = "<a\\s+href\\s*=\\s*(\"[^\"]*\"|[^\\s>]*)\\s*>";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(stringBuilder);

            while (matcher.find()) {
                String match = matcher.group();
                System.out.println(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
