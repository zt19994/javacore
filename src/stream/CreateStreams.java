package stream;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 创建流
 *
 * @author zt1994 2019/5/9 21:33
 */
public class CreateStreams {

    /**
     * 展示流
     *
     * @param title
     * @param stream
     * @param <T>
     */
    public static <T> void show(String title, Stream<T> stream) {
        final int SIZE = 10;
        List<T> firstElements = stream.limit(SIZE + 1).collect(Collectors.toList());
        System.out.print(title + ": ");
        for (int i = 0; i < firstElements.size(); i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            if (i < SIZE) {
                System.out.print(firstElements.get(i));
            } else {
                System.out.print("...");
            }
        }
        System.out.println();
    }


    /**
     * 主方法 创建流的几种方式
     * 1、Stream.of(T...values)
     * 2、Stream.empty()
     * 3、Stream.generate(Supplier<T> s) 生成无限流
     * 4、Stream.iterate(T seed, UnaryOperator<T> f)
     * 5、Stream<String> splitAsStream(CharSequence input) 产生一个流，它的元素是输入中由该模式界定的部分
     * 6、Stream.lines(Path path) 产生一个指定文件中行的流
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        Path path = Paths.get("F:\\develop\\javacore\\src\\txtfile\\first.txt");
        String contents = new String(Files.readAllBytes(path));

        //Stream.of(T...values)
        Stream<String> words = Stream.of(contents.split("\\PL+"));
        show("words", words);

        //of方法具有可变长参数，可以构建任意数量引元的流
        Stream<String> song = Stream.of("gently", "down", "the", "stream");
        show("song", song);

        //Stream.empty() 创建不包含任何元素的流
        Stream<String> silence = Stream.empty();
        show("silence", silence);

        //Stream.generate(Supplier<T> s) 产生无限序列
        Stream<String> echos = Stream.generate(() -> "Echo");
        show("echos", echos);

        Stream<Double> randoms = Stream.generate(Math::random);
        show("randoms", randoms);

        //iterate(T seed, UnaryOperator<T> f)
        Stream<BigInteger> integers = Stream.iterate(BigInteger.ONE, n -> n.add(BigInteger.ONE));
        show("integers", integers);

        //另一种words生成方式
        Stream<String> wordsAnotherWay = Pattern.compile("\\PL+").splitAsStream(contents);
        show("wordsAnotherWay", wordsAnotherWay);

        //Stream.lines(Path path) 产生一个指定文件中行的流
        try (Stream<String> lines = Files.lines(path)) {
            show("lines", lines);
        }
    }
}
