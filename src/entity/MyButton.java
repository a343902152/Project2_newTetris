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
        // ���ð�ť����ͼ
        ImageIcon icon1=new ImageIcon(url);
        icon1.setImage(icon1.getImage().getScaledInstance((int)(width*0.8),(int)(height*0.8),Image.SCALE_DEFAULT)); 
        setIcon(icon1);

        // �����ƽ���
        setFocusPainted(false);

        // ������������
        setContentAreaFilled(false);

        // ���ý������
        setFocusable(true);

        // ���ð�ť�߿���߿�����֮���������
        setMargin(new Insets(0, 0, 0, 0));
	}
}
