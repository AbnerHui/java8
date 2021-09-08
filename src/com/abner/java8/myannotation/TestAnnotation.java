package com.abner.java8.myannotation;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Java 8 新增注解：新增ElementType.TYPE_USE 和ElementType.TYPE_PARAMETER（在Target上）
 */
public class TestAnnotation {


    @Test
    public void test01() throws NoSuchMethodException {
        Class<TestAnnotation> clazz = TestAnnotation.class;
        Method show = clazz.getMethod("show");
        MyAnnotation[] mas = show.getAnnotationsByType(MyAnnotation.class);
        for (MyAnnotation ma : mas) {
            System.out.println(ma.value());
        }
    }

    /**
     * 使用重复注解
     */
    @MyAnnotation("Hello")
    @MyAnnotation("World")
    public void show() {}
}
