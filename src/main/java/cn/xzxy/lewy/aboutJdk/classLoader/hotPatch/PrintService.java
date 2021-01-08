package cn.xzxy.lewy.aboutJdk.classLoader.hotPatch;

/**
 * 这是源文件，区别于补丁文件
 * 补丁文件位于 D:\workSets\javaSets\practicalUtils\data\patchClasses
 */
public class PrintService {
    public void printName(String name) {
        System.out.println("print, " + name);
    }
}
