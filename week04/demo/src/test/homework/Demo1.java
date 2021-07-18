package test.homework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @program: demo
 * @Date: 2021/7/15 9:23
 * @Author: leimingming
 * @Description: 使用FutureTask API
 */
public class Demo1 {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();

        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });

        Thread thread = new Thread(task);
        thread.start();

        try {
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+task.get());

            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

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
