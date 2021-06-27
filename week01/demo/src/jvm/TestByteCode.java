package jvm;

/**
 * @program: demo
 * @Date: 2021/6/24 18:41
 * @Author: leimingming
 * @Description:
 */
public class TestByteCode {

    public static void main(String[] args) {

        int num1 = 1;
        int num2 = 2;
        boolean flag = false;

        int num3 = num1 + num2;

        int num4 = num1 * num3;

        float num5 = (float) num3/num4 ;

        for (int i = 0; i < 3; i++) {
            System.out.println(i);
        }

        if (num5 < num4) {
            System.out.println(num1);
        }
    }

}
