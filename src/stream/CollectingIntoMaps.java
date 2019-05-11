package stream;

import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 将流的结果收集到映射表中
 *
 * @author zt1994 2019/5/11 16:13
 */
public class CollectingIntoMaps {

    /**
     * Person 类
     */
    public static class Person {
        private int id;

        private String name;

        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }


    /**
     * 创建 people 流
     */
    public static Stream<Person> people() {
        return Stream.of(new Person(1001, "Peter"), new Person(1002, "Paul"), new Person(1003, "Mary"));
    }


    /**
     * 主函数 - 收集集合的操作
     * 对于每一个 toMap 方法，都有一个等价的可以产生并发映射表的 toConcurrentMap 方法。
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //Collectors.toMap() 方法有两个函数引元，用来产生映射表的键和值
        Map<Integer, String> idToName = people().collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("idToName: " + idToName);

        //值为实际值 第二个函数使用 Function.identity()
        Map<Integer, Person> idToPerson = people().collect(Collectors.toMap(Person::getId, Function.identity()));
        System.out.println("idToPerson: " + idToPerson.getClass().getName() + idToPerson);

        //如果有多个元素具有相同的键，会产生冲突，可以通过提供第三个函数引元来覆盖这种行为，
        //该函数会针对给定的已有值和新值来解决冲突并确定键对应的值。
        //这个函数应该返回已有值、新值或者它们的组合。
        idToPerson = people().collect(
                Collectors.toMap(
                        Person::getId,
                        Function.identity(),
                        (existingValue, newValue) -> {
                            throw new IllegalStateException();
                        },
                        TreeMap::new));
        System.out.println("idToPerson: " + idToPerson.getClass().getName() + idToPerson);

        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        Map<String, String> languageNames = locales.collect(
                Collectors.toMap(
                        Locale::getDisplayLanguage,
                        l -> l.getDisplayLanguage(l),
                        (existingValue, newValue) -> existingValue));
        System.out.println("languageNames: " + languageNames);

        //键为集合的情况
        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryLanguageSets = locales.collect(
                Collectors.toMap(
                        Locale::getDisplayCountry,
                        l -> Collections.singleton(l.getDisplayLanguage()),
                        (a, b) -> {
                            Set<String> union = new HashSet<>(a);
                            union.addAll(b);
                            return union;
                        }));
        System.out.println("countryLanguageSets: " + countryLanguageSets);
    }
}
