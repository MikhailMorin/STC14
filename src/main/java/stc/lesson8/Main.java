package stc.lesson8;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main {
    public static final int SERVER_PORT = 3845;
    public static final Map<String, Server> serverMap = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT);) {
                System.out.println("Сервер ожидает подключения");
                while (true) {
                    Socket socket = serverSocket.accept();
                    Server server = new Server(socket);
                    server.start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
