import client.MySocketClient;
import server.MySocketServer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MySocketServer mySocketServer = new MySocketServer();
        mySocketServer.start();
        //mySocketServer.join();
        MySocketClient mySocketClient = new MySocketClient(args);
        mySocketClient.start();
        //mySocketClient.join();
    }
}
