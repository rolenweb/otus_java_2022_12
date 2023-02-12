package ru.otus.homework;

import ru.otus.homework.handlers.LogHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTestLoggingFactory implements ProxyFactoryInterface {
    public TestLoggingInterface create() {
        InvocationHandler handler = new LogHandler(new TestLoggingImpl());

        return (TestLoggingInterface) Proxy.newProxyInstance(ProxyTestLoggingFactory.class.getClassLoader(), new Class<?>[]{TestLoggingInterface.class}, handler);
    }
}
