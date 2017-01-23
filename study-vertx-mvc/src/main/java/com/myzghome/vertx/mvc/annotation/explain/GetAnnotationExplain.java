package com.myzghome.vertx.mvc.annotation.explain;

import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.vertx.mvc.annotation.controller.Get;
import io.vertx.ext.web.Router;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public class GetAnnotationExplain extends AbstractMethodAnnotationExplain {

    @Override
    public Annotation getExplainClass() {
        return () -> Get.class;
    }

    @Override
    public void handler(BeanContainer beanContainer, Method method, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {
        Router router = (Router) params[0];
        Get get = (Get) annotation;
        String path = getMapperPath(beanContainer.getBeanClass(), get.path());
        if (path != null) {
            router.get(path).handler(context -> {
                try {
                    method.invoke(beanContainer.getBean(), context);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
