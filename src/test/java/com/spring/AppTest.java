package com.spring;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;
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

}
