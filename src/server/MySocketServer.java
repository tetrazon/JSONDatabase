package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class MySocketServer extends Thread {
    private static final int PORT = 22222;
    private static final String ADDRESS = "127.0.0.1";

    @Override
    public void run() {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
             Socket socket = server.accept(); // accepting a new client
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
        ) {
            String []s;
            String receivedStr;
            String sentStr;
            //while (true) {
                {
                    receivedStr = input.readUTF();
                    s = receivedStr.split(" ");

                    sentStr = "A record # " + s[s.length - 1] + " was sent!";
                    System.out.println("Received: " + receivedStr);
                    System.out.println("Sent: " + sentStr);
                    output.writeUTF(sentStr); // resend it to the client

                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
