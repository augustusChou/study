package com.myzghome.core.bean.factory;

import com.myzghome.core.annotation.AnnotationUtil;
import com.myzghome.core.annotation.Loading;
import com.myzghome.core.annotation.Register;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.exception.FieldClassAnnotationNoSuchException;
import com.myzghome.core.exception.TargetRegisterClassNoSuchException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    //存放所有bean的线程安全的map
    private ConcurrentHashMap<String, BeanContainer> beans = new ConcurrentHashMap<String, BeanContainer>();

    private ConcurrentSkipListSet<String> registerBeanClassList = new ConcurrentSkipListSet<>();

    public Object getBean(String beanName) throws Exception {
        if (beanName != null && beanName.length() > 0 && beans.containsKey(beanName)) {
            BeanContainer beanContainer = beans.get(beanName);
            Object bean = beanContainer.getBean();
            if (bean == null) {
                bean = beanContainer.getBeanClass().newInstance();
                beanContainer.setBean(bean);
                setProperty(beanContainer);
            }
            return bean;
        }
        return null;
    }

    //设置字段
    private void setProperty(BeanContainer beanContainer) throws Exception {
        //获取所有字段(私有公有)
        Field[] fields = beanContainer.getBeanClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation annotation = AnnotationUtil.getTargetAnnotation(field.getAnnotations(), Loading.class);
            if (annotation != null) {
                //如果字段使用了@Loading注解，但是字段的类未使用@Register注解 就抛出异常
                Annotation fieldClassAnnotation = AnnotationUtil.getTargetAnnotation(
                        field.getType().getAnnotations(), Register.class);
                if (fieldClassAnnotation != null) {
                    Loading loading = (Loading) annotation;

                    Object value;
                    String beanName;
                    if (loading.name().length() > 0) {
                        //优先使用注解的name属性值去获取对象
                        beanName = loading.name();
                        value = getBean(beanName);
                    } else {
                        beanName = getSimpleClassName(field.getType());
                        value = getBean(beanName);
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
    }

    public String getSimpleClassName(Class classes) {
        String tmp = classes.getName().substring(classes.getName().lastIndexOf(".") + 1);
        String firstChar = tmp.substring(0, 1);
        return firstChar.toLowerCase() + tmp.substring(1, tmp.length());
    }

    //注册bean
    public void registerBean(String beanName, BeanContainer beanContainer) throws Exception {
        if (beanName != null && beanContainer != null && beanContainer.getBeanClass() != null) {
            beans.put(beanName, beanContainer);
            registerBeanClassList.add(beanContainer.getBeanClass().getName());
        }
    }


    public ConcurrentHashMap<String, BeanContainer> getBeans() {
        return beans;
    }

    public boolean assertExistBean(Class classes) {
        if (registerBeanClassList.contains(classes.getName())) {
            return true;
        }
        return false;
    }
}
