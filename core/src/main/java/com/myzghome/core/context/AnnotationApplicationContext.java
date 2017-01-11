package com.myzghome.core.context;

import com.myzghome.core.annotation.AnnotationUtil;
import com.myzghome.core.annotation.Register;
import com.myzghome.core.bean.BeanContainer;
import com.myzghome.core.bean.factory.AbstractBeanFactory;
import com.myzghome.core.bean.factory.DefaultBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述: 注解扫描上下文
 */
public class AnnotationApplicationContext extends AbstractApplicationContext {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    //注解对象扫描路径
    private String[] packagePaths;

    public AnnotationApplicationContext(String[] packagePaths) throws Exception {
        super(new DefaultBeanFactory());
        this.packagePaths = packagePaths;
    }

    public AnnotationApplicationContext(String[] packagePaths, AbstractBeanFactory abstractBeanFactory) throws Exception {
        super(abstractBeanFactory);
        this.packagePaths = packagePaths;
    }

    //以文件的形式来获取包下的所有Class
    private static void findAndAddClassesInPackageByFile(String packageName,
                                                         String filePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(filePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory())
                || (file.getName().endsWith(".class")));
        // 循环所有文件
        if (dirFiles != null) {
            for (File file : dirFiles) {
                // 如果是目录 则继续扫描
                if (file.isDirectory()) {
                    findAndAddClassesInPackageByFile(packageName + "."
                                    + file.getName(), file.getAbsolutePath(), recursive,
                            classes);
                } else {
                    // 如果是java类文件 去掉后面的.class 只留下类名
                    String className = file.getName().substring(0,
                            file.getName().length() - 6);
                    try {
                        classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
                    } catch (ClassNotFoundException e) {
                        // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    protected void loadBean() throws Exception {
        for (String path : packagePaths) {
            //循环包路径扫描所有class
            Set<Class<?>> classSet = getClasses(path);

            for (Class classes : classSet) {
                if (!beanFactory.assertExistBean(classes)) {
                    registerBean(classes);
                }
            }
        }
    }

    //根据class创建相应的bean
    private void registerBean(Class classes) throws Exception {
        //从类的注解数组里获取指定的注解，如果没有找到就返回null
        Annotation annotation = AnnotationUtil.getTargetAnnotation(classes.getAnnotations(), Register.class);
        if (annotation != null) {
            Register register = (Register) annotation;
            BeanContainer beanContainer = new BeanContainer(classes);
            beanContainer.setBeanClass(classes);

            if (register.name().length() > 0) {
                //如果注册bean的时候指定了名称，就使用指定的名称
                beanContainer.setBeanName(register.name());
            } else {
                beanContainer.setBeanName(beanFactory.getSimpleClassName(classes));
            }

            beanFactory.registerBean(beanContainer.getBeanName(), beanContainer);
            log.debug("register: " + beanContainer.getBeanName());
        }
    }


    //从指定的包路径中获取所有的Class
    private Set<Class<?>> getClasses(String packagePath) {
        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<>();
        // 获取包的名字 并进行替换
        String packageDirName = packagePath.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packagePath, filePath, true, classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }


}
