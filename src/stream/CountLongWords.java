package stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * 计算长单词数量
 *
 * @author zt1994 2019/5/8 22:21
 */
public class CountLongWords {

    /**
     * 从迭代到流的操作
     * <p>
     * 流遵循 -> 做什么而非怎么做
     * <p>
     * 流的特点：
     * 1、流并不存储其元素
     * 2、流的操作不会修改其数据源
     * 3、流的操作是尽可能惰性执行的
     * <p>
     * 操作流的典型流程，三个阶段的操作管道：
     * 1、创建一个流
     * 2、指定将流转换为其他流的中间操作，可能包含多个步骤
     * 3、应用终止操作
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //注意文件路径是绝对路径
        String contents = new String(Files.readAllBytes(Paths.get("F:\\develop\\javacore\\src\\txtfile\\first.txt")));

        // \\PL+是匹配非字符
        List<String> words = Arrays.asList(contents.split("\\PL+"));

        //迭代操作
        long count = 0;
        for (String word : words) {
            if (word.length() > 6) {
                count++;
            }
        }
        System.out.println(count);

        //stream 产生顺序流，filter 过滤操作，count终止操作
        count = words.stream().filter(word -> word.length() > 6).count();
        System.out.println(count);

        //parallelStream 产生并行流
        count = words.parallelStream().filter(word -> word.length() > 6).count();
        System.out.println(count);
    }
}
