package server;

import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MySocketServer mySocketServer = new MySocketServer();
        mySocketServer.start();
        try {
            mySocketServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
