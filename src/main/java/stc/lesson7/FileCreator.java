package stc.lesson7;

import javax.tools.*;
import java.io.*;
import java.nio.file.*;

/**
 * Класс, содержащий функциональный метод {@code createFromCmd()},
 * генерирующий класс по данным, введенным с консоли.
 */
public class FileCreator {
    /**
     * Генерация файла по данным, введенным с консоли.
     * @return Название сгенерированного *.class файла
     * @throws IOException
     */
    public static String createFromCmd() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder code = new StringBuilder();

        /*
        Ввод с консоли кода метода doWork(), содержащегося в
        классе, имплементирующем интерфейс Worker;
         */
        String className = "SomeClass";
        code.append("package stc.lesson7;\n");
        code.append("public class SomeClass implements Worker {\n ").
                append("\t@Override\n").
                append("\tpublic void doWork(){\n");
        while (true){
            String s = reader.readLine();
            code.append("\t\t").append(s);
            if("".equals(s)) {
                code.append("\n");
                break;
            }
        }
        code.append("\t}\n}\n");

        /*
        Генерация *.java и *.class файлов.
         */
        String filename = Main.DIR_OUT + "SomeClass.java";
        try {
            // Сохраняем исходный код в файл
            Files.write(Paths.get(filename), code.toString().getBytes());
            // Получаем компилятор
            JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
            // Указываем имя .java файла
            String[] javacOpts = {filename};
            javac.run(null, null, null, javacOpts);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filename;
    }
}
