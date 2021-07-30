package com.spring;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.io.File;

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
