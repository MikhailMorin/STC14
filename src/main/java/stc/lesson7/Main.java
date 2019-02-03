package stc.lesson7;

import java.io.IOException;

/**
 * В методе {@code main()} данного класса осуществялется загрузка
 * сгенерированного класса средствами собственного загрузчика классов,
 * и запрос на выполнение метода {@code doWork} загруженного класса.
 */
public class Main {
    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        final String DIR_OUT = "target/classes/stc/lesson7/";
        FileCreator.createFromCmd(DIR_OUT);

        MyClassLoader mcl = new MyClassLoader(DIR_OUT);
        Class clazz = mcl.loadClass("stc.lesson7.SomeClass");
        clazz.getMethod("doWork").invoke(clazz.getConstructor().newInstance(), (Object[]) null);
    }
}
