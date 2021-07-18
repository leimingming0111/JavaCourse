package test.homework;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @program: demo
 * @Date: 2021/7/15 19:31
 * @Author: leimingming
 * @Description:
 */
public class Demo9 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start=System.currentTimeMillis();

        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(Demo9::sum);

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+ future.get());

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
