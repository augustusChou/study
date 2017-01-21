package com.myzghome.core.bean.factory;


import com.myzghome.core.bean.BeanContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public class AbstractBeanFactoryTest {

    private AbstractBeanFactory abstractBeanFactory;

    @Before
    public void before() throws Exception {
        abstractBeanFactory = DefaultBeanFactory.class.newInstance();
    }

    @Test
    public void registerBean() throws Exception {
        abstractBeanFactory.registerBean("student", new BeanContainer(StudentTestBean.class));
        Assert.assertTrue(abstractBeanFactory.getBeanCount() > 0);
    }

    @Test
    public void assertExistBean() throws Exception {
        boolean result = abstractBeanFactory.assertExistBean(ClassesTestBean.class);
        Assert.assertFalse(result);
    }


}
