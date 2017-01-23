package com.myzghome.vertx.mvc.annotation.explain;

import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.vertx.mvc.annotation.controller.Api;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/23 0023
 * 必要描述:
 */
@Api
public class AbstractMethodAnnotationExplainTest {

    @Test
    public void getMapperPath() throws Exception {
        TestAbstractMethodAnnotationExplain testAbstractMethodAnnotationExplain = new TestAbstractMethodAnnotationExplain();
        String test = testAbstractMethodAnnotationExplain.getMapperPath(AbstractMethodAnnotationExplainTest.class, "a");
        Assert.assertTrue(test.startsWith("/"));
        System.out.println(test);

    }

    public static class TestAbstractMethodAnnotationExplain extends AbstractMethodAnnotationExplain {

        @Override
        public Annotation getExplainClass() {
            return null;
        }

        @Override
        public void handler(BeanContainer beanContainer, Method method, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {

        }
    }

}