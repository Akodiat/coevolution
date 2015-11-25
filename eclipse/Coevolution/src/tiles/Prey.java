package tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Prey extends Tile {
	private int speedX, speedY;
	private int gridSize;
	private double caution;
	private ArrayList<Anglerfish> anglerfishes;
	private ArrayList<FoodTile> food;
	private Random random = new Random();
	
	public Prey(int x, int y, double caution, int gridSize, ArrayList<Anglerfish> anglerfishes, ArrayList<FoodTile> food) {
		super(x, y);
		this.caution = caution;
		this.gridSize = gridSize;
		this.anglerfishes = anglerfishes;
		this.food = food;
        
        speedX = random.nextInt()%3==0 ? 1 : (random.nextInt()%2==0 ? 0 : -1);
        speedY = random.nextInt()%3==0 ? 1 : (random.nextInt()%2==0 ? 0 : -1);
		
		try {
			tileImage = ImageIO.read(new File("resources/fish.png"));
		} catch (IOException e) {
		}
		rotateImage();
	}
	
	private void rotateImage() {
	    double angle = Math.PI;;
	    if(speedX == 0)
	    	angle = (Math.PI/2 * speedY);
	    else if(speedY == 0)
	    	angle = speedX == 1 ? 0 : Math.PI;
	    else if(speedX == 1 && speedY == 1)
	    	angle = Math.PI/4;
	    else if(speedX == -1 && speedY == 1)
	    	angle = 3*Math.PI/4;
	    else if(speedX == -1 && speedY == -1)
	    	angle = -3*Math.PI/4;
	    else if(speedX == 1 && speedY == -1)
	    	angle = -Math.PI/4;
	    
	    angle += Math.PI/2;
	    try {
			tileImage = ImageIO.read(new File("resources/fish.png"));
		} catch (IOException e) {
		}
	    
	    int w = tileImage.getWidth();
	    int h = tileImage.getHeight();

	    BufferedImage result = new BufferedImage(w, h, tileImage.getType());
	    Graphics2D g = result.createGraphics();
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(tileImage, null);
	    
	    tileImage = result;
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
		
		tryEat();
	}
	
	private void tryEat()
	{
		for(int i=0; i<food.size(); i++)
		{
			FoodTile f = food.get(i);
			if(f.x == x && f.y == y)
			{
				food.remove(i);
				return;
			}
		}
	}
	
	private Boolean isNeigbourhoodSafe()
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
				rotateImage();
				return false;
			}
		}
		return true;
	}

}
