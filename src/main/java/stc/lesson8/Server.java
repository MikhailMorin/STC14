package stc.lesson8;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class Server extends Thread {
    BufferedWriter bw;
    BufferedReader br;
    Socket s;
//    String name;

    public Server(Socket s) throws IOException {
        this.bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        this.s = s;
    }

    private void sendAll(String msg) throws IOException {
        for (Map.Entry<String, Server> e : Main.serverMap.entrySet()) {
            Server s = e.getValue();
            s.bw.write(msg);
            s.bw.newLine();
            s.bw.flush();
        }
    }

    @Override
    public void run() {
        try {
            String name = br.readLine();

            System.out.println(name + " подключился к чату");
            sendAll(name + " подключился к чату");
            Main.serverMap.put(name, this);

            String msg;
            while (!"quit".equals(msg = br.readLine())) {
                System.out.println(name + ": " + msg );
                sendAll(name + ": " + msg);
            }

            Main.serverMap.remove(name);
            System.out.println("Участник " + name + " покинул чат.");
            sendAll("Участник " + name + " покинул чат.");
        } catch (IOException e) {
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
