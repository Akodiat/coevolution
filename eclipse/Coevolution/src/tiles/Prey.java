package tiles;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Prey extends Tile {
	private int speedX, speedY;
	private int gridSize;
	private double caution;
	private ArrayList<Anglerfish> anglerfishes;
	private Random random = new Random();
	
	public Prey(int x, int y, double caution, int gridSize, ArrayList<Anglerfish> anglerfishes) {
		super(x, y);
		this.caution = caution;
		this.gridSize = gridSize;
		this.anglerfishes = anglerfishes;
		
		speedX = random.nextInt()%2==0 ? 1 : -1;
		speedY = random.nextInt()%2==0 ? 1 : -1;
	}
	
	@Override
	public Color getColor() {
		return Color.white;
	}
	
	public void move()
	{
		double rand = random.nextDouble();
		if(caution < rand) {
			isNeigbourhoodSafe();
			return;
		}
		else{
			x += speedX;
			y += speedY;
		}
		
		x %= gridSize;
		y %= gridSize;
	}
	
	public Boolean isNeigbourhoodSafe()
	{
		int xPlus1 =  (x+1) % gridSize;
		int yPlus1 =  (y+1) % gridSize;
		int xMinus1 = (x-1) % gridSize;
		int yMinus1 = (y-1) % gridSize;
		
		for (Anglerfish a : anglerfishes) {
			if(
			(a.x == xPlus1 || a.x == x || a.x == xMinus1) && 
			(a.y == yPlus1 || a.y == y || a.y == yMinus1))
			{
				speedX = x-a.x;
				speedY = y-a.y;
				return false;
			}
		}
		return true;
	}

}
