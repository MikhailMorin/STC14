package stc.lesson4;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.URL;

/**
 * Класс генерации списка слов {@code wordList}.
 * @author Михаил Морин
 */
public class WordListBuilder {
    private Set<String> wordList = new HashSet<>();

    /**
     * Метод генерирует словарь из содержимого web-страницы.
     *
     * @param sourceWords - URL адрес, откуда будет производится заполнение списка слов.
     * @return - список слов.
     */
    public Set<String> makeListFromUrl(String sourceWords) {

        try (InputStream input = new URL(sourceWords).openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(input));) {
            Pattern p = Pattern.compile("[а-яА-Я]{1,15}");

            while (br.ready()) {
                String s = br.readLine();
                Matcher m = p.matcher(s);

                while (m.find()) {
                    wordList.add(m.group().toLowerCase());
                    if (wordList.size() == 1000)
                        return wordList;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    /**
     * Метод, позволяющий получить сгенерированный словарь.
     *
     * @return список слов
     */
    public Set<String> getWordList() {
        return wordList;
    }
}
