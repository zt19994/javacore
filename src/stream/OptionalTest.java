package stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Optional<T> 对象是一种包装器对象，被当做一种更安全的方式，用来替代类型T的引用，这种引用要么引用某个对象，要么为null。
 *
 * @author zt1994 2019/5/9 22:08
 */
public class OptionalTest {

    public static void main(String[] args) throws IOException {
        String contents = new String(Files.readAllBytes(Paths.get("F:\\develop\\javacore\\src\\txtfile\\first.txt")));
        List<String> wordList = Arrays.asList(contents.split("\\PL+"));

        //orElse() 在没有任何匹配时，可以设置默认值
        Optional<String> optionalValue = wordList.stream().filter(s -> s.contains("end")).findFirst();
        System.out.println(optionalValue.orElse("No word") + " contains end");

        //Optional.empty() 产生一个空Optional
        Optional<String> optionalString = Optional.empty();
        String result = optionalString.orElse("N/A");
        System.out.println("result: " + result);

        result = optionalString.orElseGet(() -> Locale.getDefault().getDisplayName());
        System.out.println("result: " + result);

        try {
            result = optionalString.orElseThrow(IllegalStateException::new);
            System.out.println("result: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //ifPresent(Consumer<? super T> consumer) 如果该Optional不为空，那么就将它的值传递给consumer
        optionalValue = wordList.stream().filter(s -> s.contains("end")).findFirst();
        optionalValue.ifPresent(s -> System.out.println(s + " contains end"));

        //map(Function<? super T,? extends U> mapper) 产生将该Optional的值传递给mapper之后的结果，只要这个Optional不为空且
        //结果不为null，否则产生一个空Optional
        Set<String> results = new HashSet<>();
        optionalValue.ifPresent(results::add);
        Optional<Boolean> added = optionalValue.map(results::add);
        System.out.println(added);

        //s.f().flatMap(T::g) 如果s.f()的值存在，那么g就可以应用到它上面。否则返回一个空Optional<U>
        System.out.println(inverse(4.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(-1.0).flatMap(OptionalTest::squareRoot));
        System.out.println(inverse(0.0).flatMap(OptionalTest::squareRoot));
        Optional<Double> result2 = Optional.of(-4.0).flatMap(OptionalTest::inverse).flatMap(OptionalTest::squareRoot);
        System.out.println(result2);
    }

    /**
     * 倒数
     *
     * @param x
     * @return
     */
    public static Optional<Double> inverse(Double x) {
        return x == 0 ? Optional.empty() : Optional.of(1 / x);
    }

    /**
     * 平方根
     *
     * @param x
     * @return
     */
    public static Optional<Double> squareRoot(Double x) {
        return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
    }
}
