import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
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
	private ArrayList<FoodTile> food;
	private ArrayList<Anglerfish> anglerfishes;
	private ArrayList<Prey> preyPopulation;
	
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
		
		occupied = new boolean[gridSize][];
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
		
		food = new ArrayList<FoodTile>();
		anglerfishes = new ArrayList<Anglerfish>();
		preyPopulation = new ArrayList<Prey>();
		
		int nFood = (int) (foodPercentage * nTiles);
		while(nFood > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			
			if(!occupied[i][j]){
				food.add(new FoodTile(i, j));
				occupied[i][j] = true;
				nFood--;
			}
		}

		while(nAnglerFish > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			 
			if(!occupied[i][j]){
				Anglerfish f = new Anglerfish(i, j, preyPopulation); 
				occupied[i][j] = true;
				anglerfishes.add(f);
				
				nAnglerFish--;
			}
		}
		
		while(preyPopulationSize > 0)
		{
			int i = random.nextInt(gridSize);
			int j = random.nextInt(gridSize);
			
			double caution = random.nextDouble();

			if(!occupied[i][j]){
				Prey p = new Prey(i, j, caution, gridSize, 
						anglerfishes, food, preyPopulation);
				occupied[i][j] = true;
				preyPopulation.add(p);
				preyPopulationSize--;
			}
		}
	}
	
	public void update(){
		// Use shallow copy of list to allow prey to remove
		// themselves from the actual list if they die.
		ArrayList<Prey> preyPopCopy = (ArrayList<Prey>) 
				preyPopulation.clone();
		
		for (Prey prey : preyPopCopy) {
			prey.move();
		}
		
		for (Anglerfish a : anglerfishes) {
			a.checkForPrey();
		}
		this.revalidate();
		this.invalidate();
		this.repaint();
	}
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g); 
        
        this.setBackground(Color.decode("#8e97ed"));
        int windowWidth = this.getWidth();
		int windowHeight = this.getHeight();
		
		int tileWidth = windowWidth / gridSize;
		int tileHeight = windowHeight / gridSize;

		ArrayList<Tile> tiles = new ArrayList<Tile>(food);
		tiles.addAll(anglerfishes);
		tiles.addAll(preyPopulation);
		
		for (Tile tile : tiles) {
			g.drawImage(
					tile.getImage(),
					tile.getX()*tileWidth,
					tile.getY()*tileHeight,
					tileWidth,
					tileHeight, 
					new ImageObserver() {
				
				@Override
				public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
					// TODO Auto-generated method stub
					return false;
				}
			});
		}
				

    }  

}
