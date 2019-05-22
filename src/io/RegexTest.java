package io;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式
 *
 * @author zt1994 2019/5/22 21:46
 */
public class RegexTest {

    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入模型：");
        String patternString = scanner.nextLine();

        Pattern pattern = Pattern.compile(patternString);

        while (true) {
            System.out.println("输入要匹配的字符串：");
            String input = scanner.nextLine();
            if (input == null || input.equals("")) {
                return;
            }
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                System.out.println("Match");
                int group = matcher.groupCount();
                if (group > 0) {
                    for (int i = 0; i < input.length(); i++) {
                        //输出空组
                        for (int j = 1; j <= group; j++) {
                            if (i == matcher.start(j) && i == matcher.end(j)) {
                                System.out.print("()");
                            }
                        }
                        //开始输出不为空的匹配
                        for (int j = 1; j <= group; j++) {
                            if (i == matcher.start(j) && i != matcher.end(j)) {
                                System.out.print("(");
                            }
                        }
                        System.out.print(input.charAt(i));
                        //输出结束
                        for (int j = 1; j <= group; j++) {
                            if (i + 1 != matcher.start(j) && i + 1 == matcher.end(j)) {
                                System.out.print(")");
                            }
                        }
                        System.out.println();
                    }
                }
            }else {
                System.out.println("No Match");
            }
        }
    }
}
