package com.myzghome.core.bean.factory;

import com.myzghome.core.annotation.explain.*;
import com.myzghome.core.annotation.explain.impl.LoadingExplain;
import com.myzghome.core.annotation.explain.impl.RegisterExplain;
import com.myzghome.core.bean.BeanContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public abstract class AbstractBeanFactory implements BeanFactory {

    //存放所有bean的线程安全的map
    protected ConcurrentHashMap<String, BeanContainer> beans = new ConcurrentHashMap<String, BeanContainer>();
    //加载注解解释器列表
    protected ConcurrentHashMap<Class, AnnotationExplain> annotationExplainContainer = new ConcurrentHashMap<>();
    //已经注册的bean列表
    private ConcurrentSkipListSet<String> registerBeanClassList = new ConcurrentSkipListSet<>();

    public AbstractBeanFactory() {
        loadDefaultAnnotationExplain();
    }

    //初始化
    public void initialize() throws Exception {
        for (Map.Entry<String, BeanContainer> bean : beans.entrySet()) {
            getBean(bean.getKey());
        }
    }

    public Object getBean(String beanName) throws Exception {
        if (beanName != null && beanName.length() > 0 && beans.containsKey(beanName)) {
            BeanContainer beanContainer = beans.get(beanName);
            Object bean = beanContainer.getBean();
            if (bean == null) {
                bean = beanContainer.getBeanClass().newInstance();
                beanContainer.setBean(bean);
                setProperty(beanContainer);
                setMethod(beanContainer);
            }
            return bean;
        }
        return null;
    }

    //根据class创建相应的bean
    public void setClass(Class classes) throws Exception {
        //不是抽象类和接口才进行注册
        if (!classes.isInterface() && !Modifier.isAbstract(classes.getModifiers())) {
            for (Annotation annotation : classes.getAnnotations()) {
                if (annotationExplainContainer.containsKey(annotation.annotationType())) {
                    ClassAnnotationExplain explain = (ClassAnnotationExplain) annotationExplainContainer.get(annotation.annotationType());
                    explain.handler(classes, annotation, this, null);
                }
            }
        }

    }

    //设置方法
    protected abstract void setMethod(BeanContainer beanContainer) throws Exception;

    //设置字段
    private void setProperty(BeanContainer beanContainer) throws Exception {
        //获取所有字段(私有公有)
        Field[] fields = beanContainer.getBeanClass().getDeclaredFields();
        for (Field field : fields) {
            for (Annotation annotation : field.getAnnotations()) {
                if (annotationExplainContainer.containsKey(annotation.annotationType())) {
                    FieldAnnotationExplain explain = (FieldAnnotationExplain) annotationExplainContainer.get(annotation.annotationType());
                    explain.handler(beanContainer, field, annotation, this, null);
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


    public int getBeanCount() {
        return beans.size();
    }

    public boolean assertExistBean(Class classes) {
        if (registerBeanClassList.contains(classes.getName())) {
            return true;
        }
        return false;
    }

    public void registerAnnotationExplain(Class classes) throws Exception {
        //如果类是AnnotationExplain接口的实现，且不是一个接口和抽象类就放入解释器容器
        if (AnnotationExplain.class.isAssignableFrom(classes) &&
                !classes.isInterface() && !Modifier.isAbstract(classes.getModifiers())) {
            AnnotationExplain explain = (AnnotationExplain) classes.newInstance();
            //允许后一个覆盖前一个
//                if (annotationExplainContainer.contains(explain.getExplainClass())){
//                    throw new AnnotationExplainAlreadyExistedException(
//                            explain.getExplainClass().annotationType().getName()+"注解的解释器已经存在");
//                }
            annotationExplainContainer.put(explain.getExplainClass().annotationType(), explain);
        }
    }

    //读取框架自身的注解解释器
    private void loadDefaultAnnotationExplain() {
        try {
            registerAnnotationExplain(RegisterExplain.class);
            registerAnnotationExplain(LoadingExplain.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ConcurrentHashMap<Class, AnnotationExplain> getAnnotationExplainContainer() {
        return annotationExplainContainer;
    }
}
