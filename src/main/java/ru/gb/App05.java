package ru.gb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

// пример использования рефлексии - создание объекта из файла
public class App05 {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        final Class<Book> bookClass = Book.class;
        final Book book = bookClass.newInstance();
        try (BufferedReader br = new BufferedReader(new FileReader("book.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] info = line.split(":");
                final String fieldName = info[0].trim();
                final String fieldValue = info[1].trim();
                for (Method method : bookClass.getMethods()) {
                    if (method.getName().equalsIgnoreCase("set" + fieldName)) {
                        final Parameter setterParameter = method.getParameters()[0];
                        final Object castValue;
                        if (setterParameter.getType().equals(int.class)) {
                            castValue = Integer.parseInt(fieldValue);
                        } else {
                            castValue = fieldValue;
                        }
                        method.invoke(book, castValue);
                    }
                }
            }
        } catch (IOException | InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("book = " + book);

        final App05 app = new App05();
        final Cat cat = app.fileToObject("cat.txt", Cat.class);
        System.out.println("cat = " + cat);
    }

    // Сделаем универсальный метод, который сможет создать объект любого класса по его
    // описанию в файле
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
                    if (method.getName().equalsIgnoreCase("set" + fieldName)) {
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

class Book {
    private String title;
    private String author;
    private int year;
    private String publisher;

    public Book() {
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setYear(final int year) {
        this.year = year;
    }

    public void setPublisher(final String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Book{");
        sb.append("title='").append(title).append('\'');
        sb.append(", author='").append(author).append('\'');
        sb.append(", year=").append(year);
        sb.append(", publisher='").append(publisher).append('\'');
        sb.append('}');
        return sb.toString();
    }
}