package stc.lesson8;

import java.io.*;
import java.net.Socket;


/**
 * Поток клиента для обработки входящих сообщений
 */
class Listener extends Thread {
    private final Socket socket;
    Listener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader msgReceiver = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (true) {
                System.out.println(msgReceiver.readLine());
            }
        } catch (IOException ex) {
            System.out.println("Listener завершил работу");
        }
    }
}