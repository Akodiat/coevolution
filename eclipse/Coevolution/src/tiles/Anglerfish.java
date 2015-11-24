package tiles;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Anglerfish extends Tile {

	public Anglerfish(int x, int y) {
		super(x, y);
		
		try {
			tileImage = ImageIO.read(new File("resources/anglerfish.png"));
		} catch (IOException e) {
		}
	}

}
