package chat.server;

import java.io.*;
import java.net.Socket;

public class ServerEndpoint extends Thread implements IChatListener {

    private String name;
    private Socket socket;
    private IChatMessageHub hub;
    private BufferedReader reader;
    private PrintWriter writer;
    private String username;

    public ServerEndpoint(Socket socket, IChatMessageHub hub) throws IOException {
        this.socket = socket;
        this.hub = hub;
        InputStream in = this.socket.getInputStream();
        reader = new BufferedReader(
                new InputStreamReader(in));
        OutputStream out = socket.getOutputStream();
        writer = new PrintWriter(out);


        // als Thread starten
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String prefix = reader.readLine();
                if (prefix.contains("OPEN")) {
                    username = reader.readLine();
                    hub.addChatListener(this);
                } else if (prefix.contains("PUBL")) {
                    hub.publish(getUsername(), reader.readLine(), false);
                } else if (prefix.contains("EXIT")) {

                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void onMessage(String fromUser, String message, boolean isAdmin) {
            if(isAdmin)
                writer.println("ADMIN#");
            else {
                if(!fromUser.equals(username))
                writer.println("SHOW#");
                writer.println(fromUser);
            }
            writer.println(message);
            writer.flush();


    }


}
