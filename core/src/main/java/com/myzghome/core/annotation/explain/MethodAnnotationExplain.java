package com.myzghome.core.annotation.explain;

import com.myzghome.core.bean.factory.AbstractBeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public interface MethodAnnotationExplain extends AnnotationExplain {

    /**
     * 注解处理
     *
     * @param annotation 扫描到的注解
     */
    void handler(Class classes, Method method, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception;


}
