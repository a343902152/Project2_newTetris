package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class MyButton extends JButton{
	private static final long serialVersionUID = 1218571878182523580L;
	public MyButton(String url,int width,int height){
        // 设置按钮背景图
        ImageIcon icon1=new ImageIcon(url);
        icon1.setImage(icon1.getImage().getScaledInstance((int)(width*0.8),(int)(height*0.8),Image.SCALE_DEFAULT)); 
        setIcon(icon1);

        // 不绘制焦点
        setFocusPainted(false);

        // 不绘制内容区
        setContentAreaFilled(false);

        // 设置焦点控制
        setFocusable(true);

        // 设置按钮边框与边框内容之间的像素数
        setMargin(new Insets(0, 0, 0, 0));
	}
}
