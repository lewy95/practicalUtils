package cn.xzxy.lewy.aboutJdk.classLoader.hotPatch;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义类加载器
 */
public class CustomClassLoader extends ClassLoader {
    private static String patchPath;// 补丁路径

    public CustomClassLoader(String patch) {
        // 打破双亲委派模型，
        // 目的：JVM在CustomClassLoader中找不到类，不会去上级的AppClassLoader中查找，直接去BootStrapClassLoader中查找
        // 当BootStrapClassLoader中找不到则会构建一个新的类，这个类的类加载器是CustomClassLoader
        // 说明：用户编写的类是由AppClassLoader加载器，如果去AppClassLoader中查询一定能找到，所以要实现热更新要打断双亲委派模型
        super(null);
        patchPath = patch;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // 从⾃定义路径下读取类的字节码⽂件
            byte[] cb = getClassBytes(name);
            // 将⼀个 byte 数组转换为 Class 类的实例，这一步必须
            return super.defineClass(name, cb, 0, cb.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 失败则调⽤⽗类的⽅法
        return super.findClass(name);
    }

    // 读取.class 的字节
    private byte[] getClassBytes(String name) throws Exception {
        String fileName = patchPath + name + ".class";
        FileInputStream in = new FileInputStream(new File(fileName));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len;
        while (-1 != (len = in.read())) {
            out.write(len);
        }
        return out.toByteArray();
    }
}
