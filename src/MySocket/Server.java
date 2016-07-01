package MySocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
//    public static final int PORT = 12345;//�����Ķ˿ں�
    private static ExchangeThread serverExchangeThread;

    private static ServerSocket ss;
    private static Socket socket;
    public static void Init(int PORT){
        try {
            ss = new ServerSocket(PORT);
            System.out.println("�˿ں�"+PORT+",������������");
            socket = ss.accept();
            // ���������߳�
            serverExchangeThread=new ExchangeThread(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExchangeThread getExchangeThread(){
        return serverExchangeThread;
    }

}