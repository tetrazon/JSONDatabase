package client;

public class Main {



    public static void main(String[] args) {
        MySocketClient mySocketClient = new MySocketClient();
        mySocketClient.start();
        try {
            mySocketClient.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //float val = 2F; toString(val);
//        long val1 = 2L;
//        System.out.println(toString(val1));
//        int val2 = 2; toString(val2);
//        byte val3 = 2; toString(val3);
//        char val4 = 2; toString(val4);
//        System.out.println(toString(val2));
//        System.out.println(toString(val3));
//        System.out.println(toString(val4));
        //double val5 = 2; toString(val5);

    }
}
