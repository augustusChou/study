package com.myzghome.core.bean;

import lombok.Data;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述: 对象的包装类
 */
@Data
public class BeanContainer {

    private Class beanClass;
    private String beanName;
    private Object bean;


    public BeanContainer() {
    }


    public BeanContainer(Class beanClass) {
        this.beanClass = beanClass;
    }

}
