package client;

import com.google.gson.Gson;
import util.Params;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class MySocketClient extends Thread{
    private static final int PORT = 22222;
    private static final String ADDRESS = "127.0.0.1";
    private String[] args;

    public MySocketClient(String[] args) {
        super();
        this.args = args;
    }

    @Override
    public void run() {
        System.out.println("Client started!");
        try (Socket client = new Socket(InetAddress.getByName(ADDRESS), PORT);
             ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
             ObjectInputStream is = new ObjectInputStream(client.getInputStream())
             ) {
            Params sentParams = Params.getInstance(args);
            Gson gson = new Gson();
            os.writeObject(gson.toJson(sentParams));
            System.out.println("Sent: " + gson.toJson(sentParams));
            String stringJsonFromServer = (String) is.readObject();

            System.out.println("Received: " + stringJsonFromServer);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
