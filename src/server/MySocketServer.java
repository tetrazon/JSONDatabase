package server;

import com.google.gson.Gson;
import util.Params;
import util.ServerResponse;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MySocketServer extends Thread {
    private static final int PORT = 22222;
    private static final String ADDRESS = "127.0.0.1";
    private HashMap<String, String> stringHashMap = new HashMap<>();

    @Override
    public void run() {
        System.out.println("Server started!");
        while (true){
            try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
                 Socket socket = server.accept(); // accepting a new client
                 ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream is = new ObjectInputStream(socket.getInputStream())
            ) {
                Gson gson = new Gson();
                String stringJsonFromClient = (String) is.readObject();
                Params receivedParams = gson.fromJson(stringJsonFromClient, Params.class);
                System.out.println("Received: " + stringJsonFromClient);
                ServerResponse serverResponse = makeServerResponse(receivedParams);
                System.out.println("Sent: " + gson.toJson(serverResponse));
                os.writeObject(gson.toJson(serverResponse));
                if (serverResponse.getResponse().equals("OK") && receivedParams.getType().equals("exit")){
                    socket.close();
                    return;

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public ServerResponse makeServerResponse(Params params){
        ServerResponse serverResponse = new ServerResponse();
        String input;
        int key;
        String stringValue;
        if (params.getType().equals("exit")) {
            serverResponse.setResponse("OK");
            return serverResponse;
        }
        switch (params.getType()) {
            case "set":
                stringHashMap.put(params.getKey(), params.getValue());
                serverResponse.setResponse("OK");
                break;
            case "get":
                if (!stringHashMap.containsKey(params.getKey())) {
                    serverResponse.setResponse("ERROR");
                    serverResponse.setReason("No such key");
                    break;
                }
                serverResponse.setResponse("OK");
                serverResponse.setValue(stringHashMap.get(params.getKey()));
                break;
            case "delete":
                if (!stringHashMap.containsKey(params.getKey())) {
                    serverResponse.setResponse("ERROR");
                    serverResponse.setReason("No such key");
                    break;
                }
                stringHashMap.remove(params.getKey());
                serverResponse.setResponse("OK");
                break;
        }
        return serverResponse;
    }

}
