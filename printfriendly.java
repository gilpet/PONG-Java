import java.awt.Color;
import java.awt.Graphics;

public class Ball 
{
	double xVelocity = getRandomSpeed()*getRandomDirection();
	double yVelocity = getRandomSpeed()*getRandomDirection();
	double x = 350;
	double y = 250;
	
	
	public double getRandomSpeed()
	{
		return (Math.random() * 3 + 2);
	}
	
	public int getRandomDirection()
	{
		int rand = (int)(Math.random() * 2);
		if (rand == 1)
		{
			return 1;
		} 
		else
		{
			return -1;
		}
	}
	public void draw(Graphics g)
	{
		g.setColor(Color.orange);
		g.fillOval((int)x - 10, (int)y - 10, 20, 20);
	}
	
	public void checkPaddleCollision(Paddle player, Paddle ai)
	{
		if (x <= 50)
		{
			if (y >= player.getY() && y <= player.getY() + 80)
			{
				xVelocity = -xVelocity;
			}
		}
		else if (x >= 650) {
			if (y >= ai.getY() && y <= ai.getY() + 80)
			{
				xVelocity = -xVelocity;
			}
		}
	}
	
	public void move()
	{
		x += xVelocity;
		y += yVelocity;
		
		if (y < 10)
		{
			yVelocity = -yVelocity;
		}
		if (y > 490)
		{
			yVelocity = -yVelocity;
		}
	}
	public int getX()
	{
		return (int)x;
	}
	
	public int getY()
	{
		return (int)y;
	}
	public void resetBall()
	{
		x = 350;
		y = 250;
	}
}
-------------------------------------------------------------------------------------------------------
	import java.awt.Color;
import java.awt.Graphics;

public class AI implements Paddle
{
	double y;
	double yVelocity;
	boolean upAccelerating;
	boolean downAccelerating;
	int player;
	int x;
	final double GRAVITY = 0.94;
	Ball b1;
	
	public AI(int ai, Ball b)
	{
		upAccelerating = false;
		downAccelerating = false;
		b1 = b;
		y = 210;
		yVelocity = 0;
		if (ai == 1)
		{
			x = 20;
		}
		else
		{
			x = 660;
		}
	}
	public void draw(Graphics g) 
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, (int)y, 20, 80);
	}

	public void move() 
	{
		y = b1.getY() - 40;
		if (y < 0)
		{
			y = 0;
		}
		if (y > 420)
		{
			y = 420;
		}
	}

	public int getY() 
	{
		return (int)y;
	}

}
-------------------------------------------------------------------------------------------------------
import java.awt.Graphics;

public interface Paddle 
{
	public void draw(Graphics g);
	public void move();
	public int getY();
}
-------------------------------------------------------------------------------------------------------
import java.awt.Color;
import java.awt.Graphics;

public class Player implements Paddle
{
	double y;
	double yVelocity;
	boolean upAccelerating;
	boolean downAccelerating;
	int player;
	int x;
	final double GRAVITY = 0.94;
	
	public Player(int player)
	{
		upAccelerating = false;
		downAccelerating = false;
		y = 210;
		yVelocity = 0;
		if (player == 1)
		{
			x = 20;
		}
		else
		{
			x = 660;
		}
	}
	public void draw(Graphics g) 
	{
		g.setColor(Color.WHITE);
		g.fillRect(x, (int)y, 20, 80);
	}

	public void move() 
	{
		if (upAccelerating)
		{
			yVelocity -= 2;
		}
		else if (downAccelerating)
		{
			yVelocity += 2;
		}
		else if (!upAccelerating && !downAccelerating)
		{
			yVelocity *= GRAVITY;
		}
		
		if (yVelocity > 5) 
		{
			yVelocity = 5;
		}
		else if (yVelocity < -5)
		{
			yVelocity = -5;
		}
		y += yVelocity;
		if (y < 0)
		{
			y = 0;
		}
		if (y > 420)
		{
			y = 420;
		}
		
	}

	public int getY() 
	{
		return (int)y;
	}
	
	public void setUpAccelerating(boolean input)
	{
		upAccelerating = input;
	}
	
	public void setDownAccelerating(boolean input)
	{
		downAccelerating = input;
	}

}	
-------------------------------------------------------------------------------------------------------
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
-------------------------------------------------------------------------------------------------------
