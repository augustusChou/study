package com.myzghome.core.annotation.explain;

import com.myzghome.core.annotation.Register;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.core.exception.AnnotationExplainUnMatchedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;

/**
 * 作者：周广
 * 创建时间：2017/1/20 0020
 * 必要描述:
 */
public class RegisterExplain implements ClassAnnotationExplain {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Annotation getExplainClass() {
        return () -> Register.class;
    }

    @Override
    public void handler(Class classes, Annotation annotation, AbstractBeanFactory beanFactory, Object[] params) throws Exception {
        String name = "";
        if (annotation instanceof Register) {
            name = ((Register) annotation).name();
        } else {
            throw new AnnotationExplainUnMatchedException("注解解释器不匹配");
        }
        BeanContainer beanContainer = new BeanContainer(classes);

        if (name.length() > 0) {
            //如果注册bean的时候指定了名称，就使用指定的名称
            beanContainer.setBeanName(name);
        } else {
            beanContainer.setBeanName(beanFactory.getSimpleClassName(classes));
        }

        beanFactory.registerBean(beanContainer.getBeanName(), beanContainer);
        log.debug("register: " + beanContainer.getBeanName());
    }
}
