package tiles;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Anglerfish extends Tile {

	private ArrayList<Prey> prey;
	
	public Anglerfish(int x, int y) {
		super(x, y);
		
		try {
			tileImage = ImageIO.read(new File("resources/anglerfish.png"));
		} catch (IOException e) {
		}

	}
	
	public void addPrey(ArrayList<Prey> prey)
	{		
		this.prey = prey;
	}
	
	public void checkForPrey()
	{
		for(int i=0; i<prey.size(); i++)
		{
			Prey p = prey.get(i);
			if(p.x == x && p.y == y)
				prey.remove(i);
		}
	}

}
