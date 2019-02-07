package stc.lesson5.ResourceLoader;

import org.junit.jupiter.api.*;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.*;
import java.nio.file.*;
import java.net.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class ResourceLoaderImplTest {
    private ResourceLoaderImpl loader;
    public static final String content = "test load";
    private static final String http = "http://some.url/path/file.txt";
    private static final String ftp = "ftp://some.url/path/file.txt";
    private static final String unknown = "192.168.12.1";

    @BeforeAll
    static void setUpAll(){
        MyURLStreamHandler handler = new MyURLStreamHandler();
        URL.setURLStreamHandlerFactory(handler);
    }

    @Test
    void openUnknownResource() throws IOException {
        loader = new ResourceLoaderImpl(unknown);
        assertThrows(IOException.class, loader::openResource);

    }

    @Test
    void openFtpResource() throws IOException {
        loader = new ResourceLoaderImpl(ftp);
        BufferedReader reader = loader.openResource();
        String read = reader.readLine();
        assertEquals(content, read);

    }

    @Test
    void openHttpResource() throws IOException {
        loader = new ResourceLoaderImpl(http);
        BufferedReader reader = loader.openResource();
        String read = reader.readLine();
        assertEquals(content, read);
    }

    @Test
    void openLocalResource() throws IOException {
        TemporaryFolder tf = new TemporaryFolder();
        tf.create();
        File file = tf.newFile();
        Files.write(Paths.get(file.toURI()), content.getBytes());
        ResourceLoader rl = new ResourceLoaderImpl(file.getAbsolutePath());

        String read = rl.openResource().lines().collect(Collectors.joining("\n"));
        assertEquals(content, read);
        tf.delete();
    }
}

class MyURLStreamHandler extends URLStreamHandler implements URLStreamHandlerFactory {
    @Override
    protected URLConnection openConnection(URL u) throws IOException {
        HttpURLConnection mock = Mockito.mock(HttpURLConnection.class);
        Mockito.when(mock.getInputStream()).thenReturn(new ByteArrayInputStream(ResourceLoaderImplTest.content.getBytes()));
        return mock;
    }

    @Override
    public URLStreamHandler createURLStreamHandler(String protocol) {
        return this;
    }
}
