package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class MySocketClient extends Thread{
    private static final int PORT = 22222;
    private static final String ADDRESS = "127.0.0.1";

    @Override
    public void run() {
        System.out.println("Client started!");
        //Scanner scanner = new Scanner(System.in);
        //String []s;
        String str;
        try (Socket client = new Socket(InetAddress.getByName(ADDRESS), PORT);
             DataInputStream input = new DataInputStream(client.getInputStream());
             DataOutputStream output  = new DataOutputStream(client.getOutputStream())) {
            //while (true) {
                {

                    //str = scanner.nextLine();
                    str = "Give me a record # 12";
                    output.writeUTF(str);
                    System.out.println("Sent: " + str);
                    //s = input.readUTF().split(" ");
                    System.out.println("Received: " + input.readUTF());
                }
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
