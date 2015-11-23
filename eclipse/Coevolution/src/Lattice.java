import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class Lattice extends JPanel {

	private int gridSize;
	private static final long serialVersionUID = 1L;
	
	public Lattice(int gridSize)
	{
		this.gridSize = gridSize;
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
				g.setColor(new Color(r));
				g.fillRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
			}
    }  

}
