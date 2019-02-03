package stc.lesson8;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * Серверный поток для поддержания соединения с клиентским потоком.
 */
public class Server extends Thread {
    private final BufferedWriter msgWriter;
    private final BufferedReader msgReader;
    private final Socket socket;

    Server(Socket socket) throws IOException {
        this.msgWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.msgReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
    }

    /**
     * Полученное от соответствующего клиента сообщение рассылается всем.
     * @param msg - полученое для рассылки сообщение.
     * @throws IOException
     */
    private void sendAll(String msg) throws IOException {
        for (Map.Entry<Server, String> entry : Main.serverMap.entrySet()) {
            Server server = entry.getKey();
            server.msgWriter.write(msg);
            server.msgWriter.newLine();
            server.msgWriter.flush();
        }
    }

    /**
     * Поток ожидания входящих сообщений с последующей рассылкой всем клиентам.
     */
    @Override
    public void run() {
        String username = "";
        try {
            username = msgReader.readLine();

            System.out.println(username + " подключился к чату");
            sendAll(username + " подключился к чату");
            Main.serverMap.put(this, username);

            String msg;
            while (!"quit".equals(msg = msgReader.readLine())) {
                System.out.println(username + ": " + msg );
                sendAll(username + ": " + msg);
            }

            Main.serverMap.remove(this, username);
            System.out.println("Участник " + username + " покинул чат.");
            sendAll("Участник " + username + " покинул чат.");
        } catch (IOException ex) {
            Main.serverMap.remove(this, username);
            System.out.println("Ошибка ввода/вывода во время ожидания подключений");
            ex.printStackTrace();
        } finally {
            try {
                msgWriter.close();
                msgReader.close();
                socket.close();
            } catch (IOException ex) {
                System.out.println("Ошибка закрытия потоков");
                ex.printStackTrace();
            }
        }
    }
}
