package stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 下游收集器
 *
 * @author zt1994 2019/5/11 20:53
 */
public class DownstreamCollectors {

    /**
     * City 类
     */
    public static class City {

        /**
         * 城市名
         */
        private String name;

        /**
         * 所属州
         */
        private String state;

        /**
         * 人口
         */
        private int population;

        public City(String name, String state, int population) {
            this.name = name;
            this.state = state;
            this.population = population;
        }

        public String getName() {
            return name;
        }

        public String getState() {
            return state;
        }

        public int getPopulation() {
            return population;
        }
    }


    /**
     * 读取city信息
     *
     * @param filename
     * @return
     * @throws IOException
     */
    public static Stream<City> readCities(String filename) throws IOException {
        return Files.lines(Paths.get(filename)).map(l -> l.split(", ")).map(a -> new City(a[0], a[1], Integer.parseInt(a[2])));
    }


    /**
     * 主方法
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //groupingBy 分组方法， Locale::getCountry 是群组的分类函数
        Stream<Locale> locales = Stream.of(Locale.getAvailableLocales());
        //查找给定国家代码对应的所有地点
        Map<String, Set<Locale>> countryToLocaleSet = locales.collect(Collectors.groupingBy(Locale::getCountry, Collectors.toSet()));
        System.out.println("countryToLocaleSet: " + countryToLocaleSet);


        //Collectors.counting() 收集元素的个数
        locales = Stream.of(Locale.getAvailableLocales());
        //对每个国家有多少个Locale进行计数
        Map<String, Long> countryToLocaleCounts = locales.collect(Collectors.groupingBy(Locale::getCountry, Collectors.counting()));
        System.out.println("countryToLocaleCounts: " + countryToLocaleCounts);

        //Collectors.summingInt() 会接受一个函数引元，将该函数应用到下游元素中，并产生他们的和
        Stream<City> cities = readCities("F:\\develop\\javacore\\src\\txtfile\\cities.txt");
        Map<String, Integer> stateToCityPopulation = cities.collect(
                Collectors.groupingBy(City::getState, Collectors.summingInt(City::getPopulation)));
        System.out.println("stateToCityPopulation: " + stateToCityPopulation);

        //Collectors.maxBy()和minBy() 会接受一个比较器，并产生下游元素中的最大值和最小值
        //获取每个州中的名字最长的城市名
        cities = readCities("F:\\develop\\javacore\\src\\txtfile\\cities.txt");
        Map<String, Optional<String>> stateToLongestCityName = cities.collect(
                Collectors.groupingBy(
                        City::getState,
                        Collectors.mapping(City::getName, Collectors.maxBy(Comparator.comparing(String::length)))));
        System.out.println("stateToLongestCityName: " + stateToLongestCityName);

        //Collectors.mapping() 会产生将函数应用到下游结果上的收集器，并将函数值传递给另一个收集器
        locales = Stream.of(Locale.getAvailableLocales());
        Map<String, Set<String>> countryToLanguages = locales.collect(
                Collectors.groupingBy(
                        Locale::getDisplayCountry,
                        Collectors.mapping(Locale::getDisplayLanguage, Collectors.toSet())));
        System.out.println("countryToLanguages: " + countryToLanguages);

        //汇总统计对象 summarizingInt 从中获取总数、个数、平均值、最小值和最大值
        cities = readCities("F:\\develop\\javacore\\src\\txtfile\\cities.txt");
        Map<String, IntSummaryStatistics> stateToCityPopulationSummary = cities.collect(
                Collectors.groupingBy(
                        City::getState,
                        Collectors.summarizingInt(City::getPopulation)));
        System.out.println("stateToCityPopulationSummary: " + stateToCityPopulationSummary.get("NY"));

        cities = readCities("F:\\develop\\javacore\\src\\txtfile\\cities.txt");
        Map<String, String> stateToCityNames = cities.collect(
                Collectors.groupingBy(
                        City::getName,
                        Collectors.reducing("", City::getName, (s, t) -> s.length() == 0 ? t : s + ", " + t)));
        System.out.println("stateToCityNames: " + stateToCityNames);

        //获取每个州的所有城市并用逗号拼接起来
        cities = readCities("F:\\develop\\javacore\\src\\txtfile\\cities.txt");
        stateToCityNames = cities.collect(
                Collectors.groupingBy(
                        City::getState,
                        Collectors.mapping(City::getName, Collectors.joining(", "))));
        System.out.println("stateToCityNames: " + stateToCityNames);
    }
}
