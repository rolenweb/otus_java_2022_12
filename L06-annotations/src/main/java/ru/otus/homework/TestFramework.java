package ru.otus.homework;

import ru.otus.homework.annotations.After;
import ru.otus.homework.annotations.Before;
import ru.otus.homework.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestFramework {
    public static void main(String[] args) {
        TestFramework testFramework = new TestFramework();
        Statistic statistic = new Statistic();
        for (String arg : args) {
            testFramework.runTestClass(arg, statistic);
        }
        System.out.println(statistic);
        for (String error : statistic.getErrors()) {
            System.err.println(error);
        }
    }

    private void runTestClass(String className, Statistic statistic) {
        try {
            Class<?> clazz = findClass(getClass().getPackage().getName(), className);
            List<Method> beforeMethods = findMethodsByAnnotation(clazz.getDeclaredMethods(), Before.class);
            List<Method> afterMethods = findMethodsByAnnotation(clazz.getDeclaredMethods(), After.class);
            List<Method> testMethods = findMethodsByAnnotation(clazz.getDeclaredMethods(), Test.class);
            for (Method method : testMethods) {
                runTestMethod(clazz, beforeMethods, method, afterMethods, statistic);
            }
        } catch (ClassNotFoundException classNotFoundException) {
            statistic.addError("Class " + className + " not found");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Class<?> findClass(String packageName, String className) throws ClassNotFoundException {
        return Class.forName(packageName + "." + className);
    }

    private List<Method> findMethodsByAnnotation(Method[] methods, Class<? extends Annotation> annotationClass) {
        return Arrays.stream(methods).filter(method -> method.isAnnotationPresent(annotationClass)).collect(Collectors.toList());
    }

    private void runTestMethod(Class<?> clazz, List<Method> beforeMethods, Method method, List<Method> afterMethods, Statistic statistic) {
        try {
            Object object = clazz.getDeclaredConstructor().newInstance();
            try {
                runBeforeMethods(object, beforeMethods);
                runTestedMethod(object, method, statistic);
            } finally {
                runAfterMethods(object, afterMethods, statistic);
            }
        } catch (Exception e) {
            statistic.addFailed(1);
            statistic.addError(e.getCause().toString());
        }
    }

    private void runBeforeMethods(Object object, List<Method> methods) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(object);
        }
    }

    private void runAfterMethods(Object object, List<Method> methods, Statistic statistic) {
        try {
            for (Method method : methods) {
                method.invoke(object);
            }
        } catch (Exception e) {
            statistic.addError(e.getCause().toString());
        }
    }

    private void runTestedMethod(Object object, Method method, Statistic statistic) {
        try {
            method.invoke(object);
            statistic.addSuccess(1);
        } catch (Exception e) {
            statistic.addError(e.getCause().toString());
            statistic.addFailed(1);
        }
    }
}

