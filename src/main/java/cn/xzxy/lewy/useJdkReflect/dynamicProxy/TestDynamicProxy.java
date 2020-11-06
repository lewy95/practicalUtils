package cn.xzxy.lewy.useJdkReflect.dynamicProxy;

import java.lang.reflect.Proxy;

/**
 * 测试自定义动态代理
 * <p>
 * 注意：如果一个对象想要通过Proxy.newProxyInstance方法被代理，
 * 那么这个对象的类一定要有相应的接口，就像本类中的IPlayerService和PlayerServiceImpl一样
 */
public class TestDynamicProxy {

    public static void main(String[] args) {
        IPlayerService player = new PlayerServiceImpl();

        DynamicProxyHandler handler = new DynamicProxyHandler();
        handler.setObject(player);

        /**
         * Proxy.newProxyInstance(ClassLoader, interfaces, h)
         * 参数一：代理对象的类加载器
         * 参数二：被代理对象的接口
         * 参数三：代理对象
         */
        IPlayerService p = (IPlayerService) Proxy.newProxyInstance(
                handler.getClass().getClassLoader(),
                player.getClass().getInterfaces(),
                handler);

        p.goal();
        System.out.println("************");
        p.save();

    }

}
