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
 * ������Ϸ�Ŀ��
 * @author hp
 *
 */
public class MainFrame extends JFrame {

	private String[] title={"����˹���鵥����","����˹����������ս��"};
	private int[] size={415,760};

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MusicPlayer.bgmPlay();
		
		//���ñ���
		this.setTitle("����˹����");
		//���ô����С
		this.setSize(470,410);
		//����
		this.setLocationRelativeTo(null);
		//�ر��¼�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//����Ĭ��Panel
		this.setContentPane(new LauncherJPanel(this));
	}
	
	public void chooseMode(int mode){
		System.out.println("mode"+mode);
		switch (mode){
		case 0:
			OfflinePanel offlinePanel=new OfflinePanel();
			GameController.localController=new GameController(offlinePanel);
			offlinePanel.setLocalController(GameController.localController);
			this.setContentPane(offlinePanel);
			this.addKeyListener(new KeyController(GameController.localController));
			// ����ˢ��һ�½���ſ���,
			this.setTitle(title[0]);
			this.setSize(size[0],470);
			GameController.localController.gameStart();
			break;
		case 1:
			String port=JOptionPane.showInputDialog("�����뷿���:");
			Server.Init(Integer.parseInt(port));
			System.out.println("���ӳɹ�");
			
			OnlinePanel onlinePanel=new OnlinePanel();
			GameController.localController=new GameController(Server.getExchangeThread(),onlinePanel);
			RemoteController.remoteController=new RemoteController(onlinePanel);
			onlinePanel.setLocalController(GameController.localController);
			onlinePanel.setRemoteController(RemoteController.remoteController);
			this.setContentPane(onlinePanel);
			this.addKeyListener(new KeyController(GameController.localController));
			// ����ˢ��һ�½���ſ���,
			this.setTitle(title[1]);
			this.setSize(size[1],470);
			GameController.localController.gameStart();
			break;
		case 2:
			String port2=JOptionPane.showInputDialog("�����뷿���:");
			Client.Init(Integer.parseInt(port2));
			System.out.println("���ӳɹ�");

			OnlinePanel onlinePanel2=new OnlinePanel();
			GameController.localController=new GameController(Client.getExchangeThread(),onlinePanel2);
			RemoteController.remoteController=new RemoteController(onlinePanel2);
			onlinePanel2.setLocalController(GameController.localController);
			onlinePanel2.setRemoteController(RemoteController.remoteController);
			this.setContentPane(onlinePanel2);
			this.addKeyListener(new KeyController(GameController.localController));
			// ����ˢ��һ�½���ſ���,
			this.setTitle(title[1]);
			this.setSize(size[1],470);
			GameController.localController.gameStart();
			break;
		}
		requestFocus();
	}

}
