package com.myzghome.core.annotation;

import java.lang.annotation.Annotation;

/**
 * 作者：周广
 * 创建时间：2017/1/9 0009
 * 必要描述:
 */
public class AnnotationUtil {

    public static Annotation getTargetAnnotation(Annotation[] annotations,Class targetAnnotationClass){
        for (Annotation annotation:annotations){
            if (annotation.annotationType()==targetAnnotationClass){
                return annotation;
            }
        }
        return null;
    }

    //判断是否存在指定的注解
    public static boolean assertExistTargetAnnotation(Annotation[] annotations,Class targetAnnotationClass){
        for (Annotation annotation:annotations){
            if (annotation.annotationType()==targetAnnotationClass){
                return true;
            }
        }
        return false;
    }

}
