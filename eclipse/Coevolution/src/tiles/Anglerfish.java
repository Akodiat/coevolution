package tiles;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class Anglerfish extends Tile {

	private LinkedList<Prey> prey;
	
	public Anglerfish(int x, int y, LinkedList<Prey> prey) {
		super(x, y);
		
		this.prey = prey;
		
		try {
			tileImage = ImageIO.read(new File("resources/anglerfish.png"));
		} catch (IOException e) {
		}

	}
	
	public void checkForPrey()
	{
		for(int i=prey.size()-1; i >= 0; i--)
		{
			Prey p = prey.get(i);
			if(p.x == x && p.y == y)
				prey.remove(i);
		}
	}

}
