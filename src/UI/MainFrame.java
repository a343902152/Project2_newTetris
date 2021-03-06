package UI;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.GameController;
import Controller.KeyController;
import Controller.MusicPlayer;
import Controller.RemoteController;
import MySocket.Client;
import MySocket.Server;

/**
 * 整个游戏的框框
 * @author hp
 *
 */
public class MainFrame extends JFrame {

    private String[] title = {"俄罗斯方块单机版", "俄罗斯方块联网对战版"};
    private int[] size = {415, 760};
    private LauncherJPanel launcherJPanel;

    /**
     * Create the frame.
     */
    public MainFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MusicPlayer.bgmPlay();
    }

    public void init(){
        //设置标题
        this.setTitle("俄罗斯方块");
        //设置窗体大小
        this.setSize(470, 410);
        //居中
        this.setLocationRelativeTo(null);
        //关闭事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置默认Panel
        launcherJPanel=new LauncherJPanel(this);
        this.setContentPane(launcherJPanel);
        setVisible(true);
    }

    public void chooseMode(int mode) {
        switch (mode) {
            case 0:
                OfflinePanel offlinePanel = new OfflinePanel();
                GameController.localController = new GameController(offlinePanel);
                offlinePanel.setLocalController(GameController.localController);
                this.setContentPane(offlinePanel);
                this.addKeyListener(new KeyController(GameController.localController));
                // 必须刷新一下界面才可以,
                this.setTitle(title[0]);
                this.setSize(size[0], 470);
                GameController.localController.gameStart();
                break;
            case 1:
                String port = JOptionPane.showInputDialog("请输入房间号:");
                Server.Init(Integer.parseInt(port));
                System.out.println("连接成功");

                OnlinePanel onlinePanel = new OnlinePanel();
                GameController.localController = new GameController(Server.getExchangeThread(), onlinePanel);
                RemoteController.remoteController = new RemoteController(onlinePanel);
                onlinePanel.setLocalController(GameController.localController);
                onlinePanel.setRemoteController(RemoteController.remoteController);
                this.setContentPane(onlinePanel);
                this.addKeyListener(new KeyController(GameController.localController));
                // 必须刷新一下界面才可以,
                this.setTitle(title[1]);
                this.setSize(size[1], 470);
                GameController.localController.gameStart();
                break;
            case 2:
                String ip = JOptionPane.showInputDialog("请输入对方IP:","localhost");
                String port2 = JOptionPane.showInputDialog("请输入房间号:");
                Client.Init(ip, Integer.parseInt(port2));
                System.out.println("连接成功");

                OnlinePanel onlinePanel2 = new OnlinePanel();
                GameController.localController = new GameController(Client.getExchangeThread(), onlinePanel2);
                RemoteController.remoteController = new RemoteController(onlinePanel2);
                onlinePanel2.setLocalController(GameController.localController);
                onlinePanel2.setRemoteController(RemoteController.remoteController);
                this.setContentPane(onlinePanel2);
                this.addKeyListener(new KeyController(GameController.localController));
                // 必须刷新一下界面才可以,
                this.setTitle(title[1]);
                this.setSize(size[1], 470);
                GameController.localController.gameStart();
                break;
        }
        requestFocus();
    }

}
