package cn.xzxy.lewy.aboutJdk.classLoader;

import java.io.*;

/**
 * 自定义类加载器
 */
public class FileClassLoader extends ClassLoader {
    private String rootDir;

    public FileClassLoader(String rootDir) {
        this.rootDir = rootDir;
    }

    /**
     * 编写findClass方法的逻辑
     * @param name 类名
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //获取类的class文件字节数组
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            //直接生成class对象
            return defineClass(name, classData, 0, classData.length);
        }
    }

    /**
     * 编写获取class文件并转换为字节码流的逻辑
     * @param className 类名
     */
    private byte[] getClassData(String className) {
        //读取类文件的字节
        String path = classNameToPath(className);
        try {
            InputStream ins = new FileInputStream(path);
            ByteArrayOutputStream aos = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int bytesNumRead;
            // 读取类文件的字节码
            while ((bytesNumRead = ins.read(buffer)) != -1) {
                aos.write(buffer, 0, bytesNumRead);
            }
            return aos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 类文件的完整路径
     * @param className 类名
     */
    private String classNameToPath(String className) {
        return rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }

    /**
     * 读取文件
     */
    public static void main(String[] args) {
        String rootDir = "C:\\java\\JVM\\JVMInstruction\\src";
        //创建自定义文件类加载器
        FileClassLoader loader = new FileClassLoader(rootDir);

        try {
            //加载指定的class文件,加上包名
            Class<?> object1 = loader.loadClass("SelfClassLoader.DemoObj");
            System.out.println(object1.newInstance().toString());

            //输出结果：I am DemoObj
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
