package test.homework;

import java.util.concurrent.*;

/**
 * @program: demo
 * @Date: 2021/7/15 10:02
 * @Author: leimingming
 * @Description: 使用线程池submit
 */
public class Demo4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {


        long start=System.currentTimeMillis();

        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<Integer> submit = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+ submit.get());
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
