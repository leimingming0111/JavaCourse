package test.homework;

import java.util.concurrent.locks.LockSupport;

/**
 * @program: demo
 * @Date: 2021/7/15 19:19
 * @Author: leimingming
 * @Description:
 */
public class Demo7 {
    public static void main(String[] args) {
        Thread currentThread = Thread.currentThread();

        long start=System.currentTimeMillis();

        final int[] sum = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sum[0] = sum();
                LockSupport.unpark(currentThread);
            }
        });
        thread.start();

        LockSupport.park();
        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+sum[0]);

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
