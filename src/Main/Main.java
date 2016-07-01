package Main;
import java.awt.EventQueue;

import UI.MainFrame;

public class Main {

	private static MainFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new MainFrame();
					frame.init();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static MainFrame getFrame() {
		return frame;
	}
}
