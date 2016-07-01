package MySocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
//    public static final int PORT = 12345;//监听的端口号
    private static ExchangeThread serverExchangeThread;

    private static ServerSocket ss;
    private static Socket socket;
    public static void Init(int PORT){
        try {
            ss = new ServerSocket(PORT);
            System.out.println("端口号"+PORT+",服务器已启动");
            socket = ss.accept();
            // 启动交流线程
            serverExchangeThread=new ExchangeThread(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ExchangeThread getExchangeThread(){
        return serverExchangeThread;
    }

}