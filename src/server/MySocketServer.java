package server;

import util.Params;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class MySocketServer extends Thread {
    private static final int PORT = 22222;
    private static final String ADDRESS = "127.0.0.1";
    private HashMap<Integer, String> stringHashMap = new HashMap<>();

    @Override
    public void run() {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
             Socket socket = server.accept(); // accepting a new client
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output  = new DataOutputStream(socket.getOutputStream());
        ) {
                    ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                    Params receivedParams = new Params();
                    receivedParams = (Params) is.readObject();
                    System.out.println("Received: " + receivedParams);
                    String response = processParams(receivedParams);
                    System.out.println("Sent: " + response);
                    output.writeUTF(response); // resend it to the client
                    if (response.equals("OK")){
                        this.interrupt();
                    }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String processParams(Params params){
        String input;
        int key;
        String stringValue;
            if (params.getType().equals("exit")) {
                return "OK";
            }
            switch (params.getType()) {
                case "set":
                    key = Integer.parseInt(params.getIndex());
                    if (key < 1 || key > 1000) {
                        return "ERROR";
                    }
                    stringHashMap.put(key, params.getData());
                    return "OK";
                case "get":
                    key = Integer.parseInt(params.getIndex());
                    if (key < 1 || key > 1000 || !stringHashMap.containsKey(key)) {
                        return "ERROR";
                    }
                    return params.getData();
                case "delete":
                    key = Integer.parseInt(params.getIndex());
                    if (key < 1 || key > 1000) {
                        return "ERROR";
                    }
                    stringHashMap.remove(key);
                    return "OK";
            }
            return null;
    }
}
