package client;

import util.Params;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

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
             DataInputStream input = new DataInputStream(client.getInputStream());
             /*DataOutputStream output  = new DataOutputStream(client.getOutputStream())*/) {
                    ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                    Params sentParams = Params.getInstance(args);
                    os.writeObject(sentParams);
                    System.out.println("Sent: " + sentParams);
                    System.out.println("Received: " + input.readUTF());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
