package com.myzghome.vertx.mvc.bean;

import com.myzghome.core.annotation.explain.MethodAnnotationExplain;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.vertx.mvc.annotation.explain.DeleteAnnotationExplain;
import com.myzghome.vertx.mvc.annotation.explain.GetAnnotationExplain;
import com.myzghome.vertx.mvc.annotation.explain.PostAnnotationExplain;
import com.myzghome.vertx.mvc.annotation.explain.PutAnnotationExplain;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public class VertxBeanFactory extends AbstractBeanFactory {
    private Router subRouter;
    private Vertx vertx;
    private JsonObject config;

    public VertxBeanFactory(Vertx vertx, Router subRouter, JsonObject config) throws Exception {
        this.vertx = vertx;
        this.subRouter = subRouter;
        this.config = config;
        loadAnnotationExplain();
        setBean(vertx);
        setBean(config);
    }

    @Override
    protected void setMethod(BeanContainer beanContainer) throws Exception {
        Method[] methods = beanContainer.getBeanClass().getMethods();
        for (Method method : methods) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotationExplainContainer.containsKey(annotation.annotationType())) {
                    MethodAnnotationExplain explain = (MethodAnnotationExplain) annotationExplainContainer.get(annotation.annotationType());
                    explain.handler(beanContainer, method, annotation, this, new Object[]{subRouter});
                }
            }
        }
    }

    private void loadAnnotationExplain() {
        try {
            registerAnnotationExplain(GetAnnotationExplain.class);
            registerAnnotationExplain(PostAnnotationExplain.class);
            registerAnnotationExplain(PutAnnotationExplain.class);
            registerAnnotationExplain(DeleteAnnotationExplain.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
