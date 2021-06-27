package jvm;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JvmCustomerClassLoader extends ClassLoader{

    public static void main(String[] args) throws Exception{
        Class<?> hello = new JvmCustomerClassLoader().findClass("Hello");
        Method method = hello.getMethod("hello");
        method.invoke(hello.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        try {
            Path path = Paths.get("F:\\JAVA进阶\\github\\JavaCourse\\week01\\demo\\src\\jvm\\Hello.xlass");

            byte[] bytes = Files.readAllBytes(path);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            return defineClass(name,bytes,0,bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
