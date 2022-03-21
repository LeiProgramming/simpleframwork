package demo.pattern.factory.entity;

import demo.annotation.TestAnnotation;

/**
 * @author Peter
 */
public class HpHouse implements Mouse{
    @Override
    @TestAnnotation
    public void sayHi() {
        System.out.println("HP");
    }
}
