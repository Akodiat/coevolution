package tiles;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Prey extends Tile {
	public static double reproductionFoodLevel = 3;
	public static double foodNeededForMove = 0.01;
	public static double cautionMutationSpan = 0.01;
	
	private int gridSize;
	private double caution;
	private double food;
	private double direction;
	private LinkedList<Anglerfish> anglerfishes;
	private LinkedList<FoodTile> foodTiles;
	private LinkedList<Prey> school;
	private Random random = new Random();
	
	public Prey(
			int x,
			int y,
			double caution,
			int gridSize,
			LinkedList<Anglerfish> anglerfishes,
			LinkedList<FoodTile> foodTiles,
			LinkedList<Prey> school) 
	{
		super(x, y);
		this.caution = caution;
		this.gridSize = gridSize;
		this.anglerfishes = anglerfishes;
		this.foodTiles = foodTiles;
		this.school = school;
		this.food = reproductionFoodLevel / 2;
        
		direction = 2*Math.PI*(random.nextInt(7))/8;
		
		try {
			tileImage = ImageIO.read(new File("resources/fish.png"));
		} catch (IOException e) {
		}
		rotateImage();
	}
	
	private void rotateImage() {
	    double angle = direction + Math.PI/2;
	    
	    try {
			tileImage = ImageIO.read(new File("resources/fish.png"));
		} catch (IOException e) {
		}
	    
	    int w = tileImage.getWidth();
	    int h = tileImage.getHeight();

	    BufferedImage result = new BufferedImage(w, h, tileImage.getType());
	    Graphics2D g = result.createGraphics();
	    g.rotate(angle, w / 2, h / 2);
	    
	    Color color = new Color(178,34,34,(int) (caution*255));
	    g.drawRenderedImage(tileImage, null);
	    g.setColor(color);
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));
	    g.fillRect(0, 0, w, h);
	    
	    g.drawRenderedImage(tileImage, null);
	    
	    
	    
	    tileImage = result;
	    
	}

	
	public void move()
	{
		if(food <= 0)
			die();

		food -= foodNeededForMove;
		
		double rand = random.nextDouble();
		if(caution < rand) {
			isNeigbourhoodSafe();
			return;
		}
		else{	
			
			rand = random.nextDouble();
			if (rand < 0.25 ){
				direction -= Math.PI/4;
				rotateImage();
			}
			else if (rand > 0.75){
				direction += Math.PI/4;
				rotateImage();
			}
			
			x += Math.signum(Math.cos(direction));
			y += Math.signum(Math.sin(direction));
		}
		
		x %= gridSize;
		y %= gridSize;
		
		if(food < reproductionFoodLevel)
			tryEat();
		else
			reproduce();
	}
	private void die()
	{
		school.remove(this);
	}
	private void reproduce()
	{
		food -= reproductionFoodLevel / 2;
		
		double c = cautionMutationSpan;
		
		double mutatedCaution = caution + (c*2*random.nextDouble())-c;
		
		if(mutatedCaution>1)
			mutatedCaution = 1;
		else if(mutatedCaution<0)
			mutatedCaution = 0;
		
		Prey child = new Prey(x,y,mutatedCaution,gridSize,anglerfishes,foodTiles,school);
		
		school.add(child);
	}
	
	private void tryEat()
	{
		for(int i=0; i<foodTiles.size(); i++)
		{
			FoodTile f = foodTiles.get(i);
			if(f.x == x && f.y == y)
			{
				foodTiles.remove(i);
				food++;
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
				direction += Math.PI;
				direction %= 2*Math.PI;
				rotateImage();
				return false;
			}
		}
		return true;
	}

}
