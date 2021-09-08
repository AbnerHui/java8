package com.abner.java8.interfaces.impl;

import com.abner.java8.entity.Employee;
import com.abner.java8.interfaces.MyPredicate;

public class FilterEmployeeByAge implements MyPredicate<Employee> {

    @Override
    public boolean test(Employee emp) {
        return emp.getAge() > 35;
    }
}
