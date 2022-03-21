package org.simpleframework.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author Peter
 */
public class BeanContainerTest {
    private static BeanContainer beanContainer;
    @BeforeAll
    static void init(){
        beanContainer = BeanContainer.getInstance();
    }
    @Test
    public void loadBeansTest(){
        Assertions.assertEquals(false, beanContainer.isLoaded());
        beanContainer.loadBeans("com.imooc");
        System.out.println(beanContainer.size());

    }
}
