package com.test.proxy;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.test.proxy.cglib.CglibProxyCreator;
import com.test.proxy.cglib.Hello;
import com.test.proxy.cglib.HelloInterceptor;
import com.test.proxy.javassist.HelloFilterAndHandler;
import com.test.proxy.javassist.JavassistProxyCreator;


public class ProxyCreatorTest {

    /**
     * jdk11 运行时添加 -Djdk.proxy.ProxyGenerator.saveGeneratedFiles=true 可以保存动态生成的代理类
     */
    @Test
    public void testJdk() {
        UserService userService = new UserServiceImpl();
        userService.save(null);
        JdkProxyCreator proxyCreator = new JdkProxyCreator(userService);
        Object proxy = proxyCreator.getProxy();
        userService = (UserService) proxy;
        userService.save(null);
    }

    @Test
    public void testInterfaces() {
        Class<?>[] interfaces = UserService.class.getInterfaces();
        Arrays.asList(interfaces).forEach(System.out::println);
    }

    @Test
    public void testCglib() {
        Hello hello = new Hello();
        hello.say();
        ProxyCreator proxyCreator = new CglibProxyCreator(hello, new HelloInterceptor());
        Object proxy = proxyCreator.getProxy();
        hello = ((Hello) proxy);
        hello.say();
    }

    @Test
    public void testJavassist() {
        Hello hello = new Hello();
        hello.say();
        HelloFilterAndHandler helloFilterAndHandler = new HelloFilterAndHandler(hello);
        ProxyCreator proxyCreator = new JavassistProxyCreator<>(hello, helloFilterAndHandler);
        Object proxy = proxyCreator.getProxy();
        hello = ((Hello) proxy);
        hello.say();
    }
}
