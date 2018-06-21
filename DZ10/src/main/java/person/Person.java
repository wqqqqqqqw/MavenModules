package person;

import com.sun.istack.internal.NotNull;

public class Person {

    private String name;
    private int age;

    public Person(@NotNull String name, int age) {
        this.name = name;
        this.age = age > 0 ? age : 0;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return new String(
                 " Имя: "
                         + getName() + "; " +
                        "возраст: "
                         + getAge() + "\n");
    }
}