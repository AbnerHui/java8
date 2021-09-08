package com.abner.java8;

import com.abner.java8.entity.Employee;
import com.abner.java8.interfaces.MyPredicate;
import com.abner.java8.interfaces.impl.FilterEmployeeByAge;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author Abner
 */
public class TestLambda {

    /**
     * 原来的匿名内部类写法
     */
    @Test
    public void test1() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(1,2);
            }
        };
    }

    /**
     * Lambda表达式匿名内部类写法
     */
    @Test
    public void test2() {
        Comparator<Integer> comparator = (n1,n2) -> Integer.compare(1,2);
    }

    List<Employee> list = Arrays.asList(
      new Employee("张三",18,999.00),
      new Employee("王五",38,399.00),
      new Employee("天齐",28,459.00),
      new Employee("奥利",48,99.00)
    );

    /**
     * 获取当前公司中员工年龄大于35的年龄信息(正常写法)
     */
    @Test
    public void test3() {
        List<Employee> employees = filtersEmployees(list);
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    public List<Employee> filtersEmployees(List<Employee> list) {
        List<Employee> emp = new ArrayList<>();
        for (Employee e : list) {
            if(e.getAge() > 35) {
                emp.add(e);
            }
        }
        return emp;
    }

    /**
     * 获取当前公司中员工年龄大于35的年龄信息(优化写法一 策略设计模式)
     */
    @Test
    public void test4() {
        List<Employee> employees = filterEmployees1(list,new FilterEmployeeByAge());
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    public List<Employee> filterEmployees1(List<Employee> list, MyPredicate<Employee> mp) {
        List<Employee> emp = new ArrayList<>();
        for (Employee e : list) {
            if(mp.test(e)) {
                emp.add(e);
            }
        }
        return emp;
    }

    /**
     * 获取当前公司中员工年龄大于35的年龄信息(优化写法二 匿名内部类)
     */
    @Test
    public void test5() {
        List<Employee> employees = filterEmployees1(list, new MyPredicate<Employee>() {
            @Override
            public boolean test(Employee employee) {
                return employee.getAge() > 35;
            }
        });
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }

    /**
     * 获取当前公司中员工年龄大于35的年龄信息(优化写法三 Lambda)
     */
    @Test
    public void test6() {
        List<Employee> employees = filterEmployees1(list, employee -> employee.getAge() > 35);
        employees.forEach(System.out::println);
    }

    /**
     * 获取当前公司中员工年龄大于35的年龄信息(优化写法四 stream)
     */
    @Test
    public void test7() {
        list.stream().filter((e) -> e.getAge() > 35)
                     .forEach(System.out::println);

        //提取出来所有人的名字
        list.stream()
            .map(Employee::getName)
            .forEach(System.out::println);
    }
}
