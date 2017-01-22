package com.myzghome.core.annotation.explain;

import com.myzghome.core.bean.factory.AbstractBeanFactory;

import java.lang.annotation.Annotation;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public interface ClassAnnotationExplain extends AnnotationExplain {


    /**
     * 注解处理
     *
     * @param annotation    扫描到的注解
     * @param beanContainer
     */
    void handler(Class classes, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception;


}
