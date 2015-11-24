package tiles;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Tile {
	protected int x;
	protected int y;
	protected BufferedImage tileImage;
	
	public Tile(int x, int y){
		this.x = x;
		this.y = y;
		
		try {
			tileImage = ImageIO.read(new File("default.jpg"));
		} catch (IOException e) {
		}
	}
	
	public Image getImage()
	{
		return tileImage;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
