package demo.reflect;

import java.lang.reflect.Constructor;

/**
 * @author Peter
 */
public class ConstructorController {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        Class clazz = Class.forName("demo.reflect.ReflectTarget");
        System.out.println("获取所有的公有构造方法");
        Constructor[] constructors = clazz.getConstructors();
        for(Constructor c : constructors){
            System.out.println(c);
        }
        System.out.println("获取所有的构造方法");
        Constructor[] declaredConstructor = clazz.getDeclaredConstructors();
        for (Constructor con : declaredConstructor){
            System.out.println(con);
        }
    }


}
