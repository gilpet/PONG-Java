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
