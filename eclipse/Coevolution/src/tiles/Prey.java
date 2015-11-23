package tiles;

import java.awt.Color;
import java.util.Random;

public class Prey extends Tile {
	private int speedX, speedY;
	private int gridSize;
	private double caution;
	private Random random = new Random();
	
	public Prey(int x, int y, double caution, int gridSize) {
		super(x, y);
		this.caution = caution;
		this.gridSize = gridSize;
	}
	
	@Override
	public Color getColor() {
		return Color.white;
	}
	
	public void move()
	{
		double rand = random.nextDouble();
		if(caution < rand) {
			; // Check for prey
		}
		else{
			x += speedX;
			y += speedY;
		}
		
		x %= gridSize;
		y %= gridSize;
	}

}
