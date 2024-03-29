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
