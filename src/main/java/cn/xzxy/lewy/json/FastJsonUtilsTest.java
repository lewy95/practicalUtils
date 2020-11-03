package cn.xzxy.lewy.json;


import java.util.concurrent.TimeUnit;

public class FastJsonUtilsTest {

    public static void main(String[] args) {

        new Thread(() -> {
            System.out.println("running");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
