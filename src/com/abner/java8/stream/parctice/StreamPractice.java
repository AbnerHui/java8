package com.abner.java8.stream.parctice;

import com.abner.java8.stream.parctice.entity.Trader;
import com.abner.java8.stream.parctice.entity.Transaction;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Abner
 */
public class StreamPractice {

    /**
     * 给定一个数字列表,如何返回一个由每个数的平方构成的列表呢
     * 例如: 1,2,3,4,5   1,4,9,16,25
     */
    @Test
    public void test() {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        list.stream().map((x) -> x*x).forEach(System.out::println);
    }

    List<Transaction> transaction=null;

    @Before
    public void before(){
        Trader raoul=new Trader("Raoul","Cambridge");
        Trader mario=new Trader("Mario","Milan");
        Trader alan=new Trader("Alan","Cambridge");
        Trader brian=new Trader("Brian","Cambridge");

        transaction=Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    /**
     * 1.找出2011年发生的所有交易，并按交易额排序(从低到高)
     */
    @Test
    public void test1(){
        transaction.stream()
                .filter((f) -> f.getYear() == 2011)
                .sorted((t1,t2) -> Integer.compare(t1.getValue(),t2.getValue()))
                .forEach(System.out::println);

    }

    /**
     * 2.交易员都在哪些不同的城市工作过？
     */
    @Test
    public void test2(){
        transaction.stream()
                .map((t) -> t.getTrader().getCity())
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 3.查找所有来自剑桥的交易员，并按姓名排序
     */
    @Test
    public void test3(){
        transaction.stream()
                .filter((t) -> t.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .sorted((t1,t2) -> t1.getName().compareTo(t2.getName()))
                .distinct()
                .forEach(System.out::println);
    }

    /**
     * 4.返回所有交易员的姓名字符串连成一个字符串，按字母顺序排序
     */
    @Test
    public void test4(){
        String reduce = transaction.stream()
                .map((t) -> t.getTrader().getName())
                .sorted()
                .reduce("", String::concat);
        System.out.println(reduce);
    }


    /**
     * 5.有没有交易员是在米兰工作的？
     */
    @Test
    public void test5(){
        boolean milan = transaction.stream()
                .anyMatch((t) -> t.getTrader().getCity().equals("Milan"));
        System.out.println(milan);
    }

    /**
     * 6.打印生活在剑桥的交易员的所有交易额
     */
    @Test
    public void test6(){
        Optional<Integer> cambridge = transaction.stream()
                .filter((e) -> e.getTrader().getCity().equals("Cambridge"))
                .map((t) -> t.getValue())
                .reduce(Integer::sum);
        System.out.println(cambridge.get());
    }

    /**
     * 7.所有交易中，最高的交易额是多少
     */
    @Test
    public void test7(){
        Optional<Integer> max = transaction.stream()
                .map(Transaction::getValue)
                .max(Integer::compareTo);
        System.out.println(max.get());
    }

    /**
     * 8.找到交易额最小的交易
     */
    @Test
    public void test8(){
        Optional<Transaction> op=transaction.stream()
                .min((t1,t2)->Integer.compare(t1.getValue(), t2.getValue()));
        System.out.println(op.get());
    }


}
