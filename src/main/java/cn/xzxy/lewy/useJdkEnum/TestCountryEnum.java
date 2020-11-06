package cn.xzxy.lewy.useJdkEnum;

import java.util.concurrent.CountDownLatch;

public class TestCountryEnum {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " came in");
                countDownLatch.countDown();
            }, CountryEnum.getTextByCode(i) != null ? CountryEnum.getTextByCode(i).getReturnText() : Thread.currentThread().getName())
                    .start();
        }

        try {
            countDownLatch.await();
            System.out.println("========= all came in =========");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
