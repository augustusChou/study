package com.myzghome.core.annotation.controller;

import com.myzghome.core.annotation.Register;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者：周广
 * 创建时间：2017/1/20 0020
 * 必要描述:
 */
@Target(ElementType.TYPE)
@Register
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonApiController {

    String name() default "";

    String mapperPath() default "";

}