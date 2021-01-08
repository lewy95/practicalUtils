package cn.xzxy.lewy.aboutJdk.reflect;

import cn.xzxy.lewy.aboutJdk.reflect.prepare.Student;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 根据反射获取类相关信息
 */
public class GetReflects {

    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("cn.xzxy.lewy.aboutJDK.reflect.prepare.Student");
            // 获取类所在包
            Package pa = clazz.getPackage();
            System.out.println("所在包是 " + pa.getName());

            // 获取父类
            Class superClazz = clazz.getSuperclass();
            System.out.println("父类是 " + superClazz.getName());
            // 获取接口
            Class[] interfaces = clazz.getInterfaces();
            for (Class ci : interfaces) {
                System.out.println("实现接口是 " + ci.getName());
            }
            // 获取构造器（仅限公有）
            Constructor[] constructors = clazz.getConstructors();
            // 获取构造器（公有+私有）
            // Constructor[] constructors2 = clazz.getDeclaredConstructors();
            for (Constructor cs : constructors) {
                // 构造方法是 cn.xzxy.lewy.useJdkReflect.prepare.Student 修饰符为 1
                // getModifiers 返回数字1表示public 2表示private
                System.out.println("构造方法是 " + cs.getName() + " 修饰符为 " + cs.getModifiers());

                Class[] parameterTypes = cs.getParameterTypes();
                for (Class pt : parameterTypes) {
                    System.out.println("构造方法是 " + cs.getName() + " 参数类型为 " + pt.getName());
                }
            }

            // 获取类的方法
            Method[] methods = clazz.getMethods(); // 获取所有的公有方法，包括Object类下的方法
            // Method method = clazz.getMethod("callName", String.class); // 根据方法名获取具体的公有方法
            // Method[] methods = clazz.getDeclaredMethods(); // 获取所有的方法，包括私有
            // Method method = clazz.getDeclaredMethod("callName", String.class); // 根据方法名获取具体的方法（包含私有）
            for (Method method : methods) {
                System.out.println("修饰符 " + method.getModifiers());
                System.out.println("方法名 " + method.getName());
                System.out.println("返回值类型 " + method.getReturnType());

                Class<?>[] pcs = method.getParameterTypes();
                if (pcs.length > 0) {
                    for (Class pc : pcs) {
                        System.out.println("=======参数类型：" + pc.getName());
                    }
                }

                // 测试方法的调用
                if ("callName".equals(method.getName())) {
                    // 调用具有的方法，通过invoke方法
                    // 参数1：需要实例化的对象  参数2：调用当前方法的实际参数
                    Constructor myc = clazz.getConstructor(String.class);
                    //myc.setAccessible(true); // 如果是私有方法需要接触封装
                    Student student = (Student) myc.newInstance("yy");
                    method.invoke(student, "yy"); // 也可以接受有返回值的方法
                }
            }

            // 属性
            Field[] fields = clazz.getFields(); // 获取所有的公有属性
            // Field[] fields = clazz.getField("name"); // 获取具体的公有属性
            //Field[] fields = clazz.getDeclaredFields(); // 获取所有的属性（包括私有）
            // Field[] fields = clazz.getDeclaredField("name"); // 获取所有的属性（包括私有）
            for (Field field : fields) {
                System.out.println("属性修饰符：" + field.getModifiers());
                System.out.println("属性类型：" + field.getType());
                System.out.println("属性名称：" + field.getName());
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
