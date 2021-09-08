package com.abner.java8.myannotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
/**
 * 要想定义重复注解需要使用@Repeatable修饰并指明容器的类
 *  @author Abner
 */
@Repeatable(MyAnnotations.class)
public @interface MyAnnotation {

    String value() default "abner";
}
