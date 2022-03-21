package demo.pattern.singleton;

/**
 * @author Peter
 */
public class StarvingSingleton {
    private static final StarvingSingleton starvingSingleton = new StarvingSingleton();
    private StarvingSingleton(){}
    public static StarvingSingleton getInstance(){
        return starvingSingleton;
    }
}
