package stc.lesson5;

import org.jsoup.Jsoup;
import stc.lesson5.ResourceLoader.ResourceLoader;
import stc.lesson5.ResourceLoader.ResourceLoaderImpl;

import java.io.*;
import java.util.*;
import java.util.regex.*;

/**
 * Класс, реализующий разбор исходного ресурса с целью поиска
 * требуемых слов.
 */
public class DataParser extends Thread {
    private final Pattern uncompletedSentencePattern;
    private final Pattern endOfSentencePattern;
    private final Pattern fullSentencePattern;
    private String unfinishedSentence = "";

    private Set<String> wordList;

    private final BufferedWriter output;
    private final BufferedReader input;

    /**
     * Конструктор, принимающий в качестве параметров исходные данные для разбора.
     *
     * @param outRes     - поток для записи результатов работы.
     * @param inputSrc - поток для считывания данных.
     * @param wordList   - список искомых слов.
     */
    DataParser(BufferedWriter outRes, BufferedReader inputSrc, Set<String> wordList) {
        this.output = outRes;
        this.input = inputSrc;
        this.wordList = wordList;

        // Неоконченное предложение
        uncompletedSentencePattern = Pattern.compile("[A-ZА-Я][^!?.]+$");
        // Конец предложение
        endOfSentencePattern = Pattern.compile("^[^A-ZА-Я!?.]+[!?.]");
//        endOfSentencePattern = Pattern.compile("^[^A-ZА-Я].+?[!?.]");
        // Целое предложение
        fullSentencePattern = Pattern.compile("[A-ZА-Я][^!?.]+[!?.]", Pattern.MULTILINE);
    }

    /**
     * Поиск в предложении соответствия регулярному выражению.
     *
     * @param pattern - регулярное выражение.
     * @param content - предложение
     * @return - массив найденых соответствий
     */
    private String[] findMatches(Pattern pattern, String content) {
        List<String> matches = new LinkedList<>();
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            matches.add(matcher.group(0));
        }
        return matches.toArray(new String[0]);
    }

    /**
     * Метд проверки содержания искомых слов в предложении.
     *
     * @param sentence - предложение, в котором осуществляется поиск.
     * @return - найдено-ли совпадение (true/false)
     */
    boolean isContains(String sentence) {
        for (String word : wordList) {
            if (sentence.contains(word)) {
                return true;
            }
        }
        return false;
    }

    /*
        Ищем завершение предложения.
    */
    boolean checkFinish(String line) throws IOException {
        String[] endOfStcs = findMatches(endOfSentencePattern, line);
        if (endOfStcs.length == 0) {
            unfinishedSentence += " " + line;
            return false;
        }

        String continuation = endOfStcs[0];
        String sentence = unfinishedSentence + " " + continuation;

        if (isContains(sentence)) {
            synchronized (output) {
                sentence = sentence.replaceAll("[^a-zA-Zа-яА-Я\\d\\s.!?]", "");
                output.write(sentence + "\n");
            }
        }
        unfinishedSentence = "";
        return true;
    }

    /*
        Поиск целых предложений в строке.
    */
    int checkFinished(String line) throws IOException {
        int numOfStcs = 0;
        for (String matched : findMatches(fullSentencePattern, line)) {
            if (isContains(matched)) {
                synchronized (output) {
                    matched = matched.replaceAll("[^a-zA-Zа-яА-Я\\d\\s.!?]", "");
                    output.write(matched + "\n");
                    numOfStcs++;
                }
            }
        }
        return numOfStcs;
    }

    /*
        Поиск незавершенных предложений в строке.
    */
    boolean checkUnfinished(String line) {
        boolean isChecked = false;
        String[] end = findMatches(uncompletedSentencePattern, line);
        if (end.length != 0) {
            unfinishedSentence = end[0];
            isChecked = true;
        }
        return isChecked;
    }

    /**
     * Разбор текстовой строки.
     *
     * @param line - разбираемая строка
     * @throws IOException - в случае ошибки при работе с выходным потоком
     */
    void checkLine(String line) throws IOException {
        if (!unfinishedSentence.equals("")) {
            if (!checkFinish(line)) {
                return;
            }
        }
        checkFinished(line);
        checkUnfinished(line);
    }

    /**
     * Чтение из потока и разбор считанной информации.
     *
     * @throws IOException - в случае ошибки при работе с входным или выходным потоком
     */
    void parseFrom() throws IOException {
        long startTime = System.currentTimeMillis();

        String line;
        while ((line = input.readLine()) != null) {
             checkLine(line);
        }

        long fullTime = System.currentTimeMillis() - startTime;
        System.out.println(String.format("%s,  время %d мс.", this, fullTime));
    }

    @Override
    public void run() {
        try {
            System.out.println("ЗАПУСК " + this);
            parseFrom();
        } catch (IOException e) {
            System.err.printf("Ошибка при работе с ресурсами в %s", this);
            e.printStackTrace();
        }
    }
}
