package chat.client;

import java.io.*;
import java.net.Socket;

public class ClientEndpoint extends Thread implements IMessageSender {

    // Vorschlag fuer Attribute, bitte erweitern oder aendern:
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private IMessageGui messageGui;

    public ClientEndpoint() {

    }

    @Override
    public void run() {
        while (true) {
            try {
                String prefix = reader.readLine();
                switch (prefix) {
                    case "SHOW#":messageGui.showNewMessage(reader.readLine(), reader.readLine()); break;
                    case "ADMIN#": messageGui.showAdminMessage(reader.readLine()); break;
                    default: break;
                }



            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    @Override
    public void openChatConnection(String username, String host, int port, IMessageGui messageGui) {
        this.messageGui = messageGui;

        try {
            Socket s = new Socket(host, port);
            System.out.println("Trying to connect as " + username + " to " + host + ":" + port);
            InputStream in = s.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            OutputStream out = s.getOutputStream();
            writer = new PrintWriter(out);

            writer.println("OPEN#");
            writer.println(username);
            writer.flush();
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendChatMessage(String message) {
        writer.println("PUBL#");
        writer.println(message);
        writer.flush();
    }

    @Override
    public void closeChatConnection() {
        writer.println("EXIT");
        writer.flush();
    }
}
