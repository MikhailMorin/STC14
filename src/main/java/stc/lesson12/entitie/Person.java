package stc.lesson12.entitie;

import lombok.*;

import java.util.*;

/**
 * Класс, описывающий POJO
 * для представления информации о персоне.
 */
@Data
public class Person {
    private int id;
    private String name;
    private long birthDate;

    public Person(int id, String name, long birthDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + new Date(birthDate )+
                '}';
    }
}
