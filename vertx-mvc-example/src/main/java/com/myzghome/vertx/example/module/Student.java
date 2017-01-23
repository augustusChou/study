package com.myzghome.vertx.example.module;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 作者：周广
 * 创建时间：2017/1/19 0019
 * 必要描述:
 */
@Data
public class Student {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private String id;
    private String name;
    private int age;

    public Student() {

    }

}
