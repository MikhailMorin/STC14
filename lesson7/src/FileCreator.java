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
        code.append("public class SomeClass implements Worker {\n "
                + "\t@Override\n"
                + "\tpublic void doWork(){\n");
        while (true){
            String s = reader.readLine();
            code.append("\t\t" + s);
            if("".equals(s)) {
                code.append("\n");
                break;
            }
        }
        code.append("\t}\n}\n");

        /*
        Генерация *.java и *.class файлов.
         */
        String filename = "";
        try {
            filename = "./src/SomeClass.java";
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
