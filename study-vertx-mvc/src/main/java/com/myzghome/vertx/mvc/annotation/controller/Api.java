package com.myzghome.vertx.mvc.annotation.controller;

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
@Retention(RetentionPolicy.RUNTIME)
public @interface Api {

    String path() default "";

}
