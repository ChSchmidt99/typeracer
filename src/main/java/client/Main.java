package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import requests.SampleRequest;

import java.io.IOException;
import java.net.InetAddress;

public class Main {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getByName("127.0.0.1");
            Client client = new Client(addr,8080);
            client.sendSampleRequest();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
}
