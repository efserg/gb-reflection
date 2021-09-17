package ru.gb;

public class App01 {
    public static void main(String[] args) {
        // Class - это специальный класс Java, содержащий информацию о классе (конструкторы, методы,
        // поля, предки, аннотации и т.д.

        // Для получения объекта типа Class можно:
        //   1) вызвать метод getClass() у объекта класса (этот метод определен в классе Object)
        final String abc = new String("abc");
        final Class<? extends String> abcClass = abc.getClass();

        //   2) вызвать метод специальный объекта class у класса (выглядит как поле класса,
        //   но таковым не является):
        final Class<String> stringClass = String.class;

        //   3) использовать метод classForName() у класса Class
        try {
            final Class<?> classForName = Class.forName("java.lang.String");
            System.out.println("classForName.getName() = " + classForName.getName());
            System.out.println("classForName.getSimpleName() = " + classForName.getSimpleName());
            System.out.println("classForName.getCanonicalName() = " + classForName.getCanonicalName());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // объект Class можно получить у любого объекта Java
        final Class<Integer> iClass = int.class;
        final Class<int[]> iiClass = int[].class;
        final Class<int[][]> iiiClass = int[][].class;

        System.out.println("iClass = " + iClass.getName() + ", " + iClass.getSimpleName() + ", " + iClass.getCanonicalName());
        System.out.println("iiClass = " + iiClass.getName() + ", " + iiClass.getSimpleName() + ", " + iiClass.getCanonicalName());
        System.out.println("iiiClass = " + iiiClass.getName() + ", " + iiiClass.getSimpleName() + ", " + iiiClass.getCanonicalName());

        final Class<Integer[]> iIClass = Integer[].class;
        System.out.println("iIClass = " + iIClass.getName() + ", " + iIClass.getSimpleName() + ", " + iIClass.getCanonicalName());

        try {
            final Class<?> aClass = Class.forName("[Ljava.lang.Integer;");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
