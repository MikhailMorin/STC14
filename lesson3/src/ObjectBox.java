import java.util.Set;
import java.util.TreeSet;

/**
 * Класс, способный хранить данные типа Object.
 * Данные кранятся в виде коллекции {@code TreeSet}
 * Имеются методы, позволяющие добавлять и удалять данные из хранилища.
 */
public class ObjectBox {
    protected Set<Object> set = new TreeSet<>();

    /**
     * Метод, добавляющий объект в коллекцию.
     * @param obj - добавляемый элемент.
     */
    public void addObject(Object obj) {
        set.add(obj);
    }

    /**
     * Удаляет элемент из коллекции (если такой элемент есть).
     * @param i - удаляемый элемент.
     * @return результат удаления
     * (true - элемент найден и удален, false - элемент отсутствует в коллекции).
     */
    public boolean removeIfContains(Object i) {
        return set.remove(i);
    }

    @Override
    public String toString() {
        return set.toString();
    }
}
