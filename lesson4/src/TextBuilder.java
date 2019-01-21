/**
 * Класс для генерации и получения текста.
 * @author Михаил Морин
 */
public class TextBuilder {
    private StringBuilder text = new StringBuilder();
    private String[] words;
    private int size;
    private int probability;

    /**
     * Метод для получения сгенерированного текста
     *
     * @return сгенерированный текст
     */
    public String getText() {
        return text.toString();
    }

    /**
     * Конструктор, принимающий на вход необходимые для генерации параметры.
     *
     * @param words       - словарь
     * @param size        - размер файлов
     * @param probability - вероятность вхождения слова в следующее предложение
     */
    public TextBuilder(String[] words, int size, int probability) {
        setParams(words, size, probability);
    }

    /**
     * Метод, позволяющий изменить параметры генерации
     *
     * @param words       - словарь
     * @param size        - размер файлов
     * @param probability - вероятность вхождения слова в следующее предложение
     */
    public void setParams(String[] words, int size, int probability) {
        this.words = words;
        this.size = size;
        this.probability = probability;
    }

    /**
     * Метод, осуществляющий генерацию текста файла
     */
    public void makeText() {
        text.delete(0, text.length());

        while (true) {
            text.append(makeParagraph((size - text.length() * 2 / 1024)).toString());
            if (text.length() * 2 / 1024 >= size) {
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
        int numOfWordGroups = (int) (Math.random() * 20) + 1; // Количество предложений в абзаце

        StringBuilder paragraph = new StringBuilder();
        paragraph.append("\t");
        for (int i = 0; i < numOfWordGroups; i++) {
            paragraph.append(makeSentence());

            if ((paragraph.length() * 2) / 1024 >= paragraphSize)
                break;
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
        char[] eosArr = {'.', '!', '?'};
        StringBuilder sentence = new StringBuilder();
        int numOfWords = (int) (Math.random() * 15) + 1; // Количество слов в предложении

        int i = 0;
        while (true) {
            String word = words[(int) (Math.random() * words.length)]; // Индекс слова из массива
            if (Math.random() < 1.0 / probability) {
                if (i == 0)
                    word = word.substring(0, 1).toUpperCase() + word.substring(1);
                sentence.append(word + " ");

                if (++i >= numOfWords)
                    break;
            }
        }
        sentence.deleteCharAt(sentence.length() - 1);
        char eos = eosArr[(int) (Math.random() * 3)];
        sentence.append(eos + " ");
        return sentence;
    }
}
