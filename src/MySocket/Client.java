package MySocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by hp on 2015/11/1.
 */
public class Client {
    private static ExchangeThread clientExchangeThread;
    private static Socket socket;

    

    public static void Init(String ip,int PORT){
        try {
            socket = new Socket(ip,PORT);
            System.out.println("�ͻ���IP:"+socket.getLocalAddress()+"�˿�"+socket.getPort());
            // ���������߳�
            clientExchangeThread=new ExchangeThread(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ExchangeThread getExchangeThread(){
        return clientExchangeThread;
    }

}
