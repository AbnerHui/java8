package com.abner.java8.basic;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Java8内置的四大核心函数式接口
 */
public class CoreInterface {

    /**
     * Consumer<T> 消费型
     */
    @Test
    public void test1() {
        happy(1000,(m) -> System.out.println("消费:"+m+"元"));
    }

    public void happy(double money, Consumer<Double> con) {
        con.accept(money);
    }

    /**
     * Supplier<T> 供给型
     */
    @Test
    public void test2() {
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        numList.stream().forEach(System.out::println);
    }

    /**
     * 产生一些整数并放入集合中
     */
    public List<Integer> getNumList(int num, Supplier<Integer> sup) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i <num ; i++) {
            list.add(sup.get());
        }
        return list;
    }

    /**
     * Function<T,R> 函数型
     */
    @Test
    public void test3() {
        String s = strHandler("\t\t\t abner威武", (str) -> str.trim());
        System.out.println(s);
    }

    /**
     * 用于处理字符串
     */
    public String strHandler(String str, Function<String,String> fun) {
        return fun.apply(str);
    }

    /**
     * Predicate<T> 断定型
     */
    @Test
    public void test4() {
        List<String> strings = filterStr(Arrays.asList("a", "bcd", "c", "dfgt"), (s) -> s.length() > 3);
        strings.stream().forEach(System.out::println);
    }

    /**
     * 将满足条件的字符串,放入集合中
     */
    public List<String> filterStr(List<String> lists, Predicate<String> pre) {
        List<String> list = new ArrayList<>();
        for (String str : lists) {
            if(pre.test(str)) {
                list.add(str);
            }
        }
        return list;
    }
}
