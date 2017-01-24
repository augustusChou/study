package com.myzghome.vertx.mvc.annotation.explain;

import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.vertx.mvc.annotation.controller.Put;
import io.vertx.ext.web.Router;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public class PutAnnotationExplain extends AbstractMethodAnnotationExplain {

    @Override
    public Annotation getExplainClass() {
        return () -> Put.class;
    }

    @Override
    public void handler(BeanContainer beanContainer, Method method, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {
        Router router = (Router) params[0];
        Put put = (Put) annotation;
        String path = getMapperPath(beanContainer.getBeanClass(), put.path());
        if (path != null) {
            router.put(path).handler(context -> {
                invoke(beanContainer, method, context, path);
            });
        }
    }
}
