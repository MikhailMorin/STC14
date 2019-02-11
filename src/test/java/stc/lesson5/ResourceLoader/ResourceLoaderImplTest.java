package stc.lesson5.ResourceLoader;

import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import stc.lesson5.loader.ResourceLoader;
import stc.lesson5.loader.ResourceLoaderImpl;

import java.io.*;
import java.nio.file.*;
import java.net.*;

import static org.junit.jupiter.api.Assertions.*;

class ResourceLoaderImplTest {
    static final String CONTENT = "test load";
    private static final String HTTP_RESOURCE = "http://some.url/path/file.txt";
    private static final String FTP_RESOURCE = "ftp://some.url/path/file.txt";
    private static final String UNKNOWN_RESOURCE = "192.168.12.1";
    private static final String LOCAL_RESOURCE_DIR = "./target/out/";

    private ResourceLoaderImpl loader;

    @BeforeAll
    static void setUpAll(){
        MyURLStreamHandler handler = new MyURLStreamHandler();
        URL.setURLStreamHandlerFactory(handler);
    }

    @Test
    void openUnknownResource() {
        loader = new ResourceLoaderImpl(UNKNOWN_RESOURCE);
        assertThrows(IOException.class, loader::openResource);
    }

    @Test
    void openFtpResource() throws IOException {
        loader = new ResourceLoaderImpl(FTP_RESOURCE);
        BufferedReader reader = loader.openResource();
        String read = reader.readLine();
        assertEquals(CONTENT, read);

    }

    @Test
    void openHttpResource() throws IOException {
        loader = new ResourceLoaderImpl(HTTP_RESOURCE);
        BufferedReader reader = loader.openResource();
        String read = reader.readLine();
        assertEquals(CONTENT, read);
    }

    @Test
    void openLocalResource() throws IOException {
        Files.createDirectories(Paths.get(LOCAL_RESOURCE_DIR));
        final String localFilename = LOCAL_RESOURCE_DIR + "out.txt";

        Files.write(Paths.get(localFilename), CONTENT.getBytes());
        ResourceLoader resourceLoader = new ResourceLoaderImpl(localFilename);
        BufferedReader reader = resourceLoader.openResource();

        assertEquals(CONTENT, reader.readLine());

        reader.close();
        Files.delete(Paths.get(localFilename));
    }
}

class MyURLStreamHandler extends URLStreamHandler implements URLStreamHandlerFactory {
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        HttpURLConnection mock = Mockito.mock(HttpURLConnection.class);
        Mockito.when(mock.getInputStream())
               .thenReturn(new ByteArrayInputStream(ResourceLoaderImplTest.CONTENT.getBytes()));
        return mock;
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return this;
    }
}
