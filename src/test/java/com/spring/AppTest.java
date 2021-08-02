package com.spring;

import static org.junit.Assert.assertTrue;

import com.spring.module.Category;
import org.junit.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test01() {
        Application application = new Application("com.study.spring.ApplicationContext");

    }

    @Test
    public void test02() {
        List list = new ArrayList();
        Iterator iterator = list.iterator();
        Iterator iterator1 = list.iterator();
        System.out.println(iterator);
        System.out.println(iterator1);
        System.out.println(iterator == iterator1);
    }

    @Test
    public void test03(){
        AbstractBImpl abstractB = new AbstractBImpl();
        System.out.println(InterfaceA.class.isAssignableFrom(abstractB.getClass()));
        System.out.println(abstractB.getClass().isAssignableFrom(InterfaceA.class));
    }

    /**
     * 文件递归扫描测试
     */
    @Test
    public void fileScan() {
        File file = new File("E:\\2Executioner\\Desktop\\test");
        scan(file);
    }

    public static void scan(File file) {
        if (file.isFile()) {
            System.out.println(file);
            return;
        } else {
            File[] files = file.listFiles();
            for (File f : files) {
                scan(f);
            }
        }
    }

    @Test
    public void test04() {
        System.out.println(Category.class);
        System.out.println(InterfaceA.class.isInterface());
    }

    @Test
    public void fieldTest() throws ClassNotFoundException, IllegalAccessException {
        Category category = new Category();
        Field[] declaredFields = category.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            System.out.println(field);
            Object type = field.getGenericType();
            System.out.println("字段类型：" + type); // 获取字段类型
            System.out.println(Class.forName("com.spring.test.module.User").equals(type));

            System.out.println();
        }

        System.out.println(category);

        Field field = declaredFields[0];
        field.setAccessible(true);
        field.set(category, "12");
        System.out.println(category);
    }
}
