package client;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            Client client = new Client(addr,8080);
            client.writeToServer("Hello \\n World");
            client.writeToServer("From Client");
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
