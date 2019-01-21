import java.io.*;
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
        WordListBuilder wordBuilder = new WordListBuilder();
        wordBuilder.makeListFromUrl("https://habr.com/ru/post/321344/");
        Set<String> wordList = wordBuilder.getWordList();

        String[] wordsArray = new String[wordList.size()];
        wordList.toArray(wordsArray);

        genFiles("C:\\path", 5, 30, wordsArray, 2);
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
    public static void genFiles(String path, int n, int size, String[] words, int probability) {
        for (int i = 0; i < n; i++) {
            File f = new File(path + "\\file" + i + ".txt");

            try (FileWriter file = new FileWriter(f);
                 BufferedWriter bw = new BufferedWriter(file);) {

                TextBuilder tb = new TextBuilder(words, size, probability);
                tb.makeText();
                bw.write(tb.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
