package com.abner.java8.date;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;

/**
 * @author Abner
 */
public class TestDate {

    /**
     * 传统得日期穿在线程安全问题
     * @throws ExecutionException
     * @throws InterruptedException
     */
    @Test
    public void test1() throws ExecutionException, InterruptedException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Callable<Date> call = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return sdf.parse("20201033");
            }
        };
        //创建一个长度为10的线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);
        //Future用来接收多线程的执行结果
        List<Future<Date>> res = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            res.add(pool.submit(call));
        }
        for (Future<Date> future : res) {
            System.out.println(future.get());
        }
        pool.shutdown();
    }

    /**
     * 解决方式 通过上锁得方式保证线程安全
     */
    @Test
    public void test2() throws ExecutionException, InterruptedException {
        Callable<Date> call = new Callable<Date>() {
            @Override
            public Date call() throws Exception {
                return DateFormatThreadLocal.convent("20201031");
            }
        };
        //创建一个长度为10的线程池
        ExecutorService pool = Executors.newFixedThreadPool(10);
        //Future用来接收多线程的执行结果
        List<Future<Date>> res = new ArrayList<>();
        for (int i = 0; i < 10 ; i++) {
            res.add(pool.submit(call));
        }
        for (Future<Date> future : res) {
            System.out.println(future.get());
        }

        pool.shutdown();
    }

    //--------------------------------- java8日期-----------------------------------

    @Test
    public void test3() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间为:"+now);

        LocalDateTime of = LocalDateTime.of(2020, 10, 31, 10, 10);
        System.out.println("得到指定得时间:"+of);

        LocalDateTime localDateTime = now.plusDays(2);
        System.out.println("加两天:"+localDateTime);

        LocalDate ld1 = LocalDate.of(2016, 9, 1);
        LocalDate ld2 = LocalDate.now();
        Period period = Period.between(ld1, ld2);
        System.out.println("相差几年:"+period.getYears());

    }

    /**
     * 格式化
     */
    @Test
    public void test4() {
        DateTimeFormatter dtf1 = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime ldt1 = LocalDateTime.now();
        String str1 = ldt1.format(dtf1);
        System.out.println(str1);

        //自定义格式化 ofPattern
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt2 = LocalDateTime.now();
        String str2 = ldt2.format(dtf2);
        System.out.println(str2);

        //解析
        LocalDateTime newDate = LocalDateTime.parse(str1, dtf1);
        System.out.println(newDate);
    }

    /**
     * 时区
     */
    @Test
    public void test5(){
        //查看支持的时区
        Set<String> set = ZoneId.getAvailableZoneIds();
        set.forEach(System.out::println);

        LocalDateTime ldt1 = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        System.out.println("指定时区时间为:"+ldt1);

        LocalDateTime ldt2 = LocalDateTime.now(ZoneId.of("Europe/Tallinn"));
        ZonedDateTime zdt1 = ldt2.atZone(ZoneId.of("Europe/Tallinn"));
        System.out.println("指定时区得时间并带上时区:"+zdt1);
    }
}
