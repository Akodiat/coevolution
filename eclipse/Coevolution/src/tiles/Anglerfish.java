package tiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Anglerfish extends Tile {

	private ArrayList<Prey> prey;
	
	public Anglerfish(int x, int y, ArrayList<Prey> prey) {
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
