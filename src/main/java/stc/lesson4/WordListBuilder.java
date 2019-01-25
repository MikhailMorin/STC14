package stc.lesson4;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.net.URL;

/**
 * Класс генерации списка слов {@code wordList}.
 *
 * @author Михаил Морин
 */
class WordListBuilder {
    private static final int MAX_WORD_SIZE = 15;
    private static final int MAX_WORDLIST_SIZE = 1000;

    private WordListBuilder() {
    }

    /**
     * Метод генерирует словарь из содержимого web-страницы.
     *
     * @param sourceWords - URL адрес, откуда будет производится заполнение списка слов.
     * @return - список слов.
     */
    static Set<String> makeListFromUrl(String sourceWords) {
        Set<String> wordList = new HashSet<>();

        try (InputStream input = new URL(sourceWords).openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(input));) {
            Pattern p = Pattern.compile("[а-яА-Я]{1," + MAX_WORD_SIZE + "}");

            while (br.ready()) {
                String s = br.readLine();
                Matcher m = p.matcher(s);

                while (m.find()) {
                    wordList.add(m.group().toLowerCase());
                    if (wordList.size() == MAX_WORDLIST_SIZE) {
                        return wordList;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка при генерация списка слов");
            e.printStackTrace();
        }
        return wordList;
    }
}
