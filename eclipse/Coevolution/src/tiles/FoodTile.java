package tiles;

import java.awt.Color;

public class FoodTile extends Tile {

	public FoodTile(int x, int y) {
		super(x, y);
	}
	
	@Override
	public Color getColor() {
		return Color.green;
	}

}
