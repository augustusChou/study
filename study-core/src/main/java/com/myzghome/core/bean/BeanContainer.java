package com.myzghome.core.bean;

import lombok.Data;
import lombok.experimental.Accessors;

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

    public BeanContainer(Class beanClass, String beanName) {
        this.beanClass = beanClass;
        this.beanName = beanName;
    }

    public BeanContainer(Class beanClass, String beanName, Object bean) {
        this.beanClass = beanClass;
        this.beanName = beanName;
        this.bean = bean;
    }
}
