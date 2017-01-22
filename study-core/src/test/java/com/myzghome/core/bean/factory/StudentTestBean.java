package com.myzghome.core.bean.factory;

import com.myzghome.core.annotation.Loading;
import com.myzghome.core.annotation.Register;
import lombok.Data;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
@Data
@Register
public class StudentTestBean {

    private String name;
    private int age;
    @Loading
    private ClassesTestBean classes;


    public void hello(){
        System.out.println("Hello World");
    }

}
