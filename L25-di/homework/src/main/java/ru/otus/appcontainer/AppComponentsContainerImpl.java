package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    private final Object initialConfigInstance;
    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            this.initialConfigInstance = initialConfigClass.getConstructor().newInstance();
        } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException | InstantiationException e) {
            throw new RuntimeException(e);
        }
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        var methods =  Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(m -> m.getAnnotation(AppComponent.class).order()))
                .toList();
        for (Method method: methods) {
            var annotation = method.getAnnotation(AppComponent.class);
            if (appComponentsByName.containsKey(annotation.name())) {
                throw new RuntimeException(String.format("The component %s has been already presented in the context", annotation.name()));
            }
            List<Object> methodParameters = new ArrayList<>();
            for (Class<?> parameter: method.getParameterTypes()) {
                var component = getAppComponent(parameter);
                methodParameters.add(component);
            }
            try {
                Object component = method.invoke(initialConfigInstance, methodParameters.toArray());
                appComponentsByName.put(annotation.name(), component);
                appComponents.add(component);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
         var components = appComponents
                .stream()
                .filter(c -> componentClass.isAssignableFrom(c.getClass())).toList();
        if ((long) components.size() > 1) {
            throw new RuntimeException("There are doubles");
        }
        if (components.isEmpty()) {
            throw new NoSuchElementException("No value present");
        }

        return (C) components.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (!appComponentsByName.containsKey(componentName)) {
            throw new NoSuchElementException("No value present");
        }
        return (C) appComponentsByName.get(componentName);
    }
}
