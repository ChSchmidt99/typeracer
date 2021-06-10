package client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Client provides all communication with the server. */
public class Client implements Closeable {

    private final Socket socket;
    private final PrintWriter writer;

    /**
     * Tries to connect to the server and starts listening for responses when successful.
     * Subscribe observer to receive responses.
     * @param serverAddress address to server
     * @param port port the server listens to
     * @throws IOException if connection attempt fails
     */
    public Client(InetAddress serverAddress, int port) throws IOException {
        this.socket = new Socket(serverAddress, port);
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void close() throws IOException {
        writer.close();
        socket.close();
    }

    void writeToServer(String message) {
        this.writer.println(message);
    }

}
