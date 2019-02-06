package stc.lesson5.ResourceLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.*;

public class ResourceLoaderImpl implements ResourceLoader{
    private final String sourcePath;

    public ResourceLoaderImpl(String sourcePath){
        this.sourcePath = sourcePath;
    }

    /**
     * Создание потока для работы с ресурсом.
     *
     * @return созданый поток.
     * @throws IOException - в случае ошибки при попытке создания входного потока
     */
    @Override
    public BufferedReader openResource() throws IOException {
        SourceType sourceType = getSourceType(sourcePath);

        switch (sourceType) {
            case FTP: {
                URL url = new URL(sourcePath);
                URLConnection urlc = url.openConnection();
                return new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            }
            case HTTP: {
                Document parse = Jsoup.parse(new URL(sourcePath).openStream(), "utf-8", sourcePath);
                return new BufferedReader(new InputStreamReader(new ByteArrayInputStream(parse.text().getBytes())));
            }
            case FILE: {
                return new BufferedReader(new InputStreamReader(new FileInputStream(sourcePath)));
            }
            case UNKNOWN:
            default: {
                throw new IOException();
            }
        }
    }

    /**
     * Определение типа ресурса по адресу ресурса.
     *
     * @param source - адрес ресурса
     * @return тип ресурса в формате {@code SourceType}
     */
    private SourceType getSourceType(String source) {
        if (source.matches("^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$")) {
            return SourceType.HTTP;
        } else if (source.matches("^(ftps?://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w.-]*)*/?$")) {
            return SourceType.FTP;
        } else if(source.matches("((\\w{1}:|\\.)\\/([A-z]|[0-9]|\\s|\\/)+\\.\\w+)")) {
            return SourceType.FILE;
        }
        return SourceType.UNKNOWN;
    }

    enum SourceType {HTTP, FTP, FILE, UNKNOWN}
}