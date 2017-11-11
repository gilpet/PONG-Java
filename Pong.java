import java.applet.Applet;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends Applet implements Runnable, KeyListener 
{
	
	final int WIDTH =700; 
	final int HEIGHT = 500;
	int playerScore;
	int aiScore;
	Thread thread;
	Player player;
	AI ai;
	Ball ball;
	Font font;
	boolean gameStarted;
	Graphics graphics;
	Image img;
	
	public void init() 
	{
		gameStarted = false;
		this.resize(WIDTH, HEIGHT);
		this.addKeyListener(this);
		player = new Player(1);
		ball = new Ball();
		ai = new AI(2, ball);
		img = createImage(WIDTH,HEIGHT);
		graphics = img.getGraphics();
		playerScore = 0;
		aiScore = 0;
		thread = new Thread(this);
		thread.start();
	}
	
	public void paint(Graphics g) 
	{
		graphics.setColor(Color.cyan);
		graphics.fillRect(0, 0, WIDTH, HEIGHT);
		graphics.setColor(Color.black);
		graphics.setFont(new Font("Comic Sans MS", Font.BOLD, 18));
		graphics.drawString("Player: "+Integer.toString(playerScore), 200, 25);
		graphics.drawString("Computer: "+Integer.toString(aiScore), 400, 25);
		if (ball.getX() < -10) 
		{
			aiScore++;
			ball.resetBall();
		}
		if (ball.getX() > 710)
		{
			playerScore++;
			ball.resetBall();
		}
		if (aiScore == 15 || playerScore == 15)
		{
			playerScore = 0;
			aiScore = 0;
			gameStarted = false;
		}
		player.draw(graphics);
		ball.draw(graphics);
		ai.draw(graphics);
		if (!gameStarted)
		{
			graphics.setColor(Color.BLACK);
			graphics.drawString("PONG for CSCI3430", (WIDTH/2) - 80, 130);
			graphics.drawString("Up/Down to move", (WIDTH/2) - 80, 160);
			graphics.drawString("Press Enter to start", (WIDTH/2) - 80, 190);
		}
		g.drawImage(img, 0,0, this);
	}
	
	public void update(Graphics g) 
	{
		paint(g);
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			if (gameStarted) 
			{
				player.move();
				ai.move();
				ball.move();
				ball.checkPaddleCollision(player, ai);
			}
			repaint();
			try 
			{
				Thread.sleep(10);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			player.setUpAccelerating(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			player.setDownAccelerating(true);
		}
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			gameStarted = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			player.setUpAccelerating(false);
		}
		else if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			player.setDownAccelerating(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
