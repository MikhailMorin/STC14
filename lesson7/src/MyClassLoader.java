import java.io.*;
import java.nio.file.*;

/**
 * Собственный загрузчик классов.
 */
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if("SomeClass".equals(name)){
            byte[] data;
            try {
                data = Files.readAllBytes(Paths.get("./src/SomeClass.class"));
                return defineClass(name, data, 0, data.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }
}
