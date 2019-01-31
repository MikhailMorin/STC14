package stc.lesson12.entitie;

import lombok.*;

import java.util.Objects;

/**
 * Класс, описывающий POJO
 * для представления информации об учебной дисциплине.
 */
@Data
public class Subject {
    private int id;
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
