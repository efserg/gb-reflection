package ru.gb;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Получение информации о классе
public class App02 {
    public static void main(String[] args) {
        final App02 app = new App02();

        app.showClassInfo(String.class);
    }

    public void showClassInfo(final Class<?> clazz) {
        try {
            showModifiersInfo(clazz);
            showAncestorsInfo(clazz);
            showFieldsInfo(Dog.class, new Dog());
            showMethodsInfo(clazz);
            showConstructorsInfo(clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showConstructorsInfo(final Class<?> clazz) {
        // Это очень похоже на получение методов
        for (Constructor<?> constructor : clazz.getConstructors()) { // getDeclaredConstructors() для непубличных методов
            final int modifiers = constructor.getModifiers();
            System.out.print(Modifier.isPublic(modifiers) ? "public " : "");
            System.out.print(Modifier.isProtected(modifiers) ? "protected " : "");
            System.out.print(Modifier.isPrivate(modifiers) ? "private " : "");
            System.out.print(constructor.getName() + "(");
            for (Parameter parameter : constructor.getParameters()) {
                System.out.printf("%s %s, ", parameter.getType().getName(), parameter.getName());
            }
            System.out.println(")");
            // для нестатических внутренних классов в конструктор неявно передается инстанс внешнего класса
        }
    }

    private void showMethodsInfo(final Class<?> clazz) {
        for (Method method : clazz.getMethods()) { // getDeclaredMethods() для непубличных методов
            final int modifiers = method.getModifiers();
            System.out.print(Modifier.isPublic(modifiers) ? "public " : "");
            System.out.print(Modifier.isProtected(modifiers) ? "protected " : "");
            System.out.print(Modifier.isPrivate(modifiers) ? "private " : "");
            System.out.print(method.getReturnType() + " ");
            System.out.print(method.getName() + "(");
            for (Parameter parameter : method.getParameters()) {
                System.out.printf("%s %s, ", parameter.getType().getName(), parameter.getName());
            }
            System.out.println(")");
            // ну или так
            final String parameters = Arrays.stream(method.getParameters())
                    .map(p -> p.getType().getName() + " " + p.getName())
                    .collect(Collectors.joining(", ", "(", ")"));
            System.out.println(parameters);

            // имена параметров не сохраняются после компиляции, и отображаются как arg0, arg1...

        }
    }

    // информация о полях класса
    private void showFieldsInfo(final Class<?> clazz, final Object o) throws Exception {
        Field[] publicFields = clazz.getFields();
        System.out.println("Публичные поля");
        for (Field field : publicFields) {
            // можно получить имя, тип, значение поля
            final String fieldType = field.getType().getName();
            final String fieldName = field.getName();
            final Object fieldValue = field.get(o);
            // и его модификаторы
            final int modifiers = field.getModifiers();
            final String finalMod = Modifier.isFinal(modifiers) ? "final " : "";
            final String transientMod = Modifier.isTransient(modifiers) ? "transient " : "";
            final String volatileMod = Modifier.isVolatile(modifiers) ? "volatile " : "";

            System.out.printf("  %s%s%s%s %s = %s%n", finalMod, transientMod, volatileMod, fieldType, fieldName, fieldValue);
        }

        System.out.println("Публичные и приватные поля");
        for (Field field : clazz.getDeclaredFields()) {
            System.out.printf("  %s %s", field.getType().getName(), field.getName());
            // field.get(o) - если попробовать сделать так, то для приватных полей будет брошено
            // исключение IllegalAccessException
            // для получения доступа надо сначала сделать так
            field.setAccessible(true);
            // теперь все должно быть ок
            System.out.println(" = " + field.get(o));
        }
        // интересно, что внутренние (нестатические) классы имеют ссылку на экземпляр
        // наружного класса App02

        // можно также установить значение поля:
        final Field fieldName = clazz.getField("owner"); // получение поля по имени. Для
        // приватных полей надо использовать
        // метод getDeclaredField, а потом
        // делать его доступны с помощью setAccessible
        fieldName.set(o, "Василий Пупкин");
        System.out.println(o);
    }

    // модификаторы класса
    private void showAncestorsInfo(final Class<?> clazz) {
        final Class<?> superclass = clazz.getSuperclass();
        System.out.println("superclass.getName() = " + superclass.getName());
        final Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> i : interfaces) {
            System.out.println("i.getName() = " + i.getName());
        }
    }

    // Предки класса
    private void showModifiersInfo(final Class<?> clazz) {
        int modifiers = clazz.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            System.out.println(clazz.getSimpleName() + " - interface");
        }
        if (Modifier.isPublic(modifiers)) {
            System.out.println(clazz.getSimpleName() + " - public");
        }
        if (Modifier.isAbstract(modifiers)) {
            System.out.println(clazz.getSimpleName() + " - abstract");
        }
        if (Modifier.isFinal(modifiers)) {
            System.out.println(clazz.getSimpleName() + " - final");
        }
    }

    class Dog {
        private String name;
        private String color;
        protected int age;
        public volatile transient String owner = "Иван Иванов";

        public Dog() {
        }

        public Dog(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Dog{");
            sb.append("name='").append(name).append('\'');
            sb.append(", color='").append(color).append('\'');
            sb.append(", age=").append(age);
            sb.append(", owner='").append(owner).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
