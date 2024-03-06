package cn.xzxy.lewy;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class GuavaBloomFilter {
    public static void main(String[] args) {
        BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 1000000, 0.1);

        for (int i = 0; i < 1000000; i++) {
            bloomFilter.put(i);
        }

        Long startTime = System.nanoTime();
        System.out.println(bloomFilter.mightContain(6666));
        System.out.println(bloomFilter.mightContain(99999));
        Long stopTime = System.nanoTime();
        System.out.println("耗时：" + (stopTime - startTime));

    }
}
