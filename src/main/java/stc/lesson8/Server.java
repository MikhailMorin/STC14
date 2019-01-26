package stc.lesson8;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * Серверный поток для поддержания соединения с клиентским потоком.
 */
public class Server extends Thread {
    private BufferedWriter bw;
    private BufferedReader br;
    private Socket s;

    Server(Socket s) throws IOException {
        this.bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.s = s;
    }

    /**
     * Полученное от соответствующего клиента сообщение рассылается всем.
     * @param msg - полученое для рассылки сообщение.
     * @throws IOException
     */
    private void sendAll(String msg) throws IOException {
        for (Map.Entry<Server, String> e : Main.serverMap.entrySet()) {
            Server s = e.getKey();
            s.bw.write(msg);
            s.bw.newLine();
            s.bw.flush();
        }
    }

    /**
     * Поток ожидания входящих сообщений с последующей рассылкой всем клиентам.
     */
    @Override
    public void run() {
        String name = "";
        try {
            name = br.readLine();

            System.out.println(name + " подключился к чату");
            sendAll(name + " подключился к чату");
            Main.serverMap.put(this, name);

            String msg;
            while (!"quit".equals(msg = br.readLine())) {
                System.out.println(name + ": " + msg );
                sendAll(name + ": " + msg);
            }

            Main.serverMap.remove(this, name);
            System.out.println("Участник " + name + " покинул чат.");
            sendAll("Участник " + name + " покинул чат.");
        } catch (IOException e) {
            Main.serverMap.remove(this, name);
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                br.close();
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
