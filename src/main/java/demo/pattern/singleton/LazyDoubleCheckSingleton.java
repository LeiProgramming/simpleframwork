package demo.pattern.singleton;

/**
 * @author Peter
 */
public class LazyDoubleCheckSingleton {
    private volatile static LazyDoubleCheckSingleton instance;

    private LazyDoubleCheckSingleton(){

    }

    public static LazyDoubleCheckSingleton getInstance(){
        if (instance == null){
            synchronized (LazyDoubleCheckSingleton.class){
                if (instance == null){
                    /**
                     * 1.分配对象内存空间
                     * 2.初始化对象
                     * 3。设置instance指向刚分配的内存地址
                     */
                    instance = new LazyDoubleCheckSingleton();
                }
            }
        }
        return instance;
    }
}
