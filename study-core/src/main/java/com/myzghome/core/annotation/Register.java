package com.myzghome.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：周广
 * 创建时间：2017/1/9 0009
 * 必要描述: 被这个注解的类将加载到上下文对象工厂
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Register {

    String name() default "";

}
