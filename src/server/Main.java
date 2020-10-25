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

    public void consoleMenu(){
        HashMap<Integer, String> stringHashMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        String input;
        int key;
        String stringValue;
        while (true){
            input = scanner.nextLine();
            String [] strings = input.split(" ");
            if (strings[0].equals("exit")) {
                break;
            }
            switch (strings[0]) {
                case "set":
                    key = Integer.parseInt(strings[1]);
                    if (key < 1 || key > 100) {
                        System.out.println("ERROR");
                        break;
                    }
                    stringValue = input.substring(6);
                    stringHashMap.put(key, stringValue);
                    System.out.println("OK");
                    break;
                case "get":
                    key = Integer.parseInt(strings[1]);
                    if (key < 1 || key > 100 || !stringHashMap.containsKey(key)) {
                        System.out.println("ERROR");
                        break;
                    }
                    System.out.println(stringHashMap.get(key));
                    break;
                case "delete":
                    key = Integer.parseInt(strings[1]);
                    if (key < 1 || key > 100) {
                        System.out.println("ERROR");
                        break;
                    }
                    stringHashMap.remove(key);
                    System.out.println("OK");
                    break;
            }
        }
    }
}
