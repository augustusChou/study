package com.myzghome.core.annotation.explain;

import com.myzghome.core.annotation.Loading;
import com.myzghome.core.annotation.Register;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.core.exception.AnnotationExplainUnMatchedException;
import com.myzghome.core.exception.FieldClassAnnotationNoSuchException;
import com.myzghome.core.exception.TargetRegisterClassNoSuchException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 作者：周广
 * 创建时间：2017/1/21 0021
 * 必要描述:
 */
public class LoadingExplain implements FieldAnnotationExplain {

    @Override
    public Annotation getExplainClass() {
        return () -> Loading.class;
    }

    @Override
    public void handler(BeanContainer beanContainer, Field field, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {
        String name = "";
        if (annotation instanceof Loading) {
            name = ((Loading) annotation).name();
        } else {
            throw new AnnotationExplainUnMatchedException("注解解释器不匹配");
        }

        //如果字段使用了@Loading注解，但是字段的类未使用@Register注解 就抛出异常
        Annotation fieldClassAnnotation = field.getType().getAnnotation(Register.class);
        if (fieldClassAnnotation != null) {
            Object value;
            String beanName;
            if (name.length() > 0) {
                //优先使用注解的name属性值去获取对象
                beanName = name;
                value = beanFactory.getBean(beanName);
            } else {
                beanName = beanFactory.getSimpleClassName(field.getType());
                value = beanFactory.getBean(beanName);
            }
            if (value != null) {
                field.setAccessible(true);
                field.set(beanContainer.getBean(), value);
            } else {
                throw new TargetRegisterClassNoSuchException("上下文找不到已经注册的：" + beanName);
            }
        } else {
            throw new FieldClassAnnotationNoSuchException(field.getName() + "未注册 也未声明 @Register");
        }
    }
}
