package cn.xzxy.lewy.useJdkReflect.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 自定义动态代理类
 */
public class DynamicProxyHandler implements InvocationHandler {

    // 定义代理对象
    private Object object;

    public void setObject(Object object) {
        this.object = object;
    }

    // 需求：在方法执行前输出"开始执行"，在方式执行后输出"完成执行"
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println(method.getName() + " starting");
        Object result = method.invoke(this.object, args);
        System.out.println(method.getName() + " ending");

        return result;
    }
}
