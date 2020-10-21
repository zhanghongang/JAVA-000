package ClassloaderDemo;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 任务：自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，
 * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
            try {
                Class<?> aClass =  new HelloClassLoader().findClass("Hello");
                Object object = aClass.newInstance();
                try {
                    HelloClassLoader helloClassLoader = new HelloClassLoader();
                    Method method = aClass.getMethod("hello");
                    try {
                        method.invoke(object);
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Path path = Paths.get("Week_01/ClassloaderDemo/Hello.xlass");
        byte[] helloBase64 = null;
        try {
            helloBase64 = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < helloBase64.length; i++) {
            helloBase64[i] = (byte) (255 - helloBase64[i]);
        }

        return defineClass(name, helloBase64, 0, helloBase64.length);
    }
}
