package com.myzghome.core.context;

import com.myzghome.core.bean.factory.ClassesTestBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public class AnnotationApplicationContextTest {
    private AbstractApplicationContext applicationContext;

    @Before
    public void before() throws Exception {
        applicationContext = new AnnotationApplicationContext(new String[]{"com.myzghome.core"});
    }


    @Test
    public void loadAnnotationBean() throws Exception {
        applicationContext.refresh();
        Assert.assertTrue(applicationContext.getBeanCount() > 0);
        ClassesTestBean classesTestBean= (ClassesTestBean) applicationContext.getBean("classesTestBean");
        classesTestBean.test();
    }



}
