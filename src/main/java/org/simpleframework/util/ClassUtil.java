package org.simpleframework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Peter
 */
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    public static Set<Class<?>> extractPackageClass(String packageName){
        ClassLoader classLoader = getClassLoader();
        URL url = classLoader.getResource(packageName.replace(".","/"));
        if (url == null){
            log.warn("nothing from " + packageName);
            return null;
        }
        Set<Class<?>> classSet = null;
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)){
            classSet = new HashSet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        //TODO 此处可以加入其他资源类型的处理
        return classSet;
    }

    /**
     *
     * 
     * @param emptyClassSet 装载目标类的集合
     * @param fileSource 文件或者目录
     * @param packageName 包名
     */
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {
        if (!fileSource.isDirectory()){
            return;
        }
        //如果是一个文件夹，则调用其listFiles方法获取文件夹下的文件或者文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()){
                    return true;
                } else {
                    // 获取文件绝对路径
                    String absoluteFilePath = file.getAbsolutePath();
                    if (absoluteFilePath.endsWith(".class")){
                        // 如果是class文件，直接加载
                        addToClassSet(absoluteFilePath);
                    }
                }
                return false;
            }

            private void addToClassSet(String absoluteFilePath) {
                absoluteFilePath = absoluteFilePath.replace(File.separator, ".");
                String className = absoluteFilePath.substring(absoluteFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                Class targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });
        if (files != null){
            for (File f : files){
                // 递归调用
                extractClassFile(emptyClassSet, f, packageName);
            }
        }
    }

    /**
     * 获取class对象
     * @param className
     * @return
     */
    public static Class<?> loadClass(String className){
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("error:",e);
            throw new RuntimeException();
        }
    }

    /**
     * 实例化class
     * @param clazz
     * @param accessible 是否支持创建出私有class对象的实例
     * @param <T>
     * @return 类的实例化
     */
    public static <T> T newInstance(Class<?> clazz, boolean accessible){
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(accessible);
            return (T)constructor.newInstance();
        } catch (Exception e) {
            log.error("error:",e);
            throw new RuntimeException();
        }
    }
    /**
     * 获取classLoader
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String[] args) {
        extractPackageClass("com.imooc.entity");
    }
}
