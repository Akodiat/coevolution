import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import tiles.Anglerfish;
import tiles.FoodTile;
import tiles.Prey;
import tiles.Tile;

public class Lattice extends JPanel {

	private int gridSize;
	private Tile[][] tiles;
	private PreyPopulation preyPopulation;
	
	private static final long serialVersionUID = 1L;
	private Random random = new Random();
	
	public Lattice(
			int gridSize,
			double angerFishPercentage,
			double foodPercentage,
			int preyPopulationSize
			)
	{
		this.gridSize = gridSize;
		
		tiles = new Tile[gridSize][];
		for(int i=0; i<gridSize; i++)
		{
			Tile[] row = new Tile[gridSize];
			for(int j=0; j<gridSize; j++){
				row[j] = new Tile(i,j);
			}
			tiles[i] = row;
		}
		int nTiles = (int) Math.pow(gridSize, 2);
		int nAnglerFish = (int) (angerFishPercentage * nTiles);
		
		int nFood = (int) (foodPercentage * nTiles);
		while(nFood > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			
			if(!(tiles[i][j] instanceof FoodTile)){
				tiles[i][j] = new FoodTile(i, j);
				nFood--;
			}
		}
		
		ArrayList<Anglerfish> anglerFishes = new ArrayList<Anglerfish>();
		while(nAnglerFish > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			
			Tile t = tiles[i][j]; 
			if(!(	(t instanceof Anglerfish) ||
					(t instanceof FoodTile))){
				Anglerfish f = new Anglerfish(i, j); 
				tiles[i][j] = f;
				anglerFishes.add(f);
				
				nAnglerFish--;
			}
		}
		
		preyPopulation = new PreyPopulation(preyPopulationSize);
		while(preyPopulationSize > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			
			double caution = random.nextDouble();
			
			Tile t = tiles[i][j]; 
			if(!(	(t instanceof Prey)) ||
					(t instanceof Anglerfish) || 
					(t instanceof FoodTile )){
				Prey p = new Prey(i, j, caution, gridSize, anglerFishes);
				tiles[i][j] = p;
				preyPopulation.addPrey(p);
				preyPopulationSize--;
			}
		}
		int nIterations = 100;
		while(nIterations-- > 0)
		{
			preyPopulation.movePopulation();
			this.revalidate();
			this.invalidate();
			this.repaint();
		}
		
		
	}
	public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		
		int tileWidth = windowWidth / gridSize;
		int tileHeight = windowHeight / gridSize;
		
		Random random = new Random();
		for(int i=0; i<gridSize; i++)
			for(int j=0; j<gridSize; j++)
			{
				int r = random.nextInt(255);
				g.setColor(tiles[i][j].getColor());
				g.fillRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
			}
    }  

}
