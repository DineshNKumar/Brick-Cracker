package brick_cracker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener {

	private boolean play = false;
	private int score = 0;

	private int totalBricks = 21;
	private Timer timer;

	private int delay = 10;
	private int playerX = 310;

	private int ballPosX = 120;
	private int ballPosY = 350;
	private int ballDirX = -1;
	private int ballDirY = -2;

	private MapGenerator mapGenerator;

	public GamePlay() {
		mapGenerator = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer = new Timer(delay, this);
		timer.start();
	}

	@Override
	public void paint(Graphics e) {
		// Background
		e.setColor(Color.BLACK);
		e.fillRect(1, 1, 692, 592);

		// Border
		e.setColor(Color.GREEN);
		e.fillRect(0, 0, 3, 592);
		e.fillRect(0, 0, 692, 3);
		e.fillRect(691, 0, 3, 592);

		// drawing map
		mapGenerator.draw((Graphics2D) e);

		// the paddle
		e.setColor(Color.green);
		e.fillRect(playerX, 550, 100, 8);

		// score
		e.setColor(Color.white);
		e.setFont(new Font("serif", Font.BOLD, 25));
		e.drawString("" + score, 590, 30);
		// the paddle
		e.setColor(Color.yellow);
		e.fillOval(ballPosX, ballPosY, 20, 20);

		if (ballPosY > 570) {
			play = false;
			ballDirX = 0;
			ballDirY = 0;
			e.setColor(Color.red);
			e.setFont(new Font("System", Font.BOLD, 30));
			e.drawString("Game Over Score : ", 190, 300);

			e.setFont(new Font("System", Font.BOLD, 20));
			e.drawString("Enter to Restart : ", 230, 350);
		}

		e.dispose();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if (play) {
			if (new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballDirY = -ballDirY;
			}

			A: for (int i = 0; i < mapGenerator.map.length; i++) {
				for (int j = 0; j < mapGenerator.map[0].length; j++) {
					if (mapGenerator.map[i][j] > 0) {
						int brickX = j * mapGenerator.brickWidth + 80;
						int brickY = i * mapGenerator.brickHeight + 50;
						int brickWidth = mapGenerator.brickWidth;
						int brickHeight = mapGenerator.brickHeight;
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle rectangle = rect;
						if (ballRect.intersects(rectangle)) {
							mapGenerator.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;

							if (ballPosX + 10 <= rectangle.x || ballPosX + 1 <= rectangle.x + rectangle.width) {
								ballDirX = -ballDirX;
							} else {
								ballDirY = -ballDirY;
							}

						}
					}
				}
			}
			ballPosX += ballDirX;
			ballPosY += ballDirY;
			if (ballPosX < 0) {
				ballDirX = -ballDirX;
			}
			if (ballPosY < 0) {
				ballDirY = -ballDirY;
			}
			if (ballPosX > 670) {
				ballDirX = -ballDirX;
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_A) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (play) {
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballDirX = -1;
				ballDirY = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				mapGenerator = new MapGenerator(3, 7);

			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

}
