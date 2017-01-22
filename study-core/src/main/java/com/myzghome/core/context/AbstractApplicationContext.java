package com.myzghome.core.context;

import com.myzghome.core.bean.factory.AbstractBeanFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * 作者：周广
 * 创建时间：2017/1/8 0008
 * 必要描述:
 */
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected AbstractBeanFactory beanFactory;
    //类扫描路径
    protected String[] packagePaths;
    //所有扫描到的类
    protected Set<Class<?>> classSet;

    public AbstractApplicationContext(AbstractBeanFactory beanFactory, String[] packagePaths) {
        this.beanFactory = beanFactory;
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

    //核心方法
    public void refresh() throws Exception {
        scanClass();
        loadAnnotationExplain();
        loadBean();
        beanFactory.initialize();
    }

    //加载bean到注册工厂，实现由子类负责
    protected abstract void loadBean() throws Exception;

    //注册注解解释器
    private void loadAnnotationExplain() throws Exception {
        for (Class classes : classSet) {
            beanFactory.registerAnnotationExplain(classes);
        }
    }

    //扫描指定路径的class
    private void scanClass() {
        classSet = new HashSet<>();
        for (String path : packagePaths) {
            //循环包路径扫描所有class
            classSet.addAll(getClasses(path));
        }
    }

    @Override
    public Object getBean(String beanName) throws Exception {
        return beanFactory.getBean(beanName);
    }

    public Object getBean(Class classes) {
        return beanFactory.getBean(classes);
    }

    protected int getBeanCount() {
        return beanFactory.getBeanCount();
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
