package Controller;

import HttpUtils.HttpCallbackListener;
import HttpUtils.HttpUtil;
import Main.Main;
import MySocket.ExchangeThread;
import UI.OfflinePanel;
import UI.OnlinePanel;
import com.google.gson.Gson;
import dao.GameDao;
import entity.Rect;

import javax.swing.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ��Ϸ���������������������ƶ�����ͣ��Ϸ��
 */
public class GameController {

	public static GameController localController;

	// ����
	private JPanel panel;
	// ʱ�������������GameTask��ÿ��һ��ʱ�䣬����ͱ仯һ��
	private Timer timer;

	private boolean isRunning =false;

	public GameDao getGamedao() {
		return gamedao;
	}

	// ��Ϸ���̿�������������ײ���֮���
	private GameDao gamedao;

	// Զ��ͨ���õ��߳�
	private ExchangeThread exchangeThread;

	private class GameTask extends TimerTask {
		private int timeSlice = 5;
        public void run() {
			if(!isRunning){
				return ;
			}
        	if(timeSlice <= 0){
                if(gamedao.isGameover()){
                    doGameOver();
                }else{
                    keyDown();
                    panel.repaint();
                    timeSlice =10-gamedao.level;
                }
        	}
        	else{
				timeSlice--;
			}
        }
    }

    private void doGameOver() {
        System.out.println("begin to end game");
        // ����ͣ��Ϸ
        isRunning = false;
        if(exchangeThread!=null){
            exchangeThread.sendMessage("gameover");
            int myScore = gamedao.score;
            int remoteScore = RemoteController.remoteController.getGameDao().score;

            String str = Integer.toString(myScore) + "��" + Integer.toString(remoteScore) + ",";
            if (myScore > remoteScore) {
                // WIN
                JOptionPane.showMessageDialog(panel, str + "��Ӯ��");
            } else if (myScore < remoteScore) {
                // LOSE
                JOptionPane.showMessageDialog(panel, str + "������");
            } else {
                // pingju
                JOptionPane.showMessageDialog(panel, str + "����һ��ƽ��");
            }
            Main.getFrame().init();
        }else{
            int myScore = gamedao.score;
            JOptionPane.showMessageDialog(panel, "��Ϸ����."+
                    "��ĵ÷�Ϊ:"+Integer.toString(myScore));
            String userName=JOptionPane.showInputDialog("��������������:");
            updateScore(myScore, userName);
        }
    }

    private void updateScore(int myScore, String userName) {
        String url= HttpUtil.BASE_URL+"score?action=updateScore"+
                "&userName="+userName+"&score="+myScore;
        HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                if(response.equals("failed")){
                    JOptionPane.showMessageDialog(panel, "δ֪ԭ���ϴ�ʧ��");
                }else{
                    JOptionPane.showMessageDialog(panel, "�����ϴ��ɹ�");
                }
                Main.getFrame().init();
            }
            @Override
            public void onError(Exception e) {
                JOptionPane.showMessageDialog(panel, "������ϣ��ϴ�ʧ�ܡ�");
                Main.getFrame().init();
            }
        });
    }

    public GameController(JPanel panel){
		this.panel=(OfflinePanel)panel;
	}

	public GameController(ExchangeThread thread,OnlinePanel panel) {
		this.exchangeThread=thread;
		this.panel=(OnlinePanel)panel;
	}

	/**
	 * ������Ϸ
	 */
	public void gameStart(){
		gamedao = new GameDao();
		isRunning =true;

		timer = new Timer();
		timer.schedule(new GameTask(), 100,30);
	}


    public void keyChange(){
        if(!isRunning) return;

        if(gamedao.doChange()){
            if(exchangeThread!=null){
                exchangeThread.sendMessage("change");
            }
            panel.repaint();
        }
    }

	public void keyUp() {
        if(!isRunning) return;

        if(gamedao.doUp()){
            if(exchangeThread!=null)
                exchangeThread.sendMessage("up");
            panel.repaint();
        }
	}

	public void keyDown() {
		if(!isRunning) return;

        if(gamedao.doDown()){
            if(exchangeThread!=null)
                exchangeThread.sendMessage("down");
            panel.repaint();
        }else{
            // �����·���
            int color=gamedao.generateNewRect();
            if(exchangeThread!=null)
                exchangeThread.sendMessage(String.valueOf(color));
        }

	}

	public void keyLeft() {
		if(!isRunning) return;

        if(gamedao.doLeft()){
            if(exchangeThread!=null)
                exchangeThread.sendMessage("left");
            panel.repaint();
        }
				
	}

	public void keyRight() {
		if(!isRunning) return;

        if(gamedao.doRight()){
            if(exchangeThread!=null)
                exchangeThread.sendMessage("right");
            panel.repaint();
        }
	}

	/**
	 * ������ͣ����ʱ��Զ�̷�����ͣ����
	 */
	public void keyPause()  {
		isRunning =false;
		if(exchangeThread!=null)
			exchangeThread.sendMessage("keyPause");
	}

	/**
	 * �����ָ�����ʱ��Զ�̷��ͻָ�����
	 */
	public void keyResume() {
		isRunning =true;
		if(exchangeThread!=null)
			exchangeThread.sendMessage("keyResume");
	}

	/**
	 * ����Զ�̵���ָͣ��
	 */
	public void pause()  {
		isRunning =false;
	}

	/**
	 * ����Զ�̵Ļָ�ָ��
	 */
	public void resume() {
		isRunning =true;
	}

	/**
	 * ֻ��ͨ��remote������
	 * ������һ���Ѿ�gameouver�ˣ����ҲҪͣһ��
	 */
	public int gameover(){
		System.out.print("gameover");
		isRunning=false;
		return gamedao.score;
	}

	// �Ƿ��������У��������еĻ�����true
	public boolean isRunning() {
		return isRunning;
	}
	
	
}
