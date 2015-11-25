import javax.swing.JFrame;

public class Main {

	private static int 		latticeSize 		= 20;
	private static double 	angerFishPercentage	= 0.02;
	private static double 	foodPercentage 		= 0.6;
	private static int 		preyPopulationSize	= 25;
	private static int		sleepInterval		= 50;
	
	public static void main(String[] args) {
		openWindow();
	}
	
	private static void openWindow() {
		JFrame f = new JFrame("Angler fish simulation");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Lattice lattice = new Lattice(latticeSize, angerFishPercentage, foodPercentage, preyPopulationSize);
        f.add(lattice);
        f.setSize(250,250);
        f.setVisible(true);	
        
		int nIterations = 10000;
		while(nIterations-- > 0)
		{
			lattice.update();
			try {
				Thread.sleep(sleepInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
