package server;

import java.io.*;
import java.net.Socket;

public class Connection implements Closeable {

    private final Socket socket;
    private final BufferedReader reader;

    /**
     * Constructor of RequestHandler.
     * @param socket the Request handler is supposed to run on
     * @throws IOException if socket is not connected
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /** Closes socket specified when creating RequestHandler. */
    @Override
    public void close() {
        try {
            socket.close();
            reader.close();
        } catch (IOException e) {
            Logger.log(Logger.LogLevel.ERROR, e.getMessage());
        }
    }

    public void handleRequests() {
        try {
            String line;
            while ( (line = this.reader.readLine()) != null) {
                receivedRequest(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    private void receivedRequest(String message) {
        // TODO: Handle invalid requests
        System.out.println("Received message: " + message);
    }
}
