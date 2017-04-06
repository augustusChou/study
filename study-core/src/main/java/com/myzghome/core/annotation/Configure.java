package com.myzghome.core.annotation;

import com.myzghome.core.annotation.Register;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：周广
 * 创建时间：2017/1/9 0009
 * 必要描述:
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Register
public @interface Configure {

    String name() default "";

}
