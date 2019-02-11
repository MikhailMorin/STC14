package stc.lesson5.loader;

import java.io.BufferedReader;
import java.io.IOException;

public interface ResourceLoader {
    BufferedReader openResource() throws IOException;
}
