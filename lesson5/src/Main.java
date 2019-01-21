import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import java.util.List;

public class Main {

    enum SourceType {WEB, FTP, FILE, UNKNOWN};
    static private SourceType sourceType;

    public static void main(String[] args) throws IOException, InterruptedException {
        String[] sources = {"https://habr.com/ru/post/321344/"/*, "https://habr.com/ru/company/tm/blog/435562/"*/};
        String[] words = {"файл","программист", "приложения"};
        getOccurencies(sources,words, "C:\\Users\\Mikhail\\Desktop\\path\\file.txt");
    }

    static void getOccurencies(String[] sources, String[] words, String res) throws IOException, InterruptedException /*throws*/ {
        BufferedReader br = null;
        FileWriter fw  = new FileWriter(res);

        DataParser.setWordList(words);
        DataParser.setOutputStream(fw);

        int threads = 1; // Количество параллельных потоков
        List<DataParser> l = new LinkedList<>();
        for (int i = 0; i < sources.length; i++) {
            DataParser dp = new DataParser();
            l.add(dp);
        }

        for (String source : sources) {
            sourceType = detectionType(source);
            if(sourceType.equals(SourceType.UNKNOWN))
                continue;

            switch (sourceType){
                case FTP: {
                    URL url = new URL(source);
                    URLConnection urlc = url.openConnection();
                    br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
                    break;
                }
                case WEB:{
                    br = new BufferedReader(new InputStreamReader(new URL(source).openStream()));
                    break;
                }
                case FILE:{
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(source)));
                    break;
                }
            }

            DataParser.setInput(br);

            for (DataParser dp : l)
                dp.start();
            System.out.println("Threads started");
            for (DataParser dp : l)
                dp.join();
            br.close();
        }

        fw.close();
    }

    private static SourceType detectionType(String source){
        if (source.matches("^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w\\.-]*)*\\/?$"))
            return SourceType.WEB;
        else if (source.matches("^(ftps?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w\\.-]*)*\\/?$"))
            return SourceType.FTP;
        else if (source.matches("((\\w{1}:\\\\(([A-z]|[0-9]|\\s)+)\\\\\\w+\\.\\w+))|(\\w{1}:\\\\(\\w+\\.\\w+))"))
            return SourceType.FILE;
        else
            return SourceType.UNKNOWN;
    }
}


