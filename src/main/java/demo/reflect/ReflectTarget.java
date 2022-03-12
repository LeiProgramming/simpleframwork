package demo.reflect;

/**
 * @author Peter
 */
public class ReflectTarget {
    public static void main(String[] args) throws ClassNotFoundException {
        ReflectTarget reflectTarget = new ReflectTarget();
        Class res1 = reflectTarget.getClass();
        System.out.println(res1.getName());
        Class<ReflectTarget> reflectTargetClass2 = ReflectTarget.class;
        System.out.println(reflectTargetClass2);
        Class<?> aClass3 = Class.forName("demo.reflect.ReflectTarget");
        System.out.println(aClass3);
    }
}
