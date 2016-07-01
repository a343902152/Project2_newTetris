package MySocket;


import java.io.*;
import java.net.Socket;

import Controller.GameController;
import Controller.RemoteController;
import com.google.gson.Gson;
import dao.GameDao;
import entity.GamedaoMessage;

/**
 * 进程通信线程
 */
public class ExchangeThread implements Runnable {
    private Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;

    private Gson gson=new Gson();

    public ExchangeThread(Socket client) {
        socket = client;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch (Exception e){
            e.printStackTrace();
        }
        new Thread(this).start();
    }
    public void run() {
        try {
            while(true) {
                String msg = bufferedReader.readLine();
                switch (msg){
                    case "gamaover":
                        RemoteController.remoteController.gameover();
                        break;
                    case "keyPause":
                        GameController.localController.pause();
                        break;
                    case "keyResume":
                        GameController.localController.resume();
                        break;
                    default:
                        GamedaoMessage message=gson.fromJson(msg,GamedaoMessage.class);
                        RemoteController.remoteController.getGameDao().setGamedaoMessage(message);
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("服务器 run 异常: " + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    socket = null;
                    System.out.println("服务端 finally 异常:" + e.getMessage());
                }
            }
        }
    }

    public void sendMessage(String str){
        try {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}