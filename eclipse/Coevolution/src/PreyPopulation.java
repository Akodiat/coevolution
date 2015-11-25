

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

import tiles.Prey;

public class PreyPopulation {
	private ArrayList<Prey> population;
	private Random random = new Random();
	
	public PreyPopulation(int populationSize){
		population = new ArrayList<Prey>();
	}
	
	public void addPrey(Prey p)
	{
		population.add(p);
	}
	
	public ArrayList<Prey> getList()
	{
		return population;
	}
	
	public void movePopulation(){
		for (Prey prey : population) {
			prey.move();
		}
	}
}
