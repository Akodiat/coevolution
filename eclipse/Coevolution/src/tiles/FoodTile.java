package tiles;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FoodTile extends Tile {

	public FoodTile(int x, int y) {
		super(x, y);
		
		try {
			// graphic from 'NicoleMarieProductions'
			// http://opengameart.org/content/bush-1616
		    tileImage = ImageIO.read(new File("resources/bush.png"));
		} catch (IOException e) {
		}

	}


}
