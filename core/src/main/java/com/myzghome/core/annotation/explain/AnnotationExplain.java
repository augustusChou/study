package com.myzghome.core.annotation.explain;

import java.lang.annotation.Annotation;

/**
 * 作者：周广
 * 创建时间：2017/1/20 0020
 * 必要描述: 所有自定义的注解的解释器都应实现这个接口，当上下文扫描到对应注解的时候会查找相应的解释器进行处理
 */
public interface AnnotationExplain {

    /**
     * @return 返回解释器所解释的注解
     */
    Annotation getExplainClass();

}
