package UI;

import javax.swing.JPanel;

import HttpUtils.HttpCallbackListener;
import HttpUtils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Background;
import entity.GameWindow;
import entity.MyButton;
import entity.Score;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
	
	private GameWindow lblNewLabel,popedLineWindow;

    private JPanel thisPanel=this;
    private Gson gson=new Gson();
	public LauncherJPanel(MainFrame mainFrame) {
		this.mainFrame=mainFrame;
		setLayout(null);

		MyButton btnScores = new MyButton("Graphics/window/null.png","排行榜",123,50);
		btnScores.setFont(new Font("黑体", Font.BOLD, 16));
		btnScores.setForeground(Color.WHITE);
		btnScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                // http，弹出排行榜情况
                String url=HttpUtil.BASE_URL+"score?action="+"selectScore";
                HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
                    @Override
                    public void onFinish(String response) {
                        System.out.println(response);
                        List<Score> scoreList=gson.fromJson(response,new TypeToken<List<Score>>(){}.getType());
                        StringBuilder builder=new StringBuilder();
                        for(int i=0;i<scoreList.size();i++){
                            builder.append("排名"+(i+1)+".   用户名："+scoreList.get(i).getUserName()+"   得分："+scoreList.get(i).getSocre()+"\n");
                        }
                        JOptionPane.showMessageDialog(thisPanel,builder.toString());
                    }

                    @Override
                    public void onError(Exception e) {
                        System.out.println("error");
                    }
                });
            }
		});
		btnScores.setBounds(315, 193, 123, 28);
		add(btnScores);



		MyButton btnOffline = new MyButton("Graphics/window/null.png","单人模式",123,50);
		btnOffline.setFont(new Font("黑体", Font.BOLD, 16));
		btnOffline.setForeground(Color.WHITE);
		btnOffline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.chooseMode(0);
			}
		});
		btnOffline.setBounds(315, 231, 123, 28);
		add(btnOffline);
		
		MyButton btnMode1 = new MyButton("Graphics/window/null.png","创建房间",123,50);
		btnMode1.setForeground(Color.WHITE);
		btnMode1.setFont(new Font("黑体", Font.BOLD, 16));
		btnMode1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.chooseMode(1);
			}
		});
		btnMode1.setBounds(315, 269, 123, 28);
		add(btnMode1);
		
		MyButton btnMode2 = new MyButton("Graphics/window/null.png","进入房间",123,50);
		btnMode2.setForeground(Color.WHITE);
		btnMode2.setFont(new Font("黑体", Font.BOLD, 16));
		btnMode2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mainFrame.chooseMode(2);
			}
		});
		btnMode2.setBounds(315, 307, 123, 33);
		add(btnMode2);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		background.draw(g);
	}

}
