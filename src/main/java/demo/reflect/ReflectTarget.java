package demo.reflect;

/**
 * @author Peter
 */
public class ReflectTarget {
    //Constructor
    ReflectTarget(String str){
        System.out.println("s="+str);
    }
    public ReflectTarget(){
        System.out.println("共有的无参构造");
    }
    public ReflectTarget(char name){
        System.out.println("一个参数的构造方法"+name);
    }
    public ReflectTarget(char name, int age){
        System.out.println("name"+name+"age"+age);
    }
    protected ReflectTarget(boolean n){
        System.out.println("受到保护的"+n);
    }
    private ReflectTarget(int index){
        System.out.println("私有的"+index);
    }
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
