package ru.otus.homework.handlers;

import ru.otus.homework.annotations.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public class LogHandler implements InvocationHandler {
    private final Object logObject;
    private final Method[] cachedDeclaredMethods;

    public LogHandler(Object logObject) {
        this.logObject = logObject;
        this.cachedDeclaredMethods = logObject.getClass().getDeclaredMethods();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?>[] params = method.getParameterTypes();
        Method proxiedMethod = findProxiedMethod(method.getName(), params);

        if (proxiedMethod.isAnnotationPresent(Log.class)) {
            System.out.println("executed method: calculation, param: " + Arrays.toString(args));
        }
        return method.invoke(logObject, args);
    }

    private Method findProxiedMethod(String methodName, Class<?>[] params) {
        Method res = null;
        for (Method method : cachedDeclaredMethods) {
            if (method.getName().equals(methodName) && arrayContentsEq(method.getParameterTypes(), params)) {
                res = method;
            }
        }
        return res;
    }

    private boolean arrayContentsEq(Object[] a1, Object[] a2) {
        if (a1 == null) {
            return a2 == null || a2.length == 0;
        }

        if (a2 == null) {
            return a1.length == 0;
        }

        if (a1.length != a2.length) {
            return false;
        }

        for (int i = 0; i < a1.length; i++) {
            if (a1[i] != a2[i]) {
                return false;
            }
        }

        return true;
    }
}
