package client;

import com.google.gson.Gson;
import util.Params;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            String stringJson = "";
            if (args[0].equals("-in")){
                //File file = new File("F:\\java_project_idea\\JSON Database\\JSON Database\\task\\src\\client\\data\\" + args[1]);
               /* try(BufferedReader reader = new BufferedReader(new FileReader("/client/data/" + args[1]));) {
                    stringJson = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                Path path = Paths.get("F:\\java_project_idea\\JSON Database\\JSON Database\\task\\src\\client\\data\\" + args[1]);
                stringJson = Files.readString(path);
            } else {
                Params sentParams = Params.getInstance(args);
                Gson gson = new Gson();
                stringJson = gson.toJson(sentParams);
            }

            os.writeObject(stringJson);
            System.out.println("Sent: " + stringJson);
            String stringJsonFromServer = (String) is.readObject();

            System.out.println("Received: " + stringJsonFromServer);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
