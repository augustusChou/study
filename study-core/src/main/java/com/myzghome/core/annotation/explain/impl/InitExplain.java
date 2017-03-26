package com.myzghome.core.annotation.explain.impl;

import com.myzghome.core.annotation.Init;
import com.myzghome.core.annotation.explain.MethodAnnotationExplain;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/20 0020
 * 必要描述:
 */
public class InitExplain implements MethodAnnotationExplain {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Annotation getExplainClass() {
        return () -> Init.class;
    }


    @Override
    public void handler(BeanContainer beanContainer, Method method, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {
        if (annotation instanceof Init) {
            Class<?>[] methodParams = method.getParameterTypes();
            Object[] methodParamArr = new Object[methodParams.length];
            for (int i = 0; i < methodParams.length; i++) {
                Object obj = beanFactory.getBean(methodParams[i]);
                methodParamArr[i] = obj;
            }
            Object result = method.invoke(beanContainer.getBean(), methodParamArr);
            if (result!=null){
                beanFactory.setBean(result);
            }
        }
    }
}
