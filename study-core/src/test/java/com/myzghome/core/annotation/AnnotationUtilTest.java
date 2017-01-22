package com.myzghome.core.annotation;

import com.myzghome.core.bean.factory.StudentTestBean;
import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.Annotation;

/**
 * 作者：周广
 * 创建时间：2017/1/9 0009
 * 必要描述:
 */
public class AnnotationUtilTest {

    @Test
    public void depthScanAnnotation() throws Exception {

    }

    @Test
    public void getTargetAnnotation() throws Exception {
        Annotation annotation = AnnotationUtil.getTargetAnnotation(
                StudentTestBean.class.getAnnotations(), Register.class);
        Assert.assertTrue(annotation != null);
    }

    @Test
    public void assertExistTargetAnnotation() throws Exception {
        boolean result = AnnotationUtil.assertExistTargetAnnotation(
                StudentTestBean.class.getAnnotations(), Register.class);
        Assert.assertTrue(result);
    }


}
