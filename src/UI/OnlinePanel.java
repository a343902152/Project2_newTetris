package UI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import Controller.GameController;
import Controller.MusicPlayer;
import Controller.RemoteController;
import entity.Background;
import entity.MyButton;
import entity.MyLabel;

/**
 * 双人模式下的界面
 * @author hp
 *
 */
public class OnlinePanel extends JPanel {
	
	private GameController localController;
	private RemoteController remoteController;
	
	private Background background=new Background();
	
	private MyLabel mainWindow;
	private MyLabel nextWindow;
	private MyLabel popedLineWindow;
	private MyLabel levelWindow;
	private MyLabel scoreWindow;
	
	private MyLabel mainWindow2;
	private MyLabel nextWindow2;
	private MyLabel popedLineWindow2;
	private MyLabel levelWindow2;
	private MyLabel scoreWindow2;
	
	
	private MyButton btnResume;
	private MyButton btnPause;
	private MyButton btnMusic;
	
	public void setLocalController(GameController gameController){
		this.localController=gameController;
	}
	public void setRemoteController(RemoteController remoteController){
		this.remoteController=remoteController;
	}
	/**
	 * Create the panel.
	 */
	public OnlinePanel() {
		setLayout(null);
		
		int width=155;
		int heiht=53;
		
		// -------------1------------------------------
		mainWindow = new MyLabel(10, 10, 233, 405);
		mainWindow.setBounds(10, 10, 233, 405);
		add(mainWindow);
		
		nextWindow = new MyLabel(272, 24, width, 98);
		nextWindow.setBounds(272, 24, width, 98);
		add(nextWindow);
		
		popedLineWindow = new MyLabel(272, 132, width, 53,
				"Graphics/string/rmline.png");
		popedLineWindow.setBounds(272, 132, width, 53);
		popedLineWindow.setFont(new Font("Consolas",Font.BOLD,32));
		add(popedLineWindow);
		
		levelWindow = new MyLabel(272, 195, width, 53,
				"Graphics/string/level.png");
		levelWindow.setBounds(272, 195, width, 53);
		levelWindow.setFont(new Font("Consolas",Font.BOLD,32));
		add(levelWindow);
		
		scoreWindow = new MyLabel(272, 258, width, 53,
				"Graphics/string/score.png");
		scoreWindow.setBounds(272, 258, width, 53);
		scoreWindow.setFont(new Font("Consolas",Font.BOLD,32));
		add(scoreWindow);
		
		// -------------2------------------
		mainWindow2 = new MyLabel(272+175+175, 10, 233, 405);
		mainWindow2.setBounds(272+175+175, 10, 233, 405);
		add(mainWindow2);
		
		nextWindow2 = new MyLabel(272+175, 24, width, 98);
		nextWindow2.setBounds(272+175, 24, width, 98);
		add(nextWindow2);
		
		popedLineWindow2 = new MyLabel(272+175, 132, width, 53,
				"Graphics/string/rmline.png");
		popedLineWindow2.setBounds(272+175, 132, width, 53);
		popedLineWindow2.setFont(new Font("Consolas",Font.BOLD,32));
		add(popedLineWindow2);
		
		levelWindow2 = new MyLabel(272+175, 195, width, 53,
				"Graphics/string/level.png");
		levelWindow2.setBounds(272+175, 195, width, 53);
		levelWindow2.setFont(new Font("Consolas",Font.BOLD,32));
		add(levelWindow2);
		
		scoreWindow2 = new MyLabel(272+175, 258, width, 53,
				"Graphics/string/score.png");
		scoreWindow2.setBounds(272+175, 258, width, 53);
		scoreWindow2.setFont(new Font("Consolas",Font.BOLD,32));
		add(scoreWindow2);
		
		// ----------------------------------------
		btnResume = new MyButton("Graphics/string/start.png",86,52);
		btnResume.setBounds(282, 353, 145, 52);
		btnResume.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				localController.keyResume();
				if(!MusicPlayer.isRunning()){
					MusicPlayer.bgmPlay();
				}
			}
		});
		add(btnResume);
		
		btnPause = new MyButton("Graphics/string/pause.png",86,52);
		btnPause.setBounds(447, 353, 144, 52);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				localController.keyPause();
				if(MusicPlayer.isRunning()){
					MusicPlayer.bgmStop();
				}
			}
		});
		add(btnPause);
		
		btnMusic= new MyButton("Graphics/string/musicImg.png",86,52);
		btnMusic.setBounds(310, 321, 223, 23);
		btnMusic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(MusicPlayer.isturnOn()){
					MusicPlayer.setturnOn(false);
					MusicPlayer.bgmStop();
				}else{
					MusicPlayer.setturnOn(true);
					MusicPlayer.bgmPlay();
				}
			}
		});
		add(btnMusic);
		
		mainWindow.setFocusable(false);
		nextWindow.setFocusable(false);
		popedLineWindow.setFocusable(false);
		levelWindow.setFocusable(false);
		scoreWindow.setFocusable(false);
		
		mainWindow2.setFocusable(false);
		nextWindow2.setFocusable(false);
		popedLineWindow2.setFocusable(false);
		levelWindow2.setFocusable(false);
		scoreWindow2.setFocusable(false);
		
		btnResume.setFocusable(false);
		btnPause.setFocusable(false);
		btnMusic.setFocusable(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		background.draw(g);
		
		// 1-------------------------------
		mainWindow.creatwindow(g);
		nextWindow.creatwindow(g);
		popedLineWindow.creatwindow(g);
		levelWindow.creatwindow(g);
		scoreWindow.creatwindow(g);
		
		// 2-------------------------------
		mainWindow2.creatwindow(g);
		nextWindow2.creatwindow(g);
		popedLineWindow2.creatwindow(g);
		levelWindow2.creatwindow(g);
		scoreWindow2.creatwindow(g);
		
		try{
			popedLineWindow.setText(" "+Integer.toString(localController.getGamedao().cancelline));
			levelWindow.setText(" "+Integer.toString(localController.getGamedao().level));
			scoreWindow.setText(" "+Integer.toString(localController.getGamedao().score));
			
			// 在这里控制下落跟下一个形状的位置
			localController.getCurRect().draw(g, 12, 32);
			localController.getNextRect().draw(g, 235, 72);
			localController.getGamedao().drawmap(g,0);
			
			popedLineWindow2.setText(" "+Integer.toString(remoteController.getGameDao().cancelline));
			levelWindow2.setText(" "+Integer.toString(remoteController.getGameDao().level));
			scoreWindow2.setText(" "+Integer.toString(remoteController.getGameDao().score));
			
			// 在这里控制下落跟下一个形状的位置
			remoteController.getCurRect().draw(g, 623, 32);
			remoteController.getNextRect().draw(g, 405, 72);
			remoteController.getGameDao().drawmap(g,1);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
