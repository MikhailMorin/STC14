package stc.lesson5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws IOException {
        final String INPUT_DIR = "./src/main/resources/";
        final String OUTPUT_DIR = "./target/out/";

        String[] words = {"Petersburg", "программист", "приложения"};
        String[] resources = {INPUT_DIR + "warpeace.txt", "https://habr.com/ru/post/321344/", "https://habr.com/ru/company/tm/blog/435562/"};

        Files.createDirectories(Paths.get(OUTPUT_DIR));
        getOccurencies(resources, words, OUTPUT_DIR + "file.txt");
    }

    private static void getOccurencies(String[] resources, String[] words, String res) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        Set<String> wordsSet = new HashSet<>(Arrays.asList(words));

        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(res)));) {
            Arrays.stream(resources).map(r -> new DataParser(bw, r, wordsSet)).forEach(threadPool::submit);

            threadPool.shutdown();
            threadPool.awaitTermination(3600, TimeUnit.SECONDS);
        } catch (FileNotFoundException e) {
            System.out.println("Файл " + res + " не найден");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException при работе с выходным потоком");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Прерывание потоков выполнения парсинга ресурсов");
            e.printStackTrace();
        }
    }
}


