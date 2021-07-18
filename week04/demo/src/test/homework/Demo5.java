package test.homework;

import java.util.concurrent.*;

/**
 * @program: demo
 * @Date: 2021/7/15 19:05
 * @Author: leimingming
 * @Description: 使用线程池后在主线程循环等待
 */
public class Demo5 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        long start=System.currentTimeMillis();

        final int[] result = new int[1];

        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                result[0] = sum();
            }
        });

        while (result[0] == 0) {
            System.out.println("等待结果");
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+ result[0]);
        pool.shutdown();

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
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
