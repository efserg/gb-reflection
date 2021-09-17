package ru.gb;

import java.util.Random;

// Посмотрим, как аннотацию можно применять на практике.
// Создадим аннотацию @TestPerformance и попробуем добавить функционал замера времени выполнения
// методов
public class App08 {

    public static void main(String[] args) {
        final TestClass testClass = new TestClass();
        // Java "из коробки" предоставляет возможность создания объекта-обертки (proxy).
        // Вызовы proxy-класса оборачивают методы основного класса
        final TestInterface proxy = (TestInterface) new JdkProxyFactory(testClass).createProxy();
        System.out.println("proxy.testMethod() = " + proxy.testMethod());
    }
}

interface TestInterface {
    @TestPerformance
    int testMethod();
}

class TestClass implements TestInterface {
    public int testMethod() {
        final int millis = new Random().nextInt(2000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return millis;
    }
}