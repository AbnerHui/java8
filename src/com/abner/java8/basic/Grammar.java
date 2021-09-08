package com.abner.java8.basic;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Lambda基础语法
 * @author Abner
 */
public class Grammar {

    /**
     * 无参数无返回值
     */
    @Test
    public void test1() {
        Runnable runnable = () -> System.out.println("Hello");
        runnable.run();
    }

    /**
     * 一个参数无返回值 小括号可以省略
     */
    @Test
    public void test2() {
        Consumer<String> consumer = (x) -> System.out.println(x);
        consumer.accept("abner威武霸气");
    }

    /**
     * 两个以上参数有返回值
     * lambda有多条语句(一条语句 return和{} 可以省略不写)
     */
    @Test
    public void test3() {
        Comparator<Integer> comparator = (n1,n2) ->{
            System.out.println("------");
            return Integer.compare(n1,n2);
        };
    }


}
