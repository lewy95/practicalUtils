package cn.xzxy.lewy.aboutJdk.io;

import java.io.*;

public class TestBufferedStream {

    public static void main(String[] args) {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //1.提供读入、写出的文件
            File file = new File("data/file/letter");
            File newFile = new File("data/file/letter_copy");
            //2.创建相应的节点流，非文本文件只能用字节流
            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(newFile);
            //3.包装节点流
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);
            //4.具体的实现文件复制的操作
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = bis.read(buf)) != -1) {
                bos.write(buf, 0, len);
                bos.flush(); //刷新缓冲区，将缓冲区中的数据刷到目的地文件中
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //5.关闭流
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
