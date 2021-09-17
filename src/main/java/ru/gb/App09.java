package ru.gb;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

// Еще одно важное применение для аннотаций - разнообразные библиотеки и фреймворки
// Например, интересная библиотека Lombok, позволяющая уменьшить количество кода.
// Для использования библиотеки надо добавить соответствующую зависимость в pom.xml, а для
// поддержки в Intellij IDEA - добавить плагин lombok и включить Annotation Processing (легко
// ищется в настройках File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors)
public class App09 {
    public static void main(String[] args) {
        final Person person = Person.builder()
                .firstName("Василий")
                .lastName("Пупкин")
                .birthday(LocalDate.of(1988, 10, 21))
                .build();
        person.setPosition("Boss");
        System.out.println(person);
    }
}

// во время компиляции в класс будут добавлены конструктор, билдер, методы toString,
// equals и hashCode, геттеры для всех полей и сеттер для поля position
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Getter
@Log4j2
class Person {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    @Setter
    private String position;
    private Person manager;

    void m() {
        log.error("..."); // магическим образом появляется поле log
    }
}

@Data // одна эта аннотация добавит в класс геттеры, сеттеры, конструктор, методы toString, equals и hashCode
class Employee {
    private String firstName;
    private String lastName;
    private LocalDate birthday;
}