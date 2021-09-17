package ru.gb;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// Аннотации
public class App06 {
    // Аннотации - специальные объекты в Java которые позволяют добавить некоторую дополнительную
    // информацию (для компилятора, программита, JVM и т.д.) о классах, методах, полях, параметрах
    // и т.д. Примеры аннотаций, которые должным быть вам знакомы @Override, @Deprecated, @Test

    // Создав аннотацию, как описано ниже, можно добавить ее к нужным элементам
    @MyOtherAnnotation(name = "annotation parameter", additionalParameters = {1, 2, 3})
    public void method() {
        // метод с аннотацией
    }
}

// создание аннотации
@interface MyAnnotation { // interface со знаком "@"
    // аннотация может содержать параметры
    public String name();

    public int value() default 0; // значение параметра по умолчанию
}

// Создавая аннотации, можно указать некоторые дополнительные параметры
// @Retention - управляет видимостью аннотации
// @Retention(RetentionPolicy.RUNTIME) - аннотация будет доступна в байт-коде и ее можно получить
//                                       через механизм рефлексии
//@Retention(RetentionPolicy.SOURCE) - аннотация будет видна только на этапе компиляции
//@Retention(RetentionPolicy.CLASS) - аннотация будет доступна в байт-коде но не видна через рефлексию
@Retention(RetentionPolicy.RUNTIME)

// @Target - указывает, к каким объектам аннотация может быть применена
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@interface MyOtherAnnotation {
    String name();

    int[] additionalParameters();
}