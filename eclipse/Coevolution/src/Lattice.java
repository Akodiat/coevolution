import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import tiles.Anglerfish;
import tiles.FoodTile;
import tiles.Prey;
import tiles.Tile;

public class Lattice extends JPanel {

	private int gridSize;
	private boolean[][] occupied;
	private ArrayList<Tile> tiles;
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
		
		//tiles = new Tile[gridSize][];
		tiles = new ArrayList<Tile>();
		
		boolean[][] occupied = new boolean[gridSize][];
		for(int i=0; i<gridSize; i++)
		{
			boolean[] row = new boolean[gridSize];
			for(int j=0; j<gridSize; j++){
				row[j] = false;
			}
			occupied[i] = row;
		}
		int nTiles = (int) Math.pow(gridSize, 2);
		int nAnglerFish = (int) (angerFishPercentage * nTiles);
		
		int nFood = (int) (foodPercentage * nTiles);
		while(nFood > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			
			if(!occupied[i][j]){
				tiles.add(new FoodTile(i, j));
				occupied[i][j] = true;
				nFood--;
			}
		}
		
		ArrayList<Anglerfish> anglerFishes = new ArrayList<Anglerfish>();
		while(nAnglerFish > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			 
			if(!occupied[i][j]){
				Anglerfish f = new Anglerfish(i, j); 
				occupied[i][j] = true;
				tiles.add(f);
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

			if(!occupied[i][j]){
				Prey p = new Prey(i, j, caution, gridSize, anglerFishes);
				occupied[i][j] = true;
				tiles.add(p);
				preyPopulation.addPrey(p);
				preyPopulationSize--;
			}
		}
	}
	
	public void update(){
		preyPopulation.movePopulation();
		this.revalidate();
		this.invalidate();
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        this.setBackground(Color.blue);
        int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		
		int tileWidth = windowWidth / gridSize;
		int tileHeight = windowHeight / gridSize;

		for (Tile tile : tiles) {
			g.setColor(tile.getColor());
			g.fillRect(tile.getX()*tileWidth, tile.getY()*tileHeight, tileWidth, tileHeight);
		}
				

    }  

}
