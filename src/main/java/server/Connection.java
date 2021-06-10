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
            StringBuilder sb = new StringBuilder();
            String line;
            while ( (line = this.reader.readLine()) != null) {
                // TODO: Handle invalid requests
                sb.append(line).append(System.lineSeparator());
                if (isValidJson(sb.toString())){
                    receivedRequest(sb.toString());
                    // Reset StringBuilder
                    sb.setLength(0);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidJson(String message) {
        // TODO: Implement me!
        return true;
    }

    private void receivedRequest(String message) {
        System.out.println("Received message: " + message);
    }

}
