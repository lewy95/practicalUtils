/**
 * 补丁文件，区别于源目录下的文件
 * 使用时需要将补丁编译成java文件
 *
 * 注意点：补丁文件不要有包名
 */
public class PrintService {
    public void printName(String name) {
        System.out.println("patch, " + name);
    }

    public void formatName(String name) {
        System.out.println("format, " + name.toUpperCase());
    }

}
