package stc.lesson4;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * В классе осущетвляется запрос на генерацию списка слов
 * у {@code WordListBuilder}, а также генерация необходимого
 * количества файлов с текстом состоящим из слов списка
 * {@code wordList}.
 *
 * @author Михаил Морин
 */
public class Main {
    /**
     * В методе осуществляется запрос на генерацию словаря и файлов.
     */
    public static void main(String[] args) {
        Set<String> wordList = WordListBuilder.makeListFromUrl("https://habr.com/ru/post/321344/");
        String[] wordsArray = new String[wordList.size()];
        wordList.toArray(wordsArray);

        genFiles("./target/out", 5, 30, wordsArray, 30);
    }

    /**
     * Метод генерации файлов.
     *
     * @param path        - каталог, где будут храниться сгенерированные файлы
     * @param n           - количество файлов
     * @param size        - размер файлов
     * @param words       - словарь
     * @param probability - вероятность вхождения слова в следующее предложение
     */
    static void genFiles(String path, int n, int size, String[] words, int probability) {
        try {
            Files.createDirectories(Paths.get(path));

            for (int i = 0; i < n; i++) {
                File f = new File(path + "/file" + i + ".txt");

                try (FileWriter file = new FileWriter(f);
                     BufferedWriter bw = new BufferedWriter(file);) {
                    TextBuilder tb = new TextBuilder();
                    tb.setParams(words, size, probability);
                    tb.makeText();
                    bw.write(tb.getText());
                } catch (IOException e) {
                    System.out.println("IOException for file" + i + ".txt");
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            System.out.println("Не удалось создать директорию " + path);
            e.printStackTrace();
        }


    }
}
