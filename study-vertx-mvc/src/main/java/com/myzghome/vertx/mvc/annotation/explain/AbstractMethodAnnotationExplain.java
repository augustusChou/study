package com.myzghome.vertx.mvc.annotation.explain;

import com.myzghome.core.annotation.explain.MethodAnnotationExplain;
import com.myzghome.vertx.mvc.annotation.controller.Api;

import java.lang.annotation.Annotation;

/**
 * 作者：周广
 * 创建时间：2017/1/23 0023
 * 必要描述:
 */
public abstract class AbstractMethodAnnotationExplain implements MethodAnnotationExplain {


    protected String getMapperPath(Class mainClass, String methodMapperPath) {
        Annotation api = mainClass.getAnnotation(Api.class);
        if (api != null) {
            String mainMapperPath = ((Api) api).path();
            if (mainMapperPath.length() == 0 && methodMapperPath.length() == 0) {
                return null;
            }

            StringBuilder result = new StringBuilder();
            if (mainMapperPath.length() > 0) {
                result.append("/").append(formatSlash(mainMapperPath));
            }

            if (methodMapperPath.length() > 0) {
                result.append("/").append(formatSlash(methodMapperPath));
            }

            return result.toString();
        } else {
            if (methodMapperPath.length() == 0) {
                return null;
            }
            return "/" + formatSlash(methodMapperPath);
        }
    }


    private String formatSlash(String str) {
        return str.replaceAll("/", "");
    }

}
