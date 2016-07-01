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
 * 游戏整体控制器，比如命令方块移动，暂停游戏等
 */
public class GameController {

	public static GameController localController;

	// 界面
	private JPanel panel;
	// 时间控制器，加载GameTask，每过一段时间，界面就变化一次
	private Timer timer;

	private boolean isRunning =false;

	public GameDao getGamedao() {
		return gamedao;
	}

	// 游戏进程控制器，比如碰撞检测之类的
	private GameDao gamedao;

	// 远程通信用的线程
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
        // 先暂停游戏
        isRunning = false;
        if(exchangeThread!=null){
            exchangeThread.sendMessage("gameover");
            int myScore = gamedao.score;
            int remoteScore = RemoteController.remoteController.getGameDao().score;

            String str = Integer.toString(myScore) + "比" + Integer.toString(remoteScore) + ",";
            if (myScore > remoteScore) {
                // WIN
                JOptionPane.showMessageDialog(panel, str + "你赢了");
            } else if (myScore < remoteScore) {
                // LOSE
                JOptionPane.showMessageDialog(panel, str + "你输了");
            } else {
                // pingju
                JOptionPane.showMessageDialog(panel, str + "这是一场平局");
            }
            Main.getFrame().init();
        }else{
            int myScore = gamedao.score;
            JOptionPane.showMessageDialog(panel, "游戏结束."+
                    "你的得分为:"+Integer.toString(myScore));
            String userName=JOptionPane.showInputDialog("请输入您的名字:");
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
                    JOptionPane.showMessageDialog(panel, "未知原因，上传失败");
                }else{
                    JOptionPane.showMessageDialog(panel, "分数上传成功");
                }
                Main.getFrame().init();
            }
            @Override
            public void onError(Exception e) {
                JOptionPane.showMessageDialog(panel, "网络故障，上传失败。");
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
	 * 启动游戏
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
            // 生成新方块
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
	 * 按键暂停，此时向远程发送暂停命令
	 */
	public void keyPause()  {
		isRunning =false;
		if(exchangeThread!=null)
			exchangeThread.sendMessage("keyPause");
	}

	/**
	 * 按键恢复，此时向远程发送恢复命令
	 */
	public void keyResume() {
		isRunning =true;
		if(exchangeThread!=null)
			exchangeThread.sendMessage("keyResume");
	}

	/**
	 * 接受远程的暂停指令
	 */
	public void pause()  {
		isRunning =false;
	}

	/**
	 * 接受远程的恢复指令
	 */
	public void resume() {
		isRunning =true;
	}

	/**
	 * 只能通过remote来调用
	 * 表明另一边已经gameouver了，这边也要停一下
	 */
	public int gameover(){
		System.out.print("gameover");
		isRunning=false;
		return gamedao.score;
	}

	// 是否正在运行，正在运行的话返回true
	public boolean isRunning() {
		return isRunning;
	}
	
	
}
