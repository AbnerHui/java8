package com.abner.java8.interfaces;

public interface MyFun {

    default String getName(){
        return "libo";
    }

    default Integer getAge(){
        return 22;
    }
}
