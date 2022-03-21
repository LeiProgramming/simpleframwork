package org.simpleframework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.simpleframework.core.annotation.Component;
import org.simpleframework.core.annotation.Controller;
import org.simpleframework.core.annotation.Repository;
import org.simpleframework.core.annotation.Service;
import org.simpleframework.util.ClassUtil;
import org.simpleframework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Peter
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class BeanContainer {
    /**
     * 存放所有被配置标记的目标对象Map
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap();

    /**
     *
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Service.class, Controller.class, Repository.class);

    /**
     * 获取Bean容器实例
     *
     * @return BeanContainer
     */
    public static BeanContainer getInstance(){
        return ContainerHolder.HOLDER.instance;
    }
    private enum ContainerHolder{
        HOLDER;
        private BeanContainer instance;
        ContainerHolder(){
            instance = new BeanContainer();
        }
    }

    /**
     * 容器是否已经加载了bean
     */
    private boolean loaded = false;
    /**
     * 是否已经加载过bean
     */
    public boolean isLoaded(){
        return loaded;
    }

    /**
     * Bean实例的数量
     *
     */
    public int size(){
        return beanMap.size();
    }
    /**
     * 扫描加载所以Bean
     *
     * @param packageName 包名
     */
    public synchronized void loadBeans(String packageName){
        //判断bean容器是否被加载过
        if (isLoaded()){
            log.warn("BeanContainer is loaded");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if(ValidationUtil.isEmpty(classSet)){
            log.warn("extract nothing"+packageName);
            return;
        }
        for(Class<?> clazz : classSet){
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION){
                // 如果类上面标记了定义的注解
                if (clazz.isAnnotationPresent(annotation)){
                    // 将目标类本身作为键， 目标类的实例作为值， 放入到beanMap中
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }
        loaded = true;
    }
}
