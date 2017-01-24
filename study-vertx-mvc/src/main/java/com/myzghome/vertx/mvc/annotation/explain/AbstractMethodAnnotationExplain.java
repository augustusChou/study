package com.myzghome.vertx.mvc.annotation.explain;

import com.alibaba.fastjson.JSON;
import com.myzghome.core.annotation.explain.MethodAnnotationExplain;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.vertx.mvc.annotation.controller.Api;
import io.vertx.core.json.Json;
import io.vertx.ext.web.RoutingContext;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * 作者：周广
 * 创建时间：2017/1/23 0023
 * 必要描述:
 */
public abstract class AbstractMethodAnnotationExplain implements MethodAnnotationExplain {


    protected void invoke(BeanContainer beanContainer, Method method, RoutingContext routingContext, String mapperPath) {
        try {
            Object[] methodParams = getMethodParams(beanContainer.getBeanClass(), method, routingContext, mapperPath);
            Object result = method.invoke(beanContainer.getBean(), methodParams);
            if (result != null) {
                routingContext.response().end(JSON.toJSONString(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            routingContext.fail(e);
        }
    }


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

    protected Object[] getMethodParams(Class classes, Method method, RoutingContext routingContext, String mapperPath) throws Exception {
        Class<?>[] methodParams = method.getParameterTypes();
        Object[] params = new Object[methodParams.length];
        for (int i = 0; i < methodParams.length; i++) {
            String paramClassName = methodParams[i].getName();
            if (paramClassName.contains("io.vertx.ext.web.RoutingContext")) {
                //这里是为了过滤RoutingContext
                params[i] = routingContext;
            } else if (paramClassName.contains("java.lang") || methodParams[i].isPrimitive()) {
                //判断是否为包装类型或者基本类型
                if (mapperPath.contains(":")) {
                    //如果带有:占位符类型,则参数从请求里获取 key是占位符名称
                    String paramName = mapperPath.substring(mapperPath.indexOf(":") + 1);
                    String urlParam = routingContext.request().getParam(paramName);
                    params[i] = switchParam(paramClassName, urlParam);
                } else {
                    //从body里获取数据,key使用方法的参数名称
                    String paramName = getParamName(classes, method, i);
                    Object bodyParam = routingContext.getBodyAsJson().getValue(paramName);
                    params[i] = switchParam(paramClassName, bodyParam);
                }
            } else {
                params[i] = Json.decodeValue(routingContext.getBodyAsString(), methodParams[i]);
            }
        }
        return params;
    }


    private Object switchParam(String paramClassName, Object param) {

        switch (paramClassName) {
            case "int":
            case "java.lang.Integer":
                return param == null ? 0 : Integer.parseInt(String.valueOf(param));
            case "double":
            case "java.lang.Double":
                return param == null ? 0d : Double.parseDouble(String.valueOf(param));
            case "long":
            case "java.lang.Long":
                return param == null ? 0L : Long.parseLong(String.valueOf(param));
            default:
                return param;
        }
    }

    private String formatSlash(String str) {
        return str.replaceAll("/", "");
    }

    private String getParamName(Class classes, Method method, int paramIndex) throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass clz = pool.get(classes.getName());
        CtMethod cm = clz.getDeclaredMethod(method.getName());
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        return attr.variableName(paramIndex + pos);
    }

}
