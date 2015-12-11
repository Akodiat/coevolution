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
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

public class Prey extends Tile {
	public static double reproductionFoodLevel = 3;
	public static double foodNeededForMove = 0.01;
	public static double cautionMutationSpan = 0.01;
	
	private int gridSize;
	private double food;
	private double direction;
	
	private double maxMetabolism;
	private double minMetabolism;
	private double brainSize; 
	private double c1;
	private double c2;
	private LinkedList<Anglerfish> anglerfishes;
	private LinkedList<FoodTile> foodTiles;
	private LinkedList<Prey> school;
	private Random random = new Random();
	
	public Prey(
			int x,
			int y,
			double brainSize,
			double maxMetabolism,
			double minMetabolism,
			int gridSize,
			LinkedList<Anglerfish> anglerfishes,
			LinkedList<FoodTile> foodTiles,
			LinkedList<Prey> school) 
	{
		super(x, y);
		this.brainSize = brainSize;
		this.maxMetabolism = maxMetabolism;
		this.minMetabolism = minMetabolism;
		this.gridSize = gridSize;
		this.anglerfishes = anglerfishes;
		this.foodTiles = foodTiles;
		this.school = school;
		this.food = reproductionFoodLevel / 2;
		
		c1 = (minMetabolism * Math.E - maxMetabolism) / (Math.E - 1);
		c2 = (maxMetabolism - minMetabolism) / (Math.E - 1);
        
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
	    
	    Color color = new Color(
	    		(int) (brainSize*255), 		// R
	    		100,						// G
	    		(int) ((1-brainSize)*255),	// B
	    		255							// A
	    );
	    g.drawRenderedImage(tileImage, null);
	    g.setColor(color);
	    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.75f));
	    g.fillRect(0, 0, w, h);
	    
	    g.drawRenderedImage(tileImage, null);
	    
	    tileImage = result;
	    
	}
	public double getBrainSize()
	{
		return brainSize;
	}

	
	public void move()
	{
		if(food <= 0)
			die();

		food -= foodNeededForMove;
		
		double rand = random.nextDouble();
		int lookForward = lookForward();
		if(lookForward < 0) // Angler fish
		{
			rand = random.nextDouble();
			if (rand < 0.5 ){
				direction -= Math.PI/4;
				rotateImage();
			}
			else {
				direction += Math.PI/4;
				rotateImage();
			}
		}
		
		x += Math.signum(Math.cos(direction));
		y += Math.signum(Math.sin(direction));
		
		x = (x + gridSize) % gridSize;
		y = (y + gridSize) % gridSize;
		
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
		
		double mutatedBrainSize = brainSize + (c*random.nextGaussian())-c;
		if(mutatedBrainSize>1)
			mutatedBrainSize = 1;
		else if(mutatedBrainSize<0)
			mutatedBrainSize = 0;
		
		Prey child = new Prey(x,y,mutatedBrainSize,maxMetabolism,minMetabolism,gridSize,anglerfishes,foodTiles,school);
		
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
	
	private double getMetabolism()
	{
		return c1 + c2 * Math.exp(brainSize);
	}
	/**
	 * Looks forward and return what is there.
	 * @return 0, if title in front is empty. 1, if it is food, -1 if it is an angler fish.
	 */
	private int lookForward()
	{
		double dx = Math.signum(Math.cos(direction));
		double dy = Math.signum(Math.sin(direction));
		
		double xInFront = (x + dx + gridSize) % gridSize;
		double yInFront = (y + dy + gridSize) % gridSize;
		
		// value==0 equals nothing in front of us.
		int value = 0;
		
		// Check for angler fish
		for (Anglerfish a : anglerfishes)
			if(a.x == xInFront && a.y == yInFront)
			{
				value = -1;
				break;
			}
		// If we don't have a angler fish in front of us
		if(value == 0)
			// Check for food
			for (FoodTile f : foodTiles)
				if(f.x == xInFront && f.y == yInFront)
				{
					value = 1;
					break;
				}
		
		// If we fail to think correctly, be mistaken.
		value *= thinksCorrectly() ? 1 : -1;

		return value;
	}
	
	public boolean thinksCorrectly()
	{
		double probCorrect = 0.5 * (brainSize+1);
		Random r = new Random();
		return r.nextDouble() < probCorrect;
	}

}
