package cn.xzxy.lewy.aboutJdk.comparator;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Comparator 1.8 new feature
 */
public class ComparatorTest {

    public static List<Employee> employeeList = new ArrayList<>();

    @Before
    public void initList() {
        employeeList.add(new Employee("yy", 25, 5000, 9922001));
        employeeList.add(new Employee("ym", 22, 10000, 5924001));
        employeeList.add(new Employee("ly", 35, 5000, 3924401));
        employeeList.add(new Employee("sy", 35, 2000, 3911401));
    }

    @Test
    public void testSort() {
        // sort 对象接收一个 Comparator 函数式接口，可以传入一个lambda表达式
        // 以下两种方式等价：
        // employeeList.sort((o1, o2) -> o1.getName().compareTo(o2.getName()));
        employeeList.sort(Comparator.comparing(Employee::getName));
        employeeList.forEach(System.out::println);
    }

    @Test
    public void testReversed() {
        // 求反转 ，以下两种方式一样，第一种添加一个自定义的比较器
        // employeeList.sort(Comparator.comparing(
        //         Employee::getName, Comparator.reverseOrder()));
        employeeList.sort(Comparator.comparing(Employee::getName).reversed());
        employeeList.forEach(System.out::println);
    }

    @Test
    public void testNullsFirstOrLast() {
        employeeList.add(null);  //插入一个null元素
        employeeList.sort(Comparator.nullsFirst(Comparator.comparing(Employee::getName))); // 排在最前面
        employeeList.sort(Comparator.nullsLast(Comparator.comparing(Employee::getName))); // 排在最后面
        employeeList.forEach(System.out::println);
    }

    @Test
    public void testThenComparing() { // 连接多个判断
        employeeList.sort(Comparator.comparing(Employee::getAge).thenComparing(Employee::getName));
        // employeeList.sort(Comparator.comparingInt(Employee::getAge).thenComparing(Employee::getName));
        employeeList.forEach(System.out::println);
    }

}

class Employee {
    String name;
    int age;
    double salary;
    long mobile;

    public Employee(String name, int age, double salary, long mobile) {
        this.name = name;
        this.age = age;
        this.salary = salary;
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getMobile() {
        return mobile;
    }

    public void setMobile(long mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Employee{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", salary=").append(salary);
        sb.append(", mobile=").append(mobile);
        sb.append('}');
        return sb.toString();
    }
}
