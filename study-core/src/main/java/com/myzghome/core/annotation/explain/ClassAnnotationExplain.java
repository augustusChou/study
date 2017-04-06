package com.myzghome.core.annotation.explain;

import com.myzghome.core.bean.factory.AbstractBeanFactory;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public interface ClassAnnotationExplain extends AnnotationExplain {


    /**
     * 注解处理
     */
    void handler(Class classes, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params, ConcurrentHashMap<Class, AnnotationExplain> annotationExplainContainer) throws Exception;


}
