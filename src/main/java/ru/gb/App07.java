package ru.gb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

// Посмотрим, как аннотацию можно применять на практике.
// Попробуем чуть расширить функционал метода, с помощью которого мы создавали объект из файла
// Предположим, что поля класса не всегда соответствуют названиям полей в файле
// Добавим специальную аннотацию для методов - @FileFieldName, которая будет содержать название
// поля в файле
public class App07 {

    public static void main(String[] args) {
        final App07 app = new App07();
        System.out.println(app.fileToObject("car.txt", Car.class));
    }

    // Добавим в наш метод функциональность, позволяющую проверять сеттер на наличие нужной
    // аннотации
    public <T> T fileToObject(String fileName, Class<T> clazz) {
        T instance;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            instance = clazz.newInstance();
            while ((line = br.readLine()) != null) {
                final String[] info = line.split(":");
                final String fieldName = info[0].trim();
                final String fieldValue = info[1].trim();
                for (Method method : clazz.getMethods()) {
                    final FileFieldName fileFieldNameAnnotation = method.getAnnotation(FileFieldName.class);
                    if (fileFieldNameAnnotation != null && fileFieldNameAnnotation.value().equalsIgnoreCase(fieldName) || method.getName().equalsIgnoreCase("set" + fieldName)) {
                        final Parameter setterParameter = method.getParameters()[0];
                        final Object castValue;
                        if (setterParameter.getType().equals(int.class)) {
                            castValue = Integer.parseInt(fieldValue);
                        } else {
                            castValue = fieldValue;
                        }
                        method.invoke(instance, castValue);
                        break;
                    }
                }
            }
        } catch (IllegalAccessException | IOException | InstantiationException | InvocationTargetException e) {
            throw new RuntimeException("Error restore object", e);
        }
        return instance;
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface FileFieldName {
    String value() default "";
}

class Car {
    private String model;
    private Integer power;
    private Integer fuel;
    private Integer maxSpeed;

    public Car() {
    }

    public void setModel(final String model) {
        this.model = model;
    }

    @FileFieldName("engine_power")
    public void setPower(final int power) {
        this.power = power;
    }

    @FileFieldName("fuel_capacity")
    public void setFuel(final int fuel) {
        this.fuel = fuel;
    }

    @FileFieldName("max_speed")
    public void setMaxSpeed(final int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Car{");
        sb.append("model='").append(model).append('\'');
        sb.append(", power=").append(power);
        sb.append(", fuel=").append(fuel);
        sb.append(", maxSpeed=").append(maxSpeed);
        sb.append('}');
        return sb.toString();
    }
}
