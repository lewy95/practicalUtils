package cn.xzxy.lewy.aboutJdk.classLoader.hotPatch;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ClassLoaderTest {
    public static void main(String[] args) throws Exception {
        String name = "java";
        // JVM使⽤系统类加载器(AppClassLoader)完成对PrintService.class加载后得到Class对象appClazz
        PrintService service = new PrintService();
        Class appClazz = service.getClass();
        System.out.println(appClazz.getClassLoader());
        service.printName(name);

        // JVM使⽤⾃定义类加载器(CustomClassLoader)对补丁路径下的class⽂件重新定义得到新的Class对象customClazz
        String patch = "D:\\workSets\\javaSets\\practicalUtils\\data\\patchClasses\\";
        CustomClassLoader ccl = new CustomClassLoader(patch);
        // 使⽤给定的类加载器，返回与带有给定字符串名的类或接⼝相关联的 Class 对象
        Class customClazz = Class.forName("PrintService", true, ccl);
        System.out.println(customClazz.getClassLoader());

        // 验证PrintService的2个不同类加载器得到的Class对象是否相等
        System.out.println("appClazz == customClazz >> " + (appClazz == customClazz));

        // 验证下旧Class对象的实例对象
        service.printName(name);

        // 这⾥类型转换会失败，因为customClazz和appClazz不属于同⼀个Class对象
        // PrintService cannot be cast to cn.xzxy.lewy.aboutJdk.classLoader.hotPatch.PrintService
        // PrintService patchService = (PrintService) customClazz.newInstance();

        // 测试下新Class对象的实例对象，使⽤反射的⽅式完成输出
        Object object = customClazz.newInstance();// 新Class对象的实例对象
        List<Method> methods = Arrays.asList(customClazz.getMethods());
        // 被重写的⽅法printName
        Optional<Method> overrideMethod = methods.stream().filter(method ->
                "printName".equals(method.getName())).findFirst();
        overrideMethod.ifPresent(method -> invokeMethod(object, method, name));
        // 新增的⽅法printName
        Optional<Method> addMethod = methods.stream().filter(method ->
                "formatName".equals(method.getName())).findFirst();
        addMethod.ifPresent(method -> invokeMethod(object, method, name));
    }

    private static void invokeMethod(Object object, Method method, Object...
            args) {
        try {
            method.invoke(object, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
