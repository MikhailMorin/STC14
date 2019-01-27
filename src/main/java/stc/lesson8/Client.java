package stc.lesson8;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Поток клиента. Осуществляет подключение, создание потока для приёма входящих сообщений,
 * а также отправку сообщений серверной части.
 */
class Client {
    static final String SERVER_IP = "127.0.0.1";
    static final int SERVER_PORT = 3845;
    public static void main(String[] args) throws IOException {
        try (Socket s = new Socket(SERVER_IP, SERVER_PORT);
             BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
             Scanner sc = new Scanner(System.in);) {

            System.out.print("Введите ваше имя: ");
            String name = sc.next();
            bw.write(name);
            bw.flush();

            Listener listener = new Listener(s);
            listener.start();

            String msg;
            while (true){
                msg = sc.nextLine();
                bw.write(msg);
                bw.newLine();
                bw.flush();
                if("quit".equals(msg)) {
                    break;
                }
            }
        }
    }
}
