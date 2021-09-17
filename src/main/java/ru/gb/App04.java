package ru.gb;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// вызов методов класса через рефлексию
public class App04 {
    public static void main(String[] args) {
        Cat cat = new Cat("Barsik", "Black", 3);
        try {
            Method mMeow = Cat.class.getDeclaredMethod("meow", int.class);
            final Object result = mMeow.invoke(cat, 5);
            System.out.println("result = " + result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}

