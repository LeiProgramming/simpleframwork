package demo.pattern.factory.simple;

import demo.pattern.factory.entity.DellHouse;
import demo.pattern.factory.entity.HpHouse;
import demo.pattern.factory.entity.Mouse;

/**
 * @author Peter
 */
public class MouseFactory {
    public static Mouse createMouse(int type){
        switch (type){
            case 0: return new DellHouse();
            case 1: return new HpHouse();
            default:return new DellHouse();
        }
    }

    public static void main(String[] args) {
        Mouse mouse = MouseFactory.createMouse(1);
        mouse.sayHi();
    }
}
