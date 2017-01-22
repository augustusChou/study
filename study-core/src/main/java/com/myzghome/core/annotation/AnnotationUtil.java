package com.myzghome.core.annotation;

import java.lang.annotation.Annotation;

/**
 * 作者：周广
 * 创建时间：2017/1/9 0009
 * 必要描述:
 */
public class AnnotationUtil {

    //深度扫描类是否指定注解 如果类的注解的注解匹配指定注解会返回指定注解
    public static Annotation depthScanAnnotation(Class classes, Class targetAnnotationClass) {
        Annotation result = classes.getAnnotation(targetAnnotationClass);
        if (result == null) {
            for (Annotation subAnnotation : classes.getAnnotations()) {
                if (subAnnotation.annotationType().getAnnotation(targetAnnotationClass) != null) {
                    result = subAnnotation;
                }
            }
        }
        return result;
    }

    public static Annotation getTargetAnnotation(Annotation[] annotations, Class targetAnnotationClass) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == targetAnnotationClass || assertExistTargetAnnotation(annotation.getClass().getAnnotations(), targetAnnotationClass)) {
                return annotation;
            }
        }
        return null;
    }

    //判断是否存在指定的注解
    public static boolean assertExistTargetAnnotation(Annotation[] annotations, Class targetAnnotationClass) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == targetAnnotationClass) {
                return true;
            }
        }
        return false;
    }

}
