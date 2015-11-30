

import java.util.ArrayList;

import tiles.Prey;

public class PreyPopulation {
	private ArrayList<Prey> population;
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
