package stc.lesson5;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    static ExecutorService threadPool;
    static String dirName = "C:\\Users\\Mikhail\\Desktop\\path\\warpeace";

    public static void main(String[] args) throws IOException, InterruptedException {
        File folder = new File(dirName);
        String[] sources = folder.list();

        String[] words = {"to", "the", "you", "by", "and", "that", "was", "see", "not", "had"};
//        String[] sources = {/*"C:\\Users\\Mikhail\\Desktop\\path\\warpeace.txt", "C:\\Users\\Mikhail\\Desktop\\path\\warpeace1.txt"*/"https://habr.com/ru/post/321344/", "https://habr.com/ru/company/tm/blog/435562/"};
//        String[] words = {/*"Petersburg",*/"программист", "приложения"};

        Main.threadPool = Executors.newFixedThreadPool(10);
        getOccurencies(sources,words, "C:\\Users\\Mikhail\\Desktop\\path\\file.txt");
    }

    static void getOccurencies(String[] sources, String[] words, String res) throws IOException, InterruptedException /*throws*/ {
        Set<String> wordsSet = new HashSet<>();
        wordsSet.addAll(Arrays.asList(words));

        BufferedWriter bw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(res)));
        DataParser.setOutput(bw);

        for (int i = 0; i < 2000; i++) {
            Runnable resource = new DataParser(dirName + "\\" + sources[i], wordsSet);
            threadPool.submit(resource);
        }

        threadPool.shutdown();
        threadPool.awaitTermination(3600, TimeUnit.SECONDS);
        bw.close();

    }
}


