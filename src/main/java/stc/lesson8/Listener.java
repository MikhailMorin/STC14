package stc.lesson8;

import java.io.*;
import java.net.Socket;


/**
 * Поток клиента для обработки входящих сообщений
 */
class Listener extends Thread {
    Socket socket;
    Listener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            System.out.println("Клиент stc.lesson8.Listener завершил работу");
        }
    }
}