package org.simpleframework.inject;

import com.imooc.controller.frontend.MainPageController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.simpleframework.core.BeanContainer;

/**
 * @author Peter
 */
public class DependencyInjectorTest {
    @DisplayName("依赖注入IOC")
    @Test
    public void doIOCTest(){
        BeanContainer instance = BeanContainer.getInstance();
        instance.loadBeans("com.imooc");
        Assertions.assertEquals(true, instance.isLoaded());
        MainPageController mainPageController = (MainPageController) instance.getBean(MainPageController.class);
        Assertions.assertEquals(true, mainPageController instanceof MainPageController);
        new DependencyInjector().doIoc();
        Assertions.assertNotEquals(null, mainPageController.getHeadLineShopCategoryCombineService());
    }
}
