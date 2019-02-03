package stc.lesson8;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Поток клиента. Осуществляет подключение, создание потока для приёма входящих сообщений,
 * а также отправку сообщений серверной части.
 */
class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int SERVER_PORT = 3845;

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedWriter msgWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner msgScanner = new Scanner(System.in)) {

            System.out.print("Введите ваше имя: ");
            String name = msgScanner.next();
            msgWriter.write(name);
            msgWriter.flush();

            Listener listener = new Listener(socket);
            listener.start();

            String msg;
            do {
                msg = msgScanner.nextLine();
                msgWriter.write(msg);
                msgWriter.newLine();
                msgWriter.flush();
            } while (!"quit".equals(msg));
        }
        catch (SocketException ex){
            System.out.println(String.format("Отсутствует соединение с сервером %s:%d", SERVER_IP, SERVER_PORT));
        }
    }
}
