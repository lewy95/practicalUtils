package cn.xzxy.lewy.aboutJdk.reflect;

import cn.xzxy.lewy.aboutJdk.reflect.prepare.Student;

import java.lang.reflect.Constructor;

/**
 * 通过反射的构造方法来创建对象
 */
public class CreateReflects {

    public static void main(String[] args) {
        try {
            Class clazz = Class.forName("cn.xzxy.lewy.useJdkReflect.prepare.Student");

            // 情形一：默认调用的是空构造函数
            Student student = (Student) clazz.newInstance();
            System.out.println(student.toString()); // Student{name='null', school='null'}
            // 情形二：调用有参构造函数
            Constructor myc = clazz.getConstructor(String.class);
            Student student2 = (Student) myc.newInstance("muller");
            System.out.println(student2.toString()); // Student{name='muller', school='null'}
            // 情形三：强制调用私有构造函数
            Constructor myc2 = clazz.getDeclaredConstructor(String.class, String.class);
            myc2.setAccessible(true); // 解决私有封装，必须加上这一步才能用newInstance，否则报错
            Student student3 = (Student) myc2.newInstance("muller", "munich college");
            System.out.println(student3.toString()); // Student{name='muller', school='munich college'}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
