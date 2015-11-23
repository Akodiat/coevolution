

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import tiles.Prey;

public class PreyPopulation {
	ArrayList<Prey> population;
	Random random = new Random();
	
	public PreyPopulation(int populationSize, int gridSize){
		population = new ArrayList<Prey>();
		for(int i=0; i<populationSize; i++){
			int x = random.nextInt(gridSize);
			int y = random.nextInt(gridSize);
			
			double caution = random.nextDouble();
			
			population.add(new Prey(x,y,caution,gridSize));

		}
			
	}
}
