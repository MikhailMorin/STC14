package stc.lesson7;

import javax.tools.*;
import java.io.*;
import java.nio.file.*;

/**
 * Класс, содержащий функциональный метод {@code createFromCmd()},
 * генерирующий класс по данным, введенным с консоли.
 */
class FileCreator {
    /**
     * Генерация файла по данным, введенным с консоли.
     * @return Название сгенерированного *.class файла
     * @throws IOException
     */
    static String createFromCmd(String dir) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String code = writeDoWork(reader);
            String filename = dir + "SomeClass.java";
            classGen(filename, code);

            return filename;
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("Ошибка ввода/вывода генерации класса", e);
        }
    }

    /**
     * Ввод с консоли кода метода doWork(), содержащегося в
     * классе, имплементирующем интерфейс Worker;
     * @param reader - поток для ввода данных
     * @return - исходный код класса в виде строки.
     * @throws IOException
     */
    private static String writeDoWork(BufferedReader reader) throws IOException {
        StringBuilder code = new StringBuilder();
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
        return code.toString();
    }

    /**
     * Генерация *.java и *.class файлов.
     * @param filename - имя генерируемого файла
     * @param code - исходный код
     * @throws IOException
     */
    private static void classGen(String filename, String code) throws IOException {
        // Сохраняем исходный код в файл
        Files.write(Paths.get(filename), code.toString().getBytes());
        // Получаем компилятор
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        // Указываем имя .java файла
        String[] javacOpts = {filename};
        javac.run(null, null, null, javacOpts);
    }
}
