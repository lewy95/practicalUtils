package cn.xzxy.lewy.useJdkReflect.prepare;

public class Student extends Person implements Study,Gaming {

    public String name;
    private String school;

    // 空构造方法
    public Student() {
    }

    public Student(String name) {
        this.name = name;
    }

    // 私有构造方法
    private Student(String name, String school) {
        this.name = name;
        this.school = school;
    }

    public String callName(String name) {
        System.out.println("**********" + name + "ymtlyltjsycy**********");
        return name;
    }

    private String callSchool(String school) {
        return school;
    }

    @Override
    public void football() {
        System.out.println("kick the ball");
    }

    @Override
    public void preview() {
        System.out.println("do preview");
    }

    @Override
    public void review() {
        System.out.println("do review");
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
