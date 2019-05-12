package stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * 基本类型流
 *
 * @author zt1994 2019/5/12 18:44
 */
public class PrimitiveTypeStreams {

    public static void show(String title, IntStream stream) {
        final int SIZE = 10;
        int[] firstElements = stream.limit(SIZE).toArray();
        System.out.print(title + ": ");
        for (int i = 0; i < firstElements.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            if (i < SIZE) {
                System.out.print(firstElements[i]);
            } else {
                System.out.print("...");
            }
        }
        System.out.println();
    }


    /**
     * 主函数
     *
     * 专门用来存储基本类型的类 IntStream、LongStream、DoubleStream
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        IntStream is1 = IntStream.generate(() -> (int) (Math.random() * 100));
        show("is1", is1);
        IntStream is2 = IntStream.range(5, 10);
        show("is2", is2);
        IntStream is3 = IntStream.rangeClosed(5, 10);
        show("is3", is3);

        Path path = Paths.get("F:\\develop\\javacore\\src\\txtfile\\first.txt");
        String contents = new String(Files.readAllBytes(path));

        Stream<String> words = Stream.of(contents.split("\\PL+"));
        IntStream is4 = words.mapToInt(String::length);
        show("is4", is4);
        String sentence = "\uD835\uDD46 is the set of octonions.";
        System.out.println(sentence);
        IntStream codes = sentence.codePoints();
        System.out.println(codes.mapToObj(c -> String.format("%X ", c)).collect(Collectors.joining()));
        Stream<Integer> integers = IntStream.range(0, 100).boxed();
        IntStream is5 = integers.mapToInt(Integer::intValue);
        show("is5", is5);
    }
}
