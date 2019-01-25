package stc.lesson4;


import java.util.Random;

/**
 * Класс для генерации и получения текста.
 *
 * @author Михаил Морин
 */
class TextBuilder {
    private static final int MAX_SENTENCE_SIZE = 15;
    private static final int MAX_PARAGRAPH_SIZE = 20;
    private static final double CHAR_SIZE_IN_KBYTES = 2.0 / 1024;

    private StringBuilder text = new StringBuilder();
    private String[] words;
    private int size;
    private int probability;
    private Random randomize;

    /**
     * Метод для получения сгенерированного текста
     *
     * @return сгенерированный текст
     */
    String getText() {
        return text.toString();
    }

    /**
     * Конструктор, принимающий на вход необходимые для генерации параметры.
     *
     * @param words       - словарь
     * @param size        - размер файлов
     * @param probability - вероятность вхождения слова в следующее предложение
     */
    TextBuilder(String[] words, int size, int probability) {
        setParams(words, size, probability);
        randomize = new Random();
    }

    TextBuilder() {
        randomize = new Random();
    }

    /**
     * Метод, позволяющий изменить параметры генерации
     *
     * @param words       - словарь
     * @param size        - размер файлов
     * @param probability - вероятность вхождения слова в следующее предложение
     */
    void setParams(String[] words, int size, int probability) {
        this.words = words;
        this.size = size;
        this.probability = probability;
    }

    void clearBuffer() {
        text.delete(0, text.length());
    }

    /**
     * Метод, осуществляющий генерацию текста файла
     */
    void makeText() {
        clearBuffer();

        while (true) {
            text.append(makeParagraph((size - (int) (text.length() * CHAR_SIZE_IN_KBYTES))).toString());
            if (text.length() * CHAR_SIZE_IN_KBYTES >= size) {
                break;
            }
        }
    }

    /**
     * Метод, осуществляющий генерацию одного абзаца.
     *
     * @param paragraphSize - максимально допустимая длина параграфа
     * @return - сгенерированный параграф
     */
    private StringBuilder makeParagraph(int paragraphSize) {
        randomize = new Random();

        int numOfWordGroups = randomize.nextInt(MAX_PARAGRAPH_SIZE + 1); // Количество предложений в абзаце

        StringBuilder paragraph = new StringBuilder();
        paragraph.append("\t");
        for (int i = 0; i < numOfWordGroups; i++) {
            paragraph.append(makeSentence());

            if (paragraph.length() * CHAR_SIZE_IN_KBYTES >= paragraphSize) {
                break;
            }
        }
        paragraph.append("\r\n");
        return paragraph;
    }

    /**
     * Метод, осуществляющий генерацию предложения.
     *
     * @return - сгенерированное предложение.
     */
    private StringBuilder makeSentence() {
        randomize = new Random();

        char[] eosArr = {'.', '!', '?'};
        StringBuilder sentence = new StringBuilder();
        int numOfWords = randomize.nextInt(MAX_SENTENCE_SIZE + 1); // Количество слов в предложении

        int i = 0;
        while (true) {
            String word = words[randomize.nextInt(words.length)]; // Индекс слова из массива
            if (randomize.nextInt(100) < probability) {
                if (i == 0) {
                    word = toCapitalLetter(word);
                }
                sentence.append(word + " ");

                if (++i >= numOfWords) {
                    break;
                }
            }
        }
        sentence.deleteCharAt(sentence.length() - 1);
        char eos = eosArr[randomize.nextInt(3)];
        sentence.append(eos + " ");
        return sentence;
    }

    /**
     * ПРиведение слова к заглавной букве
     *
     * @param word - исходное слово
     * @return - слово с заглавной буквы
     */
    private String toCapitalLetter(String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

}
