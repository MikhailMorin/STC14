package stc.lesson10;

import java.util.*;

/**
 * Демонстрация утечки памяти в Java.
 * Объекты создаются и периодически частично удаляются, чтобы GC имел возможность очищать часть памяти.
 * Программа завершается с ошибкой OutOfMemoryError c пометкой Java Heap Space.
 */
public class Main {
    public static void main(String[] args) {
        Main.delayForProfilerStart();
        Main.makeOOM();
    }

    /**
     * Создание ситуации OutOfMemory : Java Heap Space.
     * Происходит заполнение коллекции строками, увеличивающимися на каждой итерации в размере.
     * Периодически происходит удаление последнего элемента (максимального по длине).
     */
    static void makeOOM(){
        final Random r = new Random();
        final List<String> l = new LinkedList<>();
        String s = "1";
        while (true) {
            s += "1";
            l.add(s);

            if (r.nextInt(10) > 0) {
                l.remove(l.size() - 1);
            }
        }
    }

    /**
     * Искуственно не позволяет программе начать исполнение (пока не будет нажат "Enter"),
     * для возможности запуска утилиты профилирования.
     */
    static void delayForProfilerStart(){
        System.out.println("press Enter key to start");
        new Scanner(System.in).nextLine();
        System.out.println("GO!");
    }
}
