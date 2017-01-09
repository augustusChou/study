package com.myzghome.core.bean.factory;

import com.myzghome.core.bean.BeanContainer;

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
    private ConcurrentHashMap<String, BeanContainer> beans = new ConcurrentHashMap<String, BeanContainer>();

    private ConcurrentSkipListSet<String> registerBeanClassList=new ConcurrentSkipListSet<>();

    public Object getBean(String beanName) {
        if (beanName != null && beanName.length() > 0 && beans.containsKey(beanName)) {
            return beans.get(beanName).getBean();
        }
        return null;
    }

    public Object getBean(Class classes) {
        if (assertExistBean(classes)){
            for (Map.Entry<String, BeanContainer> bean:beans.entrySet()){
                if (bean.getValue().getBeanClass()==classes){
                    return bean.getValue().getBean();
                }
            }
        }
        return null;
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

    public boolean assertExistBean(Class classes){
        if (registerBeanClassList.contains(classes.getName())){
            return true;
        }
        return false;
    }
}
