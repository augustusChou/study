package com.myzghome.core.context;

import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.core.bean.factory.DefaultBeanFactory;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述: 注解扫描上下文
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {


    public AnnotationApplicationContext(String[] packagePaths) throws Exception {
        super(new DefaultBeanFactory(), packagePaths);
    }

    public AnnotationApplicationContext(String[] packagePaths, AbstractBeanFactory abstractBeanFactory) throws Exception {
        super(abstractBeanFactory, packagePaths);
        this.packagePaths = packagePaths;
    }


    @Override
    protected void loadBean() throws Exception {
        for (Class classes : classSet) {
            if (!beanFactory.assertExistBean(classes)) {
                beanFactory.setClass(classes);
            }
        }
    }




}
