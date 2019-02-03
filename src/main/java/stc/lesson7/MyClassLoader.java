package stc.lesson7;

import java.io.*;
import java.nio.file.*;

/**
 * Собственный загрузчик классов.
 */
class MyClassLoader extends ClassLoader {
    // Директория хранения *.java и *.class файлов.
    final private String dir;

    public MyClassLoader(String dir) {
        this.dir = dir;
    }

    /**
     * Загрузка класса по указанному имени. В случае, если имя - SomeClass,
     * осуществляется загрузка класса из директории {@code MyClassLoader#dir}.
     * В противном случае загрузка делегируется родительскому классу.
     * @param name - имя загружаемого класса.
     * @return - найденый класс.
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if("SomeClass".equals(name)){
            try {
                byte[] data;
                data = Files.readAllBytes(Paths.get(dir + "SomeClass.class"));
                return defineClass(name, data, 0, data.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }
}
