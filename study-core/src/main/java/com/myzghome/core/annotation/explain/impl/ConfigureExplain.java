package com.myzghome.core.annotation.explain.impl;

import com.myzghome.core.annotation.Configure;
import com.myzghome.core.annotation.Init;
import com.myzghome.core.annotation.explain.AnnotationExplain;
import com.myzghome.core.annotation.explain.ClassAnnotationExplain;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作者：周广
 * 创建时间：2017/1/20 0020
 * 必要描述:
 */
public class ConfigureExplain implements ClassAnnotationExplain {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Annotation getExplainClass() {
        return () -> Configure.class;
    }

    @Override
    public void handler(Class classes, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params, ConcurrentHashMap<Class, AnnotationExplain> annotationExplainContainer) throws Exception {
        Method[] methods = classes.getMethods();
        for (Method method : methods) {
            for (Annotation methodAnnotation : method.getAnnotations()) {
                if (methodAnnotation.annotationType() == Init.class) {
                    String name = ((Init) methodAnnotation).name();
                    BeanContainer beanContainer = new BeanContainer(method.getReturnType());
                    if (name.length() > 0) {
                        //如果注册bean的时候指定了名称，就使用指定的名称
                        beanContainer.setBeanName(name);
                    } else {
                        beanContainer.setBeanName(beanFactory.getSimpleClassName(method.getReturnType()));
                    }
                    beanFactory.registerBean(beanContainer.getBeanName(), beanContainer);
                    log.debug("register: " + beanContainer.getBeanName());

                }
            }
        }
    }
}
