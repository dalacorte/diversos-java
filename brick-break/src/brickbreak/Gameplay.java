package brickbreak;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 6401627490752203104L;
	private boolean play = false;
	private int score = 0;
	private int totalBricks = 21;
	private Timer timer;
	private int delay = 8;
	private int playerX = 310;
	private int ballX = 120;
	private int ballY = 350;
	private int ballXd = -1;
	private int ballYd = -2;
	private MapGenerator map;

	public Gameplay() {
		map = new MapGenerator(3, 7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);

		map.draw((Graphics2D) g);

		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("" + score, 590, 30);

		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		g.setColor(Color.yellow);
		g.fillOval(ballX, ballY, 20, 20);

		if (totalBricks <= 0) {
			play = false;
			ballXd = 0;
			ballYd = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won", 260, 300);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press enter to restart", 230, 350);
		}

		if (ballY > 570) {
			play = false;
			ballXd = 0;
			ballYd = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over", 230, 300);

			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press enter to restart", 230, 350);
		}

		g.dispose();
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();

		if (play) {
			if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYd = -ballYd;
			}

			A: for (int i = 0; i < map.map.length; i++) {
				for (int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeigth + 50;
						int brickWidth = map.brickWidth;
						int brickHeigth = map.brickHeigth;

						Rectangle r = new Rectangle(brickX, brickY, brickWidth, brickHeigth);
						Rectangle ballR = new Rectangle(ballX, ballY, 20, 20);
						Rectangle brickR = r;

						if (ballR.intersects(brickR)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;

							if (ballX + 19 <= brickR.x || ballX + 1 >= brickR.x + brickR.width) {
								ballXd = -ballXd;
							} else {
								ballYd = -ballYd;
							}

							break A;
						}
					}
				}
			}

			ballX += ballXd;
			ballY += ballYd;
			if (ballX < 0) {
				ballXd = -ballXd;
			}
			if (ballY < 0) {
				ballYd = -ballYd;
			}
			if (ballX > 670) {
				ballXd = -ballXd;
			}
		}

		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballX = 120;
				ballY = 350;
				ballXd = -1;
				ballYd = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);
				repaint();
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
