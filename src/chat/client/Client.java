package chat.client;

public class Client {

    public static void main(String[] args) {
        new ChatGui(new ClientEndpoint());
    }

}
