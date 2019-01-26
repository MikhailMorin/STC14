package stc.lesson7;

import java.io.*;
import java.nio.file.*;

/**
 * Собственный загрузчик классов.
 */
class MyClassLoader extends ClassLoader {
    String dir;
    public MyClassLoader(String dir) {
        this.dir = dir;
    }

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
