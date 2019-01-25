package stc.lesson7;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * В методе {@code main()} данного класса осуществялется загрузка
 * сгенерированного класса средствами собственного загрузчика классов,
 * и запрос на выполнение метода {@code doWork} загруженного класса.
 */
public class Main {
    static final String DIR_OUT = "target/classes/stc/lesson7/";
    public static void main(String[] args) throws IOException,
            ClassNotFoundException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        FileCreator.createFromCmd();

        MyClassLoader mcl = new MyClassLoader();
        Class clazz = mcl.loadClass("stc.lesson7.SomeClass");
        clazz.getMethod("doWork").invoke(clazz.getConstructor().newInstance(), (Object[])null);
    }
}
