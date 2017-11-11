
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