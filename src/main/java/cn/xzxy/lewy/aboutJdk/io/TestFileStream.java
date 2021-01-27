package cn.xzxy.lewy.aboutJdk.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestFileStream {

    /**
     * copy file data
     * @param oldFile oldFile
     * @param newFile newFile
     */
    private static void copyFile(File oldFile, File newFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            //2.创建一个FileInputStream对象
            fis = new FileInputStream(oldFile);
            fos = new FileOutputStream(newFile);
            //3.调用FileInputStream的方法，实现文件的读取（需要异常处理）
            //法一：
            //int ch = 0;
            //while ((ch = fis.read()) != -1) { //条件是没有读到结尾
            //    System.out.print((char) ch); //调用读取流的read方法，读取一个字符
            //}
            //法二：（高效，自定义缓冲区）优于第一种
            //char[] buf = new char[1024]; //将读取字符存入字符数组 长度1024的整数倍 Reader类用
            byte[] buf = new byte[1024]; //将读字节存入字节数组 InputStream类用
            int len = 0; // len记录每次读入byte中的字节的长度1024
            while ((len = fis.read(buf)) != -1) {
                // System.out.print(new String(buf, 0, len)); // 将字节数组转换为字符串
                fos.write(buf, 0 ,len); // 可以实现文件的复制
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭相应的流（必须显示关闭，finally语句必须写，需要异常处理）
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * append str to file
     * @param file file
     */
    private static void appendToFile(File file) {
        FileOutputStream fos = null;
        try {
            //2.创建一个FileOutputStream对象，写在最上面
            //参数二表示是否续写，默认为false即不append，直接覆盖原文件
            fos = new FileOutputStream(file,true);
            //3.写入的操作（需要异常处理）
            fos.write("EWY".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4.关闭相应的流（必须显示关闭，需要异常处理）
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 创建一个File对象，要读取的文件一定要存在，否则异常
        try {
            File oldFile = new File("data/file/letter");
            File newFile = new File("data/file/letter_copy");
            TestFileStream.copyFile(oldFile, newFile);
            System.out.println("copy successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
