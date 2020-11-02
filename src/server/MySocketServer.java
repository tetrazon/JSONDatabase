package server;

import com.google.gson.Gson;
import util.Params;
import util.ServerResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MySocketServer extends Thread {
    private static final int PORT = 22222;
    private static final String ADDRESS = "127.0.0.1";
    private static final String pathToJsonDB = "F:\\java_project_idea\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json";
    private static final String nameDB = "db.json";
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    @Override
    public void run() {
        System.out.println("Server started!");
        Gson gson = new Gson();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        while (true) {
            try (ServerSocket server = new ServerSocket(PORT, 50, InetAddress.getByName(ADDRESS));
            ) {
                Socket socket = server.accept();// accepting a new client
                // executors
                executor.submit(() -> {
                    try(ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
                        ) {
                        String stringJsonFromClient;
                        stringJsonFromClient = (String) is.readObject();
                        Params receivedParams = gson.fromJson(stringJsonFromClient, Params.class);
                        System.out.println("Received: " + stringJsonFromClient);
                        ServerResponse serverResponse = makeServerResponse(receivedParams);
                        System.out.println("Sent: " + gson.toJson(serverResponse));
                        os.writeObject(gson.toJson(serverResponse));
                    if (serverResponse.getResponse().equals("OK") && receivedParams.getType().equals("exit")) {
                        socket.close();
                        //System.exit(0);
                        return;
                    }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ServerResponse makeServerResponse(Params params){
        ServerResponse serverResponse = new ServerResponse();
        if (params.getType().equals("exit")) {
            serverResponse.setResponse("OK");
            return serverResponse;
        }
        switch (params.getType()) {
            case "set":
                writeToJson(params);
                serverResponse.setResponse("OK");
                break;
            case "get":
                if (!readMapFromJson().containsKey(params.getKey())) {
                    serverResponse.setResponse("ERROR");
                    serverResponse.setReason("No such key");
                    break;
                }
                serverResponse.setResponse("OK");
                serverResponse.setValue(readMapFromJson().get(params.getKey()));
                break;
            case "delete":
                if (!readMapFromJson().containsKey(params.getKey())) {
                    serverResponse.setResponse("ERROR");
                    serverResponse.setReason("No such key");
                    break;
                }
                deleteFromJson(params);
                serverResponse.setResponse("OK");
                break;
        }
        return serverResponse;
    }

    public HashMap<String, String> readMapFromJson() {
        HashMap<String, String> hashMap = new HashMap<>();
        Lock readLock = rwLock.readLock();
        hashMap = getStringStringHashMap(readLock);
        //hashMap.forEach((k,v) -> System.out.println(k + ": " + v));
        return hashMap;
    }

    private HashMap<String, String> getStringStringHashMap(Lock lock) {
        HashMap<String, String> hashMap = new HashMap<>();
        String content;
        lock.lock();
        try {
            content = Files.readString(Paths.get(pathToJsonDB),
                    StandardCharsets.US_ASCII);
            Gson gson = new Gson();
            //Type itemsHashMapType = new TypeToken<HashMap<String, String>>() {}.getType();
            hashMap = gson.fromJson(content, HashMap.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return hashMap;
    }

    public void writeToJson(Params params) {
        HashMap<String, String> hashMap;
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            hashMap = getStringStringHashMap(writeLock);
            hashMap.put(params.getKey(), params.getValue());
            Gson gson = new Gson();

            try {
                Files.write(Paths.get(pathToJsonDB),
                        gson.toJson(hashMap).getBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
           writeLock.unlock();
        }
    }

    public void deleteFromJson(Params params) {
        HashMap<String, String> hashMap;
        Lock writeLock = rwLock.writeLock();
        writeLock.lock();
        try {
            hashMap = getStringStringHashMap(writeLock);
            hashMap.remove(params.getKey());
            Gson gson = new Gson();
            try {
                Files.write(Paths.get(pathToJsonDB),
                        gson.toJson(hashMap).getBytes(),
                        StandardOpenOption.CREATE,
                        StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            writeLock.unlock();
        }

    }

    public static void main(String[] args) {
        MySocketServer mySocketServer = new MySocketServer();
        mySocketServer.readMapFromJson();
    }

}
