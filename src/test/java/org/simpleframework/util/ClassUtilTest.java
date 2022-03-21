package org.simpleframework.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author Peter
 */
public class ClassUtilTest {
    @DisplayName("提取目标类方法")
    @Test
    public void extractClassTest(){
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("com.imooc");
        System.out.println(classSet);
    }
}
