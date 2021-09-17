package ru.gb;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyFactory implements InvocationHandler {

    private final Object target;

    public JdkProxyFactory(Object target) {
        this.target = target;
    }

    public Object createProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    // Вызов методов проксируемого объекта попадает сюда, и в аргумента method содержится
    // информация о вызываемом методе, args - аргументы этого метода
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Логирование времени выполнения методов, помеченных
        // аннотацией @PerformanceTracing
        boolean isPerformanceTracing = method.isAnnotationPresent(TestPerformance.class);

        long start = System.currentTimeMillis();
        if (isPerformanceTracing) {
            System.out.println("Start method '" + method.getName() + "'");
        }
        Object invoke = method.invoke(target, args);
        if (isPerformanceTracing) {
            System.out.println("End method '" + method.getName() + "': " + (System.currentTimeMillis() - start) + " ms");
        }
        return invoke;
    }
}