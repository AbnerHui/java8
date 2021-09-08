package com.abner.java8.ref;

import com.abner.java8.entity.Employee;
import org.junit.Test;
import java.util.Comparator;
import java.util.function.*;

/**
 * 方法引用:若Lambda体中的内容方法已经实现了,我们可以使用方法引用
 *
 * 主要有三种语法格式:
 *      对象 : : 实例方法名
 *      类 : : 静态方法名
 *      类 : : 实例方法名
 *
 *   Lambda体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致！
 *   若Lambda参数列表中的第一个参数是 实例方法的调用者，而第二个参数是实例方法的参数时，可以使用ClassName::method
 *
 * @author Abner
 */
public class Method {

    /**
     * 对象 : : 实例方法名
     */
    @Test
    public void test1() {
        Consumer<String> consumer = (x) -> System.out.println(x);
        Consumer<String> consumer1 =  System.out::println;
    }

    @Test
    public void test2(){
        final Employee emp=new Employee();
        Supplier<String> sup=()->emp.getName();
        String str=sup.get();
        System.out.println(str);

        Supplier<Integer> sup2=emp::getAge;
        Integer num=sup2.get();
        System.out.println(num);
    }

    /**
     *  类::静态方法名
     */
    @Test
    public void test3(){
        Comparator<Integer> com=(x, y)->Integer.compare(x,y);
        Comparator<Integer> com1=Integer::compare;
    }

    /**
     * 类::实例方法名
     */
    @Test
    public void test4(){
        BiPredicate<String,String> bp=(x, y)->x.equals(y);
        BiPredicate<String, String> bp2=String::equals;
    }

    /**
     * 构造器引用
     */
    @Test
    public void test5(){
        Supplier<Employee> sup=()->new Employee();

        Supplier<Employee> sup2=Employee::new;//使用无参构造器
        Employee emp=sup2.get();
        System.out.println(emp);

        Function<Integer,Employee> fun2=(x)->new Employee(x);
        Employee emp2=fun2.apply(101);
        System.out.println(emp2);

        BiFunction<String,Integer,Employee> bf=Employee::new;
    }

    /**
     * 数组引用
     */
    @Test
    public void test6(){
        Function<Integer,String[]> fun=(x)->new String[x];
        String[] strs=fun.apply(10);
        System.out.println(strs.length);

        Function<Integer,String[]> fun2=String[]::new;
        String[] str2=fun2.apply(20);
        System.out.println(str2.length);
    }
}
