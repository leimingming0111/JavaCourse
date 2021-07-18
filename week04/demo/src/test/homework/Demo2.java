package test.homework;

/**
 * @program: demo
 * @Date: 2021/7/15 9:23
 * @Author: leimingming
 * @Description: 使用 join()
 */
public class Demo2 {
    public static void main(String[] args) {
        long start=System.currentTimeMillis();

        final int[] sum = new int[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sum[0] = sum();
            }
        });
        thread.start();

         try {
            thread.join();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+sum[0]);

            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        } catch (InterruptedException e) {
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
