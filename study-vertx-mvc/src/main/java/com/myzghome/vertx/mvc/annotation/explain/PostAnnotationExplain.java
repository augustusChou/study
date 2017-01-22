package com.myzghome.vertx.mvc.annotation.explain;

import com.myzghome.core.annotation.explain.MethodAnnotationExplain;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.vertx.mvc.annotation.controller.Post;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public class PostAnnotationExplain implements MethodAnnotationExplain {

    @Override
    public Annotation getExplainClass() {
        return () -> Post.class;
    }

    @Override
    public void handler(BeanContainer beanContainer, Method method, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {
        method.invoke(beanContainer.getBean(), "测试");
    }
}
