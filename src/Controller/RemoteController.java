package Controller;

import Main.Main;
import MySocket.Client;
import MySocket.Server;
import dao.GameDao;
import entity.Rect;

import javax.swing.*;

import UI.OnlinePanel;

/**
 * Created by hp on 2015/11/3.
 */
public class RemoteController  {

    public static RemoteController remoteController;

    // 生成界面
    private OnlinePanel panel;
    private GameDao gameDao=new GameDao();

    public GameDao getGameDao() {
        return gameDao;
    }

    public RemoteController(OnlinePanel panel) {
        this.panel=panel;
    }


    // 图形控制
    public void rectChange() {
        gameDao.doChange();
        panel.repaint();
    }

    public void rectUp(){
        gameDao.doUp();
        panel.repaint();
    }
    public void rectDown() {
        gameDao.doDown();
        panel.repaint();
    }

    public void rectLeft() {
        gameDao.doLeft();
        panel.repaint();
    }

    public void rectRight() {
        gameDao.doRight();
        panel.repaint();
    }

    public void gameover(){
        // 接到另一方的命令，首先暂停另一个controller，然后比较分数
        int remoteScore= GameController.localController.gameover();
        int myScore=gameDao.score;
        String str=Integer.toString(myScore)+"比"+Integer.toString(remoteScore)+",";
        // 这里比分的判断与GameController中的相反
        if(myScore<remoteScore){
            // WIN
            JOptionPane.showMessageDialog(panel, str + "你赢了");
        }else if(myScore>remoteScore){
            // LOSE
            JOptionPane.showMessageDialog(panel,str+"你输了");
        }else{
            // pingju
            JOptionPane.showMessageDialog(panel,str+"这是一场平局");
        }
        Main.getFrame().init();
    }



}
