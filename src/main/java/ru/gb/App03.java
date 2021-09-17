package ru.gb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

// Создание инстанса класса через рефлексию
public class App03 {
    public static void main(String[] args) {
        try {
            Class<Cat> catClass = Cat.class;
            // при наличии конструктора без параметров (по умолчанию) можно создать новый
            // инстанс класса вызовом метода newInstance
            Cat cat1 = catClass.newInstance();
            System.out.println("cat1 = " + cat1);

            // зная параметры конструктора, можно создать объект через конструктор
            Constructor<Cat> catConstructor = Cat.class.getConstructor(String.class, String.class, int.class);
            Cat cat2 = catConstructor.newInstance("Murzik", "Black", 3);
            System.out.println("cat2 = " + cat2);

            // даже если конструктор приватный, можно создать класс через него
            Constructor<Cat> privateConstructor = Cat.class.getDeclaredConstructor(String.class);
            privateConstructor.setAccessible(true);
            Cat cat3 = privateConstructor.newInstance("Murzik");
            System.out.println("cat3 = " + cat3);

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

class Cat {
    public String name;
    public String color;
    public int age;

    public Cat(final String name, final String color, final int age) {
        this.name = name;
        this.color = color;
        this.age = age;
    }

    public Cat() {
    }

    private Cat(final String name) {
        this.name = name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String meow(int dB) {
        return name + ": meow - " + dB + " dB";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cat{");
        sb.append("name='").append(name).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}
