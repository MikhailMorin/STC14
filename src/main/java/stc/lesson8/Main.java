package stc.lesson8;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Поток ожидания клиентских подключений с последующим созданием
 * отдельных потоков для работы с подключенными клиентами.
 */
class Main {
    static final int SERVER_PORT = 3845;
    static final Map<Server, String> serverMap = new ConcurrentHashMap<>();

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
                System.out.println("Ошибка listener'а серверной части");
                e.printStackTrace();
            }
        }
    }
}
