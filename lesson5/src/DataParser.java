import java.io.*;
import org.jsoup.Jsoup;

public class DataParser extends Thread {
    private static String[] wordList; // Список искомых слов
    private static BufferedReader input;
    private static FileWriter output;

    public static void setOutputStream(FileWriter fw){
        output = fw;
    }

    public static void setWordList(String[] wordList) {
        DataParser.wordList = wordList;
    }

    public static void setInput(BufferedReader input) throws IOException {
        DataParser.input = input;
    }

    public static void setOutput(FileWriter output) throws IOException {
        DataParser.output = output;
    }


    public void parse() throws IOException {
        String[] sentences;

        StringBuilder buffer = new StringBuilder();
        int data = 0;
        while ((data = input.read()) != -1) {
            char symb = (char) data;
            if (symb == '.' || symb == '!' || symb == '?') {
                String buf = buffer.toString();
                System.out.println(buf);
                for (String word : wordList) {
                    if(buf.contains(word)){
                        output.write(buffer.toString() + "\n");
                        break;
                    }
                }
                buffer = new StringBuilder();
            } else {
                buffer.append(symb);
            }
        }
    }

    @Override
    public void run() {
        try {
            parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
