package brick_cracker;

import java.awt.Dimension;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(400, 150, 700, 600);
		frame.setSize(new Dimension(700, 600));
		frame.setVisible(true);
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePlay gamePlay = new GamePlay();
		frame.add(gamePlay);
	}

}
