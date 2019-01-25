package stc.lesson3;

import java.util.*;

/**
 * Отсортированная коллекция, состоящая из уникальных элементов.
 * Тип элементов коллекции - наследники абстрактного класса Number.
 * Имеется возможность добавлять и удалять элементы из коллекции.
 * Имеется возможность получить сумму всех элементов коллекции,
 * а также коллецию, состоящую из элементов, делёных на конкретное значение.
 *
 * @author Михаил Морин
 * @see LinkedList
 */
class MathBox<T extends Number> extends ObjectBox {

    /**
     * Конструктор, принимающий в качестве аргумента массив.
     * Т.к. тип используемой коллекции  {@code TreeSet},
     * данные будут уникаьными и отсортированными.
     *
     * @param arr - входной массив
     */
    MathBox(T[] arr) {
        Collections.addAll(set, arr);
    }

    /**
     * Метод, суммирующий все элементы коллекции {@code ObjectBox#list}.
     *
     * @return сумма всех элементов коллекции
     */
    T summator() {
        Iterator<Object> iter = set.iterator();
        Number sum;

        if (iter.hasNext()) {
            sum = (Number) iter.next();
        } else {
            return null;
        }

        while (iter.hasNext()) {
            Number n = (Number) iter.next();
            if (sum instanceof Byte) {
                sum = (Byte) sum + n.byteValue();
            } else if (sum instanceof Short) {
                sum = (Short) sum + n.shortValue();
            } else if (sum instanceof Integer) {
                sum = (Integer) sum + n.intValue();
            } else if (sum instanceof Long) {
                sum = (Long) sum + n.longValue();
            } else if (sum instanceof Float) {
                sum = (Float) sum + n.floatValue();
            } else if (sum instanceof Double) {
                sum = (Double) sum + n.doubleValue();
            }
        }

        return (T) sum;
    }

    /**
     * Метод ыполняет поочередное деление всех хранящихся в объекте
     * элементов на делитель, переданны в качестве аргумента.
     *
     * @param s - делитель
     * @return Коллекция, содержащая результат деления элементов
     * исходной коллекции на делитель.
     */
    List<T> splitter(int s) {
        List<T> res = new LinkedList<>();
        for (Object e : set) {
            if (e instanceof Byte) {
                Byte a = (byte) ((Byte) e / s);
                res.add((T) a);
            } else if (e instanceof Short) {
                Short a = (short) ((Short) e / s);
                res.add((T) a);
            } else if (e instanceof Integer) {
                Integer a = (Integer) e / s;
                res.add((T) a);
            } else if (e instanceof Long) {
                Long a = (Long) e / s;
                res.add((T) a);
            } else if (e instanceof Float) {
                Float a = (Float) e / s;
                res.add((T) a);
            } else if (e instanceof Double) {
                Double a = (Double) e / s;
                res.add((T) a);
            }
        }
        return res;
    }

    /**
     * Метод, добавляющий объект в коллекцию.
     * Объект д.б. наследником типа {@code Number}.
     * При несоответствии типов возникает исключение {@code ClassCastException}
     *
     * @param obj - добавляемый объект.
     */
    @Override
    void addObject(Object obj) {
        if (obj instanceof Number) {
            if (set.contains(obj)) {
                return;
            }
            set.add(obj);
        } else {
            System.out.println("Попытка добавить объект не соответствующий типу Number");
            throw new ClassCastException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MathBox mathBox = (MathBox) o;
        return Objects.equals(set, mathBox.set);
    }

    @Override
    public int hashCode() {
        return Objects.hash(set);
    }

    @Override
    public String toString() {
        return "MathBox" + set;
    }
}