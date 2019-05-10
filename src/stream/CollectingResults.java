package stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 收集结果
 *
 * @author zt1994 2019/5/10 22:47
 */
public class CollectingResults {

    /**
     * 元音替换为空字符
     *
     * @return
     * @throws IOException
     */
    public static Stream<String> noVowels() throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("F:\\develop\\javacore\\src\\txtfile\\first.txt")));
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));
        Stream<String> words = wordList.stream();
        return words.map(s -> s.replaceAll("[aeiouAEIOU]", ""));
    }


    /**
     * 展示方法
     *
     * @param label
     * @param set
     * @param <T>
     */
    public static <T> void show(String label, Set<T> set) {
        System.out.print(label + ": " + set.getClass().getName());
        //joining方法在元素之间增加分隔符
        System.out.println("[" + set.stream().limit(10).map(Object::toString).collect(Collectors.joining(",")) + "]");
    }


    /**
     * 主方法
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Iterator<Integer> iter = Stream.iterate(0, n -> n + 1).limit(10).iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }

        //stream.toArray()会返回一个 Object[] 数组
        Object[] numbers = Stream.iterate(0, n -> n + 1).limit(10).toArray();
        System.out.println("Object array: " + numbers);

        try {
            Integer number = (Integer) numbers[0];
            System.out.println("number: " + number);
            System.out.println("The following statement throws an exception:");
            // 抛出异常
            Integer[] numbers2 = (Integer[]) numbers;
        } catch (ClassCastException e) {
            System.out.println(e);
        }

        //传递数组构造器 Integer[]::new 可以将 Object[] 数组转化为其他类型 Integer[] array
        Integer[] numbers3 = Stream.iterate(0, n -> n + 1).limit(10).toArray(Integer[]::new);
        System.out.println("Integer array: " + numbers3);

        //将流中的元素收集到另一个目标中使用 collect，它将会接受一个Collector 接口的实例。
        //Collector 类提供了大量用于生成公共收集器的工厂方法
        Set<String> noVowelSet = noVowels().collect(Collectors.toSet());
        show("noVowelSet", noVowelSet);

        //想控制获得的集的种类，可以添加指定构造器，例如：TreeSet::new
        TreeSet<String> noVowelTreeSet = noVowels().collect(Collectors.toCollection(TreeSet::new));
        show("noVowelTreeSet", noVowelTreeSet);

        String result = noVowels().limit(10).collect(Collectors.joining());
        System.out.println("Joining: " + result);
        result = noVowels().limit(10).collect(Collectors.joining(", "));
        System.out.println("Joining with commas: " + result);

        //将流结果简约为总和、平均值、最大值或最小值，可以使用summarizing(Int|Long|Double)
        IntSummaryStatistics summary = noVowels().collect(Collectors.summarizingInt(String::length));
        double averageWordLength = summary.getAverage();
        double maxWordLength = summary.getMax();
        System.out.println("Average word length: " + averageWordLength);
        System.out.println("Max word length: " + maxWordLength);
        System.out.println("forEach:");
        //forEach 将某个函数应用于每个元素，forEachOrdered 按顺序处理，forEach 以任意顺序处理
        noVowels().limit(10).forEach(System.out::println);
    }
}
