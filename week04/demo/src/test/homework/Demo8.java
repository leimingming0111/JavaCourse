package test.homework;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: demo
 * @Date: 2021/7/15 19:21
 * @Author: leimingming
 * @Description:
 */
public class Demo8 {

    public static void main(String[] args) throws InterruptedException {

        long start=System.currentTimeMillis();

        final int[] sum = new int[1];

        CountDownLatch countDownLatch = new CountDownLatch(1);


        ExecutorService service = Executors.newFixedThreadPool(1);
        service.execute(
            () -> {
            sum[0] = sum();
            countDownLatch.countDown();

        });

        countDownLatch.await();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+sum[0]);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        service.shutdown();

    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
