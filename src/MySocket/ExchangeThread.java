package MySocket;


import java.io.*;
import java.net.Socket;

import Controller.GameController;
import Controller.RemoteController;
import com.google.gson.Gson;

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

    public static boolean isNum(String str){
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    public void run() {
        try {
            while(true) {
                String msg = bufferedReader.readLine();
                if(isNum(msg)){
                    RemoteController.remoteController.getGameDao().generateNewRectFromNet(Integer.parseInt(msg));
                    continue;
                }
                switch (msg){
                    case "up":
                        RemoteController.remoteController.rectUp();
                        break;
                    case "down":
                        RemoteController.remoteController.rectDown();
                        break;
                    case "left":
                        RemoteController.remoteController.rectLeft();
                        break;
                    case "right":
                        RemoteController.remoteController.rectRight();
                        break;
                    case "change":
                        RemoteController.remoteController.rectChange();
                        break;
                    case "gameover":
                        RemoteController.remoteController.gameover();
                        break;
                    case "keyPause":
                        GameController.localController.pause();
                        break;
                    case "keyResume":
                        GameController.localController.resume();
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