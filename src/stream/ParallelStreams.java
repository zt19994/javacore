package stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 并行流
 *
 * @author zt1994 2019/5/12 20:50
 */
public class ParallelStreams {

    /**
     * 主方法
     * parallelStream() 生成并行流
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("F:\\develop\\javacore\\src\\txtfile\\first.txt")));
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));

        int[] shortWords = new int[10];
        wordList.parallelStream().forEach(s -> {
            if (s.length() < 8) {
                shortWords[s.length()]++;
            }
        });
        System.out.println(Arrays.toString(shortWords));

        Arrays.fill(shortWords, 0);
        wordList.parallelStream().forEach(s -> {
            if (s.length() < 8) {
                shortWords[s.length()]++;
            }
        });
        System.out.println(Arrays.toString(shortWords));

        Map<Integer, Long> shortWordCounts = wordList.parallelStream()
                .filter(s -> s.length() < 8)
                .collect(Collectors.groupingBy(String::length, Collectors.counting()));
        System.out.println(shortWordCounts);

        Map<Integer, List<String>> result = wordList.parallelStream()
                .collect(Collectors.groupingByConcurrent(String::length));
        System.out.println(result.get(7));

        result = wordList.parallelStream()
                .collect(Collectors.groupingByConcurrent(String::length));
        System.out.println(result.get(7));

        Map<Integer, Long> wordCounts = wordList.parallelStream()
                .collect(Collectors.groupingByConcurrent(String::length, Collectors.counting()));
        System.out.println(wordCounts);
    }
}
