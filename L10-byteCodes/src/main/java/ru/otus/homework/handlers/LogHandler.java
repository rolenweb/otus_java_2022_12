package ru.otus.homework.handlers;

import ru.otus.homework.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LogHandler implements InvocationHandler {
    private final Object logObject;

    public LogHandler(Object logObject) {
        this.logObject = logObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?>[] params = method.getParameterTypes();
        Method proxiedMethod = logObject.getClass().getDeclaredMethod(method.getName(), params);

        if (proxiedMethod.isAnnotationPresent(Log.class)) {
            System.out.println("executed method: calculation, param: " + Arrays.toString(args));
        }
        return method.invoke(logObject, args);
    }
}
