package com.myzghome.core.context;

import com.myzghome.core.bean.factory.AbstractBeanFactory;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    //核心方法，获取所有bean并注册
    public void refresh() throws Exception {
        loadBean();
    }

    //加载bean到注册工厂，实现由子类负责
    protected abstract void loadBean() throws Exception;


    @Override
    public Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }


    public int getBeanCount() {
        return beanFactory.getBeans().size();
    }
}
