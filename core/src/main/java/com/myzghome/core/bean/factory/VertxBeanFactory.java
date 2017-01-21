package com.myzghome.core.bean.factory;

import com.myzghome.core.annotation.controller.Get;
import com.myzghome.core.bean.BeanContainer;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public class VertxBeanFactory extends AbstractBeanFactory {
    private Vertx vertx;
    private Router router;

    public VertxBeanFactory(Vertx vertx) {
        this.vertx = vertx;
        router = Router.router(vertx);
    }

    @Override
    protected void setMethod(BeanContainer beanContainer) throws Exception {
        Method[] methods = beanContainer.getBeanClass().getMethods();
        for (Method method : methods) {
            Annotation get = method.getAnnotation(Get.class);

        }
    }

    private void getMethodHandler(Annotation get, BeanContainer beanContainer) {
    }

}
