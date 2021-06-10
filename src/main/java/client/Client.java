package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import requests.SampleRequest;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Client provides all communication with the server. */
public class Client implements Closeable {

    private final Socket socket;
    private final PrintWriter writer;
    private final Gson gson;

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

        GsonBuilder builder = new GsonBuilder();
        this.gson = builder.create();
    }

    @Override
    public void close() throws IOException {
        writer.close();
        socket.close();
    }

    void sendSampleRequest() {
        SampleRequest request = new SampleRequest(1, "Hello World");
        writeToServer(gson.toJson(request));

    }

    private void writeToServer(String message) {
        this.writer.println(message);
    }

}
