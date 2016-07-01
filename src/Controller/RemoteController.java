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

    // ���ɽ���
    private OnlinePanel panel;
    private GameDao gameDao=new GameDao();

    public GameDao getGameDao() {
        return gameDao;
    }

    public RemoteController(OnlinePanel panel) {
        this.panel=panel;
    }


    // ͼ�ο���
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
        // �ӵ���һ�������������ͣ��һ��controller��Ȼ��ȽϷ���
        int remoteScore= GameController.localController.gameover();
        int myScore=gameDao.score;
        String str=Integer.toString(myScore)+"��"+Integer.toString(remoteScore)+",";
        // ����ȷֵ��ж���GameController�е��෴
        if(myScore<remoteScore){
            // WIN
            JOptionPane.showMessageDialog(panel, str + "��Ӯ��");
        }else if(myScore>remoteScore){
            // LOSE
            JOptionPane.showMessageDialog(panel,str+"������");
        }else{
            // pingju
            JOptionPane.showMessageDialog(panel,str+"����һ��ƽ��");
        }
        Main.getFrame().init();
    }



}
