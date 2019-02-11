package stc.lesson5;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.mockito.stubbing.*;
import stc.lesson5.loader.ResourceLoader;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DataParserTest {
    private static final Logger LOGGER =
            Logger.getLogger(DataParserTest.class.getSimpleName());

    private final String[] words = {"world", "java", "Innopolis", "программист", "Petersburg", "приложения"};

    private DataParser dataParser;
    private BufferedReader reader;
    private BufferedWriter writer;
    private StringBuilder storageForWriter;


    @BeforeEach
    void setUp() throws IOException {
        reader = Mockito.mock(BufferedReader.class);
        writer = Mockito.mock(BufferedWriter.class);
        storageForWriter = new StringBuilder();

        Answer stringAns = invocation -> {
            String string = invocation.getArgumentAt(0, String.class);
            storageForWriter.append(string.trim());
            return string;
        };
        Mockito.doAnswer(stringAns).when(writer).write(Mockito.anyString());

        dataParser = new DataParser(writer, reader,  new HashSet<>(Arrays.asList(words)));
    }

    @Test
    void isContains() {
        assertTrue(dataParser.isContains("Напиши свою версию приложения которое ты используешь каждый день просто попробуй"));
        assertFalse(dataParser.isContains("Hello, World!"));
    }

    @Test
    void checkFinish() throws IOException {
        assertTrue(dataParser.checkFinish("зарплат у программистов.\n" +
                "Напиши свою версию приложения которое ты используешь каждый день просто попробуй!"));
        assertFalse(dataParser.checkFinish("Напиши свою версию приложения которое ты используешь каждый день просто попробуй!"));
    }

    @Test
    void checkFinished() throws IOException {
        assertEquals(1, dataParser.checkFinished("зарплат у программистов.\n" +
                "Напиши свою версию приложения которое ты используешь каждый день."));

    }

    @Test
    void checkUnfinished(){
        assertTrue(dataParser.checkUnfinished("Напиши свою версию приложения которое ты используешь каждый"));
        assertFalse(dataParser.checkUnfinished("Насколько надулся пузырь зарплат у программистов."));
    }

    @Test
    void parseResource() throws IOException {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello world!".getBytes())
        ));
        dataParser = new DataParser(writer, rl.openResource(), new HashSet<>(Arrays.asList(words)));
        dataParser.parseFrom();

        assertEquals("Hello world!", storageForWriter.toString());
    }


    @Test
    void parseNextLineFinished() throws IOException {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello,\nworld!".getBytes())
        ));
        dataParser = new DataParser(writer, rl.openResource(), new HashSet<>(Arrays.asList(words)));
        dataParser.parseFrom();
        assertEquals("Hello world!", storageForWriter.toString());
    }


    @Test
    void multiLineSentences() throws IOException {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello\nmultiline\nworld.".getBytes())
        ));
        dataParser = new DataParser(writer, rl.openResource(), new HashSet<>(Arrays.asList(words)));
        dataParser.parseFrom();
        assertEquals("Hello multiline world.", storageForWriter.toString());
    }


    @Test
    void testRun() throws IOException {
        ResourceLoader rl = () -> new BufferedReader(new InputStreamReader(
                new ByteArrayInputStream("Hello world.".getBytes())
        ));
        dataParser = new DataParser(writer, rl.openResource(), new HashSet<>(Arrays.asList(words)));
        dataParser.run();
        assertEquals("Hello world.", storageForWriter.toString());
    }

    @Test
    void testRunException() throws IOException {
        Mockito.when(reader.readLine()).thenThrow(IOException.class);
        ByteArrayOutputStream errStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errStream));

        dataParser = new DataParser(writer, reader, new HashSet<>(Arrays.asList(words)));
        dataParser.run();

        assertEquals(String.format("Ошибка при работе с ресурсами в %s java.io.IOException", dataParser.getName()),
                     errStream.toString().trim()
        );
    }
}