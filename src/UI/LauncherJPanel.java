package UI;

import javax.swing.JPanel;

import entity.Background;
import entity.MyButton;
import entity.MyLabel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

/**
 * 启动界面，MainFrame启动后首先调用这个panel
 * @author hp
 *
 */
public class LauncherJPanel extends JPanel {

	private MainFrame mainFrame;
	/**
	 * Create the panel.
	 */
	private Background background=new Background();
	
	private MyLabel lblNewLabel,popedLineWindow;
	
	public LauncherJPanel(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		setLayout(null);
		
		MyButton btnOffline = new MyButton("Graphics/string/offline.png",123,50);
		btnOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.chooseMode(0);
			}
		});
		btnOffline.setBounds(359, 209, 123, 50);
		add(btnOffline);
		
		MyButton btnMode1 = new MyButton("Graphics/string/online1.png",123,50);
		btnMode1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.chooseMode(1);
			}
		});
		btnMode1.setBounds(359, 269, 123, 50);
		add(btnMode1);
		
		MyButton btnMode2 = new MyButton("Graphics/string/online2.png",123,50);
		btnMode2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.chooseMode(2);
			}
		});
		btnMode2.setBounds(359, 339, 123, 50);
		add(btnMode2);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		background.draw(g);
	}

}
