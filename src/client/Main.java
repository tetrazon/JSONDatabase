package client;

public class Main {



    public static void main(String[] args) {
        MySocketClient mySocketClient = new MySocketClient(args);
        mySocketClient.start();
        try {
            mySocketClient.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
