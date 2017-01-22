package com.myzghome.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：周广
 * 创建时间：2017/1/9 0009
 * 必要描述: 被这个注解标记的字段将被注解上下文注入
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Loading {

    String name() default "";

}
