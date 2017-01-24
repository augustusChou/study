package com.myzghome.vertx.mvc.annotation.explain;

import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.vertx.mvc.annotation.controller.Post;
import io.vertx.ext.web.Router;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public class PostAnnotationExplain extends AbstractMethodAnnotationExplain {

    @Override
    public Annotation getExplainClass() {
        return () -> Post.class;
    }

    @Override
    public void handler(BeanContainer beanContainer, Method method, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {
        Router router = (Router) params[0];
        Post post = (Post) annotation;
        String path = getMapperPath(beanContainer.getBeanClass(), post.path());
        if (path != null) {
            router.post(path).handler(context -> {
                invoke(beanContainer, method, context, path);
            });
        }
    }
}
