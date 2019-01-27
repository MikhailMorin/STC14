package stc.lesson5;

import org.jsoup.Jsoup;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

/**
 * Класс, реализующий разбор исходного ресурса с целью поиска
 * требуемых слов.
 */
class DataParser extends Thread {
    private enum SourceType {WEB, FTP, FILE, UNKNOWN};

    private SourceType sourceType;
    private String source;

    private final String UNCOMPLETED_SENTENCE = "[A-ZА-Я][^!?.]+$"; // Неоконченное предложение
    private final String ENDOF_SENTENCE = "^[^A-ZА-Я!?.]+[!?.]";    // Конец предложение
    private final String FULL_SENTENCE = "[A-ZА-Я][^!?.]+[!?.]";    // Целое предложение

    private final Pattern uncompletedSentencePattern;
    private final Pattern endofSentencePattern;
    private final Pattern fullSentencePattern;
    private String unfinishedSentence = "";

    private Set<String> wordList;
    final private BufferedWriter output;

    /**
     * Конструктор, принимающий в качестве параметров исходные данные для разбора.
     *
     * @param source   - источник данных.
     * @param wordList - список искомых слов.
     */
    DataParser(BufferedWriter fw, String source, Set<String> wordList) {
        this.output = fw;
        this.source = source;
        this.sourceType = getSourceType(source);
        this.wordList = wordList;
        uncompletedSentencePattern = Pattern.compile(UNCOMPLETED_SENTENCE);
        endofSentencePattern = Pattern.compile(ENDOF_SENTENCE);
        fullSentencePattern = Pattern.compile(FULL_SENTENCE, Pattern.MULTILINE);
    }

    /**
     * Определение типа ресурса по адресу ресурса.
     *
     * @param source - адрес ресурса
     * @return тип ресурса в формате {@code SourceType}
     */
    private SourceType getSourceType(String source) {
        if (source.matches("^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$")) {
            return SourceType.WEB;
        } else if (source.matches("^(ftps?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$")) {
            return SourceType.FTP;
        } else if (source.matches("^(.*/)([^/]+?)(\\.[^.]+)?$")) {
            return SourceType.FILE;
        } else {
            return SourceType.UNKNOWN;
        }
    }

    /**
     * Создание потока для работы с ресурсом.
     *
     * @return созданый поток.
     * @throws IOException - в случае ошибки при попытке создания входного потока
     */
    private BufferedReader openResource() throws IOException {
        switch (sourceType) {
            case FTP: {
                URL url = new URL(source);
                URLConnection urlc = url.openConnection();
                return new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            }
            case WEB: {
                return new BufferedReader(new StringReader(Jsoup.connect(source).get().text()));
            }
            case FILE: {
                return new BufferedReader(new InputStreamReader(new FileInputStream(source)));
            }
            case UNKNOWN:
            default: {
                throw new IOException();
            }
        }
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
    private boolean isContains(String sentence) {
        for (String s : wordList) {
            if (sentence.contains(s)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Разбор текстовой строки.
     *
     * @param line - разбираемая строка
     * @throws IOException - в случае ошибки при работе с выходным потоком
     */
    private void checkLine(String line) throws IOException {
        /*
        Если есть незавершенная строка, ищем завершение во вновь считанной.
         */
        if (!unfinishedSentence.equals("")) {
            String[] endOfStcs = findMatches(endofSentencePattern, line);
            if (endOfStcs.length == 0) { // если конец предложения не найден, выход для чтения следующей строки
                unfinishedSentence += line;
                return;
            }

            String continuation = endOfStcs[0];
            String sentence = unfinishedSentence + " " + continuation;

            if (isContains(sentence)) {
                synchronized (output) {
                    sentence = sentence.replaceAll("[^a-zA-Zа-яА-Я\\d\\s]", "");
                    output.write(sentence + "\n");
                }
            }
            unfinishedSentence = "";
        }

        /*
        Поиск целых предложений в строке.
         */
        for (String matched : findMatches(fullSentencePattern, line)) {
            if (isContains(matched)) {
                synchronized (output) {
                    matched = matched.replaceAll("[^a-zA-Zа-яА-Я\\d\\s]", "");
                    output.write(matched + "\n");
                }
            }
        }

        /*
        Поиск незавершенных предложений в строке.
         */
        String[] end = findMatches(uncompletedSentencePattern, line);
        if (end.length != 0) {
            unfinishedSentence = end[0];
        }
    }

    /**
     * Чтение из потока и разбор считанной информации.
     *
     * @throws IOException - в случае ошибки при работе с входным или выходным потоком
     */
    private void parse() throws IOException {
        long startTime = System.nanoTime();
        try (BufferedReader r = this.openResource()) {
            String line;
            while ((line = r.readLine()) != null) {
                checkLine(line);
            }
        }
        long fullTime = System.nanoTime() - startTime;
        System.out.println(this + ": " + source + ",  Время: " + fullTime / 1_000_000 + " мс.");
    }

    @Override
    public void run() {
        try {
            System.out.println("ЗАПУСК " + this);
            parse();
        } catch (IOException e) {
            System.out.println("Ошибка при работе с ресурсами в " + this);
            e.printStackTrace();
        }
    }
}
