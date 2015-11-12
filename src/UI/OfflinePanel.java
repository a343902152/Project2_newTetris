package UI;

import javax.swing.JPanel;

import Controller.GameController;
import Controller.MusicPlayer;
import entity.Background;
import entity.MyButton;
import entity.MyLabel;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;

/**
 * 单人模式下的界面
 * @author hp
 *
 */
public class OfflinePanel extends JPanel {
	
	private GameController localController;
	
	private Background background=new Background();
	
	private MyLabel mainWindow;
	private MyLabel nextWindow;
	private MyLabel popedLineWindow;
	private MyLabel levelWindow;
	private MyLabel scoreWindow;
	private MyButton btnResume;
	private MyButton btnPause;
	private MyButton btnMusic;
	
	public void setLocalController(GameController gameController){
		this.localController=gameController;
	}
	
	/**
	 * Create the panel.
	 */
	public OfflinePanel() {
		setLayout(null);
		
		mainWindow = new MyLabel(10, 10, 236, 405);
		mainWindow.setBounds(10, 10, 236, 405);
		add(mainWindow);
		
		nextWindow = new MyLabel(272, 24, 185, 98);
		nextWindow.setBounds(272, 24, 185, 98);
		add(nextWindow);
		
		popedLineWindow = new MyLabel(272, 132, 185, 53,
				"Graphics/string/rmline.png");
		popedLineWindow.setBounds(272, 132, 185, 53);
		popedLineWindow.setFont(new Font("Consolas",Font.BOLD,32));
		add(popedLineWindow);
		
		levelWindow = new MyLabel(272, 195, 185, 53,
				"Graphics/string/level.png");
		levelWindow.setBounds(272, 195, 185, 53);
		levelWindow.setFont(new Font("Consolas",Font.BOLD,32));
		add(levelWindow);
		
		scoreWindow = new MyLabel(272, 258, 185, 53,
				"Graphics/string/score.png");
		scoreWindow.setBounds(272, 258, 185, 53);
		scoreWindow.setFont(new Font("Consolas",Font.BOLD,32));
		add(scoreWindow);
		
		// 开始和暂停键
		btnResume = new MyButton("Graphics/string/start.png",86,52);
		btnResume.setBounds(272, 353, 86, 52);
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
		btnPause.setBounds(368, 353, 86, 52);
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				localController.keyPause();
				if(MusicPlayer.isRunning()){
					MusicPlayer.bgmStop();
				}
			}
		});
		add(btnPause);
		
		// 音乐开关
		btnMusic= new MyButton("Graphics/string/musicImg.png",86,52);
		btnMusic.setBounds(272, 317, 185, 23);
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
		
		btnResume.setFocusable(false);
		btnPause.setFocusable(false);
		btnMusic.setFocusable(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		background.draw(g);
		mainWindow.creatwindow(g);
		nextWindow.creatwindow(g);
		
		popedLineWindow.creatwindow(g);
		levelWindow.creatwindow(g);
		scoreWindow.creatwindow(g);
		
		try{
			popedLineWindow.setText(" "+Integer.toString(localController.getGamedao().cancelline));
			levelWindow.setText(" "+Integer.toString(localController.getGamedao().level));
			scoreWindow.setText(" "+Integer.toString(localController.getGamedao().score));
			
			// 在这里控制下落跟下一个形状的位置
			localController.getCurRect().draw(g, 12, 32);
			localController.getNextRect().draw(g, 235, 72);
			localController.getGamedao().drawmap(g,0);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
