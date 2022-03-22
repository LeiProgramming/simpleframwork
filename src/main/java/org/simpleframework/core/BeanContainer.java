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
import java.util.*;
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

    /**
     * 添加一个class对象以及其Bean实例
     * @param clazz
     * @param bean
     * @return
     */
    public Object addBean(Class<?> clazz, Object bean){
        return beanMap.put(clazz, bean);
    }

    /**
     *移除一个IOC容器管理的对象
     * @param clazz class对象
     * @return 删除的Bean实例，没有则返回null
     */
    public Object removeBean(Class<?> clazz){
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取Bean实例
     *
     * @param clazz Class对象
     * @return Bean实例
     */
    public Object getBean(Class<?> clazz){
        return beanMap.get(clazz);
    }

    /**
     * 获取容器管理的所有Class集合
     * @return
     */
    public Set<Class<?>> getClasses(){
        return beanMap.keySet();
    }

    /**
     * 获取所有Bean集合
     *
     * @return
     */
    public Set<Object> getBeans(){
        return new HashSet<>(beanMap.values());
    }

    /**
     * 根据注解帅选出Bean的Class集合
     * @param annotation
     * @return
     */
    public Set<Class<?>> getClassesByAnnotation(Class< ? extends Annotation> annotation){
        // 获取BeanMap所有的class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }
        // 通过注解筛选出被注解标记的class对象，并添加到classSet里面
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet){
            // 类是否有相关的注解标记
            if (clazz.isAnnotationPresent(annotation)){
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括本身
     * @param interfaceOrClass
     * @return
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass){
        // 获取BeanMap所有的class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)){
            log.warn("nothing in beanMap");
            return null;
        }
        // 判断keySet里的元素是否是传入的接口或者类的子类，如果是，添加到classSet里面
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet){
            // 判断KeySet里面的元素是否是传入的接口或者类的子类
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)){
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

}
