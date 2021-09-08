package com.abner.java8.stream;

import com.abner.java8.entity.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Abner
 */
public class StreamApi {

    /**
     * 创建stream
     */
    @Test
    public void test1() {
        //1.可以通过Collection 系列集合提供的stream()或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2.通过 Arrays 中的静态方法stream()获取数组流
        Employee[] emps=new Employee[10];
        Stream<Employee> stream2= Arrays.stream(emps);

        //3.通过Stream 类中的静态方法of()
        Stream<String> stream3=Stream.of("aa","bb","cc");

        //4.创建无限流
        Stream<Integer> stream4=Stream.iterate(0, (x) -> x+2);
        stream4.limit(10).forEach(System.out::println);

        //生成
        Stream.generate(() -> Math.random())
                .limit(5)
                .forEach(System.out::println);
    }

    /**
     * 中间操作
     */
    List<Employee> employees=Arrays.asList(
            new Employee("张三",18,9999.99),
            new Employee("李四",58,5555.55),
            new Employee("王五",26,3333.33),
            new Employee("赵六",36,6666.66),
            new Employee("田七",12,8888.88),
            new Employee("田七",12,8888.88)
    );

    /**
     *  筛选与切片
     *  filter--接收Lambda，从流中排除某些元素。
     *  limit--截断流，使其元素不超过给定数量。
     *  skip(n)--跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流。与limit(n) 互补
     *  distinct--筛选，通过流所生成元素的 hashCode() 和 equals() 去掉重复元素
     */

    //内部迭代：迭代操作由 Stream API 完成
    @Test
    public void test2(){
        Stream<Employee> stream=employees.stream()
                .filter((e) -> e.getAge()>35 );
        stream.forEach(System.out::println);
    }
    //外部迭代
    @Test
    public void test3(){
        Iterator<Employee> it=employees.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
    }

    @Test
    public void test4(){
        //发现“短路”只输出了两次，说明只要找到 2 个 符合条件的就不再继续迭代
        employees.stream()
                .filter((e)->{
                    System.out.println("短路！");
                    return e.getSalary()>5000;
                })
                .limit(2)
                .forEach(System.out::println);
    }

    @Test
    public void test5(){
        employees.stream()
                .filter((e)->e.getSalary()>5000)
                .skip(2)//跳过前两个
                .distinct()//去重，注意：需要Employee重写hashCode 和 equals 方法
                .forEach(System.out::println);
    }

    /**
     * 映射
     * map--接收Lambda，将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新元素。
     * flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     */

    @Test
    public void test6() {
        List<String> list=Arrays.asList("aaa","bbb","ccc","ddd");
        list.stream()
                .map((str) -> str.toUpperCase())
                .forEach(System.out::println);

        System.out.println("------------------------");

        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        System.out.println("------------------------");

        Stream<Stream<Character>> stream=list.stream()
                .map(StreamApi::filterChatacter);
        stream.forEach((sm)->{
            sm.forEach(System.out::println);
        });

        System.out.println("------------------------");

        Stream<Character> sm=list.stream()
                .flatMap(StreamApi::filterChatacter);
        sm.forEach(System.out::println);
    }

    public static Stream<Character> filterChatacter(String str){
        List<Character> list=new ArrayList<>();
        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /**
     * 排序
     * sorted()-自然排序（按照对象类实现Comparable接口的compareTo()方法 排序）
     * sorted(Comparator com)-定制排序（Comparator）
     */
    @Test
    public void test7(){
        List<String> list=Arrays.asList("ccc","bbb","aaa");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        System.out.println("------------------------");

        employees.stream()
                .sorted((e1,e2)-> e1.getName().compareTo(e2.getName()))
                .forEach(System.out::println);
    }

    /**
     * 终止
     */
    List<Employee> list=Arrays.asList(
            new Employee("张三",18,9999.99, Employee.Status.FREE),
            new Employee("李四",58,5555.55, Employee.Status.BUSY),
            new Employee("王五",26,3333.33, Employee.Status.VOCATION),
            new Employee("赵六",36,6666.66, Employee.Status.FREE),
            new Employee("田七",12,8888.88, Employee.Status.BUSY)
    );

    /**
     * 查找与匹配
     * allMatch-检查是否匹配所有元素
     * anyMatch-检查是否至少匹配一个元素
     * noneMatch-检查是否没有匹配所有元素
     * findFirst-返回第一个元素
     * Optional是Java8中避免空指针异常的容器类
     * findAny-返回当前流中的任意元素
     * count-返回流中元素的总个数
     * max-返回流中最大值
     * min-返回流中最小值
     */
    @Test
    public void test8() {
        boolean b = list.stream()
                        .allMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);

        boolean b1 = list.stream()
                    .anyMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = list.stream()
                .noneMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);

        Optional<Employee> b3 = list.stream()
                                    .sorted((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary()))
                                    .findFirst();
        System.out.println(b3);

        Optional<Employee> any = list.stream()
                .filter((e) -> e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        System.out.println(any);

        long count = list.stream().count();
        System.out.println(count);

        Optional<Employee> max = list.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(max.get());

        Optional<Double> min = list.stream()
                .map(Employee::getSalary)
                .min(Double::compareTo);
        System.out.println(min.get());
    }

    /**
     * 归约
     * reduce(T identity,BinaryOperator b) / reduce(BinaryOperator b)-
     * 可以将流中元素反复结合起来，得到一个值。
     */
    @Test
    public void test9() {
        List<Integer> lists=Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer reduce = lists.stream()
                             .reduce(0, (x, y) -> x + y);
        System.out.println(reduce);
        System.out.println("--------------------------");

        Optional<Double> reduce1 = list.stream()
                                        .map(Employee::getSalary)
                                        .reduce(Double::sum);
        System.out.println(reduce1.get());
    }

    /**
     * 收集
     * collect-将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中元素做汇总的方法。
     */
    @Test
    public void test10() {
        List<String> collect = list.stream()
                                    .map(Employee::getName)
                                    .collect(Collectors.toList());
        System.out.println(collect);
        System.out.println("------");

        Set<String> collect1 = list.stream()
                                    .map(Employee::getName)
                                    .collect(Collectors.toSet());
        System.out.println(collect1);

        Long count=list.stream()
                .collect(Collectors.counting());
        System.out.println("总和为:"+count);

        Double avg=list.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println("平均值:"+avg);

        Double collect2 = list.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println("工资总和:"+collect2);

        Optional<Employee> collect3 = list.stream()
                .collect(Collectors.maxBy((n1, n2) -> Double.compare(n1.getSalary(), n2.getSalary())));
        System.out.println("工资最高的员工信息:"+collect3.get());

        Optional<Double> collect4 = list.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy(Double::compareTo));
        System.out.println("最低工资为:"+collect4.get());

        Map<Employee.Status, List<Employee>> collect5 = list.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println("分组:"+collect5);

        Map<Employee.Status, Map<String, List<Employee>>> collect6 = list.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println("多级分组:"+collect6);

        Map<Boolean, List<Employee>> collect7 = list.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() > 8000));
        System.out.println("分区:"+collect7);
    }
}
